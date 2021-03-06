package ch.hslu.appe.fbs.business.item;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifier;
import ch.hslu.appe.fbs.business.logger.Logger;
import ch.hslu.appe.fbs.business.stock.Stock;
import ch.hslu.appe.fbs.business.stock.StockException;
import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.data.item.ItemPersistor;
import ch.hslu.appe.fbs.data.reorder.ReorderPersistor;
import ch.hslu.appe.fbs.model.db.Item;
import ch.hslu.appe.fbs.model.db.Reorder;
import ch.hslu.appe.fbs.wrapper.ItemWrapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ItemManagerImpl implements ItemManager {

    private static final Object LOCK = new Object();

    private static final String ERROR_NULL_OBJ_REFERENCE = "object reference can't be null";
    private static final String ERROR_INVALID_QUANTITY = "quantity has to be >= 1";

    private final AuthorisationVerifier authorisationVerifier;
    private final ItemPersistor itemPersistor;
    private final ItemWrapper itemWrapper;
    private final ReorderPersistor reorderPersistor;
    private final Stock centralStock;

    public ItemManagerImpl(final AuthorisationVerifier authorisationVerifier, final ItemPersistor itemPersistor,
                           final ReorderPersistor reorderPersistor, final Stock stock) {
        this.authorisationVerifier = authorisationVerifier;
        this.itemPersistor = itemPersistor;
        this.reorderPersistor = reorderPersistor;
        this.centralStock = stock;
        this.itemWrapper = new ItemWrapper();
    }

    @Override
    public List<ItemDTO> getAllItems(final UserDTO userDTO) throws UserNotAuthorisedException {
        this.authorisationVerifier.checkUserAuthorisation(userDTO, UserPermissions.GET_ALL_ITEMS);
        synchronized (LOCK) {
            List<ItemDTO> itemDTOS = new ArrayList<>();
            this.itemPersistor.getAllItems().forEach(item -> itemDTOS.add(itemWrapper.dtoFromEntity(item)));
            return itemDTOS;
        }
    }

    @Override
    public void updateItemStock(final ItemDTO itemDTO, final int quantity) {
        if (itemDTO == null) {
            throw new IllegalArgumentException(ERROR_NULL_OBJ_REFERENCE);
        }
        if (quantity < 1) {
            throw new IllegalArgumentException(ERROR_INVALID_QUANTITY);
        }
        synchronized (LOCK) {
            final Item item = this.itemWrapper.entityFromDTO(itemDTO);
            int stock = item.getLocalStock() - quantity;
            int virtualStock = item.getVirtualLocalStock() - quantity;
            if (stock < 0) {
                stock = 0;
            }
            item.setLocalStock(stock);
            item.setVirtualLocalStock(virtualStock);
            this.itemPersistor.updateStock(item);
            reorderCheck(item.getId());
            Logger.logInfo(getClass(), "",
                    "Updated Item Stock of Item: " + item.getArtNr() + " to " + item.getLocalStock());
        }
    }

    @Override
    public void updateMinLocalStock(final ItemDTO itemDTO, final int newMinLocalStock) {
        if (itemDTO == null) {
            throw new IllegalArgumentException(ERROR_NULL_OBJ_REFERENCE);
        }
        if (newMinLocalStock < 1) {
            throw new IllegalArgumentException("minimum local stock has to be >= 1");
        }
        synchronized (LOCK) {
            final Item item = this.itemWrapper.entityFromDTO(itemDTO);
            item.setMinLocalStock(newMinLocalStock);
            this.itemPersistor.updateStock(item);
            Logger.logInfo(getClass(), "",
                    "Updated MinLocalStock of Item: " + item.getArtNr() + " to " + item.getMinLocalStock());
            if (newMinLocalStock > item.getLocalStock()) {
                reorderCheck(item.getId());
            }
        }
    }

    @Override
    public int getAvailableItemQuantity(final int id) {
        synchronized (LOCK) {
            Optional<Item> item = this.itemPersistor.getItemById(id);
            if (item.isPresent()) {
                return item.get().getLocalStock();
            }
            throw new IllegalArgumentException("Item not found!");
        }
    }

    @Override
    public void refillItemStock(final ItemDTO itemDTO, final int quantity) {
        if (itemDTO == null) {
            throw new IllegalArgumentException(ERROR_NULL_OBJ_REFERENCE);
        }
        if (quantity < 1) {
            throw new IllegalArgumentException(ERROR_INVALID_QUANTITY);
        }
        synchronized (LOCK) {
            Optional<Item> itemById = this.itemPersistor.getItemById(itemDTO.getId());
            if (itemById.isPresent()) {
                final Item itemFromDB = itemById.get();
                final int virtualStock = itemFromDB.getVirtualLocalStock() + quantity;
                int stock = 0;
                if (virtualStock >= 0) {
                    stock = virtualStock;
                }
                itemFromDB.setLocalStock(stock);
                itemFromDB.setVirtualLocalStock(virtualStock);
                this.itemPersistor.updateStock(itemFromDB);
                Logger.logInfo(getClass(), "",
                        "Refilled Item Stock of Item: " + itemDTO.getArtNr() + " to " + itemFromDB.getLocalStock());
            }
        }
    }

    @Override
    public ItemDTO getItem(final int itemId) {
        Optional<Item> itemById = this.itemPersistor.getItemById(itemId);
        if (itemById.isPresent()) {
            return this.itemWrapper.dtoFromEntity(itemById.get());
        }
        throw new IllegalArgumentException("Item not found!");
    }

    @Override
    public void reorderItem(final int itemId, final int quantity) throws StockException {
        if (quantity < 1) {
            throw new IllegalArgumentException(ERROR_INVALID_QUANTITY);
        }
        synchronized (LOCK) {
            final Optional<Item> optItem = this.itemPersistor.getItemById(itemId);
            if(optItem.isPresent()) {
                final Item item = optItem.get();
                final Reorder reorder = new Reorder();
                reorder.setItemId(itemId);
                reorder.setQuantity(quantity);
                reorder.setReorderDate(Timestamp.from(Instant.now()));
                this.reorderPersistor.save(reorder);
                this.centralStock.orderItem(item.getArtNr(), quantity);
            } else {
                throw new IllegalArgumentException("No item with id '" + itemId + "' found!");
            }
        }
    }

    private void reorderCheck(final int itemId) {
        Optional<Item> optItem = this.itemPersistor.getItemById(itemId);
        if(optItem.isPresent()){
            Item item = optItem.get();
            List<Reorder> reorders = this.reorderPersistor.getByItemId(itemId);
            int reorderedQuantity = 0;
            for (Reorder reorder : reorders) {
                if(reorder.getDelivered() == null) {
                    reorderedQuantity += reorder.getQuantity();
                }
            }
            int reorderQuantity = item.getMinLocalStock() - (item.getVirtualLocalStock() + reorderedQuantity);
            if(reorderQuantity > 0){
                try {
                    this.reorderItem(itemId, reorderQuantity);
                } catch (StockException e) {
                    Logger.logError(getClass(), "Unknown User", e.getMessage());
                }
            }
        }
    }
}
