package ch.hslu.appe.fbs.business.item;

import ch.hslu.appe.fbs.business.stock.Stock;
import ch.hslu.appe.fbs.business.stock.StockException;
import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.dto.UserRoleDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.data.item.ItemPersistor;
import ch.hslu.appe.fbs.data.reorder.ReorderPersistor;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import ch.hslu.appe.fbs.model.db.Item;
import ch.hslu.appe.fbs.model.db.Reorder;
import ch.hslu.appe.fbs.wrapper.ItemWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public final class ItemManagerImplTest {

    @Mock
    private ItemPersistor itemPersistor;

    @Mock
    private ReorderPersistor reorderPersistor;

    @Mock
    private Stock stock;

    private ItemManager itemManager;
    private List<Item> itemTestees;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.itemManager = new ItemManagerImpl(this.itemPersistor, this.reorderPersistor, this.stock);
        this.itemTestees = getItemTestees();
        when(this.itemPersistor.getAllItems()).thenReturn(this.itemTestees);
        when(this.itemPersistor.getItemById(this.itemTestees.get(0).getId())).thenReturn(Optional.of(this.itemTestees.get(0)));
        when(this.itemPersistor.getItemById(this.itemTestees.get(1).getId())).thenReturn(Optional.of(this.itemTestees.get(1)));
        when(this.itemPersistor.getItemById(this.itemTestees.get(2).getId())).thenReturn(Optional.of(this.itemTestees.get(2)));
        when(this.reorderPersistor.getByItemId(this.itemTestees.get(0).getId())).thenReturn(new ArrayList<>(this.itemTestees.get(0).getReordersById()));
        when(this.reorderPersistor.getByItemId(this.itemTestees.get(1).getId())).thenReturn(new ArrayList<>(this.itemTestees.get(1).getReordersById()));
        when(this.reorderPersistor.getByItemId(this.itemTestees.get(2).getId())).thenReturn(new ArrayList<>(this.itemTestees.get(2).getReordersById()));
    }

    @Test
    public void getAllItems_WhenItemsExist_ReturnThemAll() throws UserNotAuthorisedException {
        final UserDTO userTestee = getUserTestee();
        final List<ItemDTO> itemsDTO = this.itemManager.getAllItems(userTestee);

        assertEquals(this.itemTestees.size(), itemsDTO.size());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void getAllItems_WhenItemsExistButUserNotAuthorized_ThrowException() throws UserNotAuthorisedException {
        final UserDTO userTestee = getNotAuthorizedUserTestee();
        this.itemManager.getAllItems(userTestee);
    }

    @Test
    public void getItem_WhenValidItemId_ThenReturnAssociatedItem() {
        final Item itemTestee = this.itemTestees.get(0);
        final ItemDTO itemDTO = this.itemManager.getItem(itemTestee.getId());

        assertEquals(itemTestee.getId(), itemDTO.getId());
        assertEquals(itemTestee.getName(), itemDTO.getName());
        assertEquals(itemTestee.getArtNr(), itemDTO.getArtNr());
        assertEquals(itemTestee.getPrice(), itemDTO.getPrice());
        assertEquals(itemTestee.getLocalStock(), itemDTO.getLocalStock());
        assertEquals(itemTestee.getMinLocalStock(), itemDTO.getMinLocalStock());
        assertEquals(itemTestee.getVirtualLocalStock(), itemDTO.getVirtualLocalStock());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getItem_WhenInvalidItemId_ThrowException() {
        this.itemManager.getItem(-1);
    }

    @Test
    public void getAvailableItemQuantity_WhenValidItemId_ThenReturnQuantityOfAssociatedItem() {
        final Item itemTestee = this.itemTestees.get(0);
        final int availableItemQuantity = this.itemManager.getAvailableItemQuantity(itemTestee.getId());

        assertEquals(itemTestee.getLocalStock().intValue(), availableItemQuantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAvailableItemQuantity_WhenInvalidItemId_ThrowException() {
        this.itemManager.getAvailableItemQuantity(-1);
    }

    @Test
    public void updateItemStock_WhenValidItemAndQuantity_ThenUpdateStockOfAssociatedItem_WithoutReorder() {
        final Item itemTestee = this.itemTestees.get(2);
        final ItemDTO itemDTO = new ItemWrapper().dtoFromEntity(itemTestee);
        final int quantity = 5;
        final int expectedLocalStock = (itemTestee.getLocalStock() - quantity) > 0 ? (itemTestee.getLocalStock() - quantity) : 0;
        final int expectedVirtualLocalStock = itemTestee.getVirtualLocalStock() - quantity;

        doAnswer(invocationOnMock -> {
            final Item item = invocationOnMock.getArgument(0);

            assertEquals(expectedLocalStock, item.getLocalStock().intValue());
            assertEquals(expectedVirtualLocalStock, item.getVirtualLocalStock().intValue());

            itemTestee.setLocalStock(item.getLocalStock());
            itemTestee.setVirtualLocalStock(item.getVirtualLocalStock());

            return null;
        }).when(this.itemPersistor).updateStock(any(Item.class));

        this.itemManager.updateItemStock(itemDTO, quantity);
    }

    @Test
    public void updateItemStock_WhenValidItemAndQuantity_ThenUpdateStockOfAssociatedItem_WithReorder() throws StockException {
        final Item itemTestee = this.itemTestees.get(1);
        final ItemDTO itemDTO = new ItemWrapper().dtoFromEntity(itemTestee);
        final int quantity = 5;
        final int expectedLocalStock = (itemTestee.getLocalStock() - quantity) > 0 ? (itemTestee.getLocalStock() - quantity) : 0;
        final int expectedVirtualLocalStock = itemTestee.getVirtualLocalStock() - quantity;
        final int expectedReorderedQuantity = 0;
        final int expectedReorderQuantity = itemTestee.getMinLocalStock() - (expectedVirtualLocalStock + expectedReorderedQuantity);

        doAnswer(invocationOnMock -> {
            final Item item = invocationOnMock.getArgument(0);

            assertEquals(expectedLocalStock, item.getLocalStock().intValue());
            assertEquals(expectedVirtualLocalStock, item.getVirtualLocalStock().intValue());

            itemTestee.setLocalStock(item.getLocalStock());
            itemTestee.setVirtualLocalStock(item.getVirtualLocalStock());

            return null;
        }).when(this.itemPersistor).updateStock(any(Item.class));

        doAnswer(invocationOnMock -> {
            final Reorder reorder = invocationOnMock.getArgument(0);

            assertEquals(itemTestee.getId(), reorder.getItemId());
            assertEquals(expectedReorderQuantity, reorder.getQuantity().intValue());

            return null;
        }).when(this.reorderPersistor).save(any(Reorder.class));

        doAnswer(invocationOnMock -> {
            final String artNr = invocationOnMock.getArgument(0);
            final int count = invocationOnMock.getArgument(1);

            assertEquals(itemTestee.getArtNr(), artNr);
            assertEquals(expectedReorderQuantity, count);

            return count;
        }).when(this.stock).orderItem(any(String.class), any(Integer.class));

        this.itemManager.updateItemStock(itemDTO, quantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateItemStock_WhenInvalidItem_ThrowException() {
        final int quantity = 5;
        this.itemManager.updateItemStock(null, quantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateItemStock_WhenInvalidQuantity_ThrowException() {
        final Item itemTestee = this.itemTestees.get(2);
        final ItemDTO itemDTO = new ItemWrapper().dtoFromEntity(itemTestee);
        final int quantity = 0;
        this.itemManager.updateItemStock(itemDTO, quantity);
    }

    @Test
    public void updateMinLocalStock_WhenValidItemAndMinLocalStock_ThenUpdateMinLocalStockOfAssociatedItem() {
        final Item itemTestee = this.itemTestees.get(1);
        final ItemDTO itemDTO = new ItemWrapper().dtoFromEntity(itemTestee);
        final int newMinLocalStock = 5;

        doAnswer(invocationOnMock -> {
            final Item item = invocationOnMock.getArgument(0);

            assertEquals(newMinLocalStock, item.getMinLocalStock().intValue());

            return null;
        }).when(this.itemPersistor).updateStock(any(Item.class));

        this.itemManager.updateMinLocalStock(itemDTO, newMinLocalStock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateMinLocalStock_WhenInvalidItem_ThrowException() {
        final int newMinLocalStock = 5;
        this.itemManager.updateMinLocalStock(null, newMinLocalStock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateMinLocalStock_WhenInvalidMinLocalStock_ThrowException() {
        final Item itemTestee = this.itemTestees.get(1);
        final ItemDTO itemDTO = new ItemWrapper().dtoFromEntity(itemTestee);
        final int newMinLocalStock = 0;
        this.itemManager.updateMinLocalStock(itemDTO, newMinLocalStock);
    }

    @Test
    public void reorderItem_WhenValidItemAndQuantity_ThenInitiateReorderOfItem() throws StockException {
        final Item itemTestee = this.itemTestees.get(0);
        final int quantity = 5;

        doAnswer(invocationOnMock -> {
            final Reorder reorder = invocationOnMock.getArgument(0);

            assertEquals(itemTestee.getId(), reorder.getItemId());
            assertEquals(quantity, reorder.getQuantity().intValue());

            return null;
        }).when(this.reorderPersistor).save(any(Reorder.class));

        doAnswer(invocationOnMock -> {
            final String artNr = invocationOnMock.getArgument(0);
            final int count = invocationOnMock.getArgument(1);

            assertEquals(itemTestee.getArtNr(), artNr);
            assertEquals(quantity, count);

            return count;
        }).when(this.stock).orderItem(any(String.class), any(Integer.class));

        this.itemManager.reorderItem(itemTestee.getId(), quantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reorderItem_WhenInvalidQuantity_ThrowException() throws StockException {
        final Item itemTestee = this.itemTestees.get(0);
        final int quantity = 0;

        this.itemManager.reorderItem(itemTestee.getId(), quantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reorderItem_WhenInvalidItemId_ThrowException() throws StockException {
        final int invalidItemId = -1;
        final int quantity = 5;

        this.itemManager.reorderItem(invalidItemId, quantity);
    }

    @Test
    public void refillItemStock_WhenValidItemAndQuantity_ThenRefillStockOfItem() {
        final Item itemTestee = this.itemTestees.get(1);
        final ItemDTO itemDTO = new ItemWrapper().dtoFromEntity(itemTestee);
        final int quantity = 5;
        final int expectedVirtualLocalStock = itemTestee.getVirtualLocalStock() + quantity;
        final int expectedLocalStock = (expectedVirtualLocalStock >= 0) ? expectedVirtualLocalStock : 0;

        doAnswer(invocationOnMock -> {
            final Item item = invocationOnMock.getArgument(0);

            assertEquals(expectedLocalStock, item.getLocalStock().intValue());
            assertEquals(expectedVirtualLocalStock, item.getVirtualLocalStock().intValue());

            return null;
        }).when(this.itemPersistor).updateStock(any(Item.class));

        this.itemManager.refillItemStock(itemDTO, quantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refillItemStock_WhenInvalidItem_ThrowException() {
        final int quantity = 5;

        this.itemManager.refillItemStock(null, quantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refillItemStock_WhenInvalidQuantity_ThrowException() {
        final Item itemTestee = this.itemTestees.get(0);
        final ItemDTO itemDTO = new ItemWrapper().dtoFromEntity(itemTestee);
        final int quantity = 0;

        this.itemManager.refillItemStock(itemDTO, quantity);
    }

    private UserDTO getUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, UserRoles.SALESPERSON.getRole());
        return new UserDTO(1, userRoleDTO, "maxmuster");
    }

    private UserDTO getNotAuthorizedUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(2, UserRoles.BRANCHMANAGER.getRole());
        return new UserDTO(2, userRoleDTO, "fritzmeier");
    }

    private List<Item> getItemTestees() {
        final List<Item> itemTestees = new ArrayList<>();

        final Item testee1 = new Item();
        testee1.setId(1);
        testee1.setName("testee1");
        testee1.setArtNr("testee1ArtNr");
        testee1.setPrice(7);
        testee1.setLocalStock(10);
        testee1.setMinLocalStock(5);
        testee1.setVirtualLocalStock(5);
        testee1.setReordersById(new ArrayList<>());
        itemTestees.add(testee1);

        createReorderForItemTestee(testee1, 1, 5, true);

        final Item testee2 = new Item();
        testee2.setId(2);
        testee2.setName("testee2");
        testee2.setArtNr("testee2ArtNr");
        testee2.setPrice(14);
        testee2.setLocalStock(20);
        testee2.setMinLocalStock(10);
        testee2.setVirtualLocalStock(10);
        testee2.setReordersById(new ArrayList<>());
        itemTestees.add(testee2);

        createReorderForItemTestee(testee2, 2, 5, true);

        final Item testee3 = new Item();
        testee3.setId(3);
        testee3.setName("testee3");
        testee3.setArtNr("testee3ArtNr");
        testee3.setPrice(28);
        testee3.setLocalStock(30);
        testee3.setMinLocalStock(20);
        testee3.setVirtualLocalStock(20);
        testee3.setReordersById(new ArrayList<>());
        itemTestees.add(testee3);

        createReorderForItemTestee(testee3, 3, 10, false);

        return itemTestees;
    }

    private void createReorderForItemTestee(final Item itemTestee, final int reorderId, final int quantity, final boolean delivered) {
        final Reorder testee = new Reorder();
        testee.setId(reorderId);
        testee.setQuantity(quantity);
        testee.setReorderDate(Timestamp.from(Instant.now()));
        testee.setDelivered((delivered) ? Timestamp.from(Instant.now()) : null);
        testee.setItemId(itemTestee.getId());
        testee.setItemByItemId(itemTestee);
        itemTestee.getReordersById().add(testee);
    }
}
