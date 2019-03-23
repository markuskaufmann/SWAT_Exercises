package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.common.dto.ReorderDTO;
import ch.hslu.appe.fbs.model.db.Item;
import ch.hslu.appe.fbs.model.db.Reorder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.Instant;

@RunWith(MockitoJUnitRunner.class)
public final class ReorderWrapperTest {

    @Test
    public void dtoFromEntity_when_reorderEntity_then_reorderDTO() {
        // arrange
        final ReorderWrapper reorderWrapper = new ReorderWrapper();

        final int itemId = 1;
        final String itemName = "TestItem";
        final String artNr = "A10000";
        final int price = 10;
        final int localStock = 100;
        final int minLocalStock = 20;
        final int virtualLocalStock = 5;
        final Item itemEntity = new Item();
        itemEntity.setId(itemId);
        itemEntity.setName(itemName);
        itemEntity.setArtNr(artNr);
        itemEntity.setPrice(price);
        itemEntity.setLocalStock(localStock);
        itemEntity.setMinLocalStock(minLocalStock);
        itemEntity.setVirtualLocalStock(virtualLocalStock);

        final int reorderId = 2;
        final int quantity = 15;
        final Timestamp reorderDate = Timestamp.from(Instant.now());
        final Timestamp delivered = Timestamp.from(Instant.now());
        final Reorder reorderEntity = new Reorder();
        reorderEntity.setId(reorderId);
        reorderEntity.setItemId(itemId);
        reorderEntity.setQuantity(quantity);
        reorderEntity.setReorderDate(reorderDate);
        reorderEntity.setDelivered(delivered);
        reorderEntity.setItemByItemId(itemEntity);

        //act
        final ReorderDTO reorderDTO = reorderWrapper.dtoFromEntity(reorderEntity);
        final ItemDTO itemDTO = reorderDTO.getItem();

        //assert
        Assert.assertEquals(reorderId, reorderDTO.getId());
        Assert.assertEquals(quantity, reorderDTO.getQuantity());
        Assert.assertEquals(reorderDate, reorderDTO.getReorderDate());
        Assert.assertEquals(delivered, reorderDTO.getDelivered());
        Assert.assertEquals(itemId, itemDTO.getId().intValue());
        Assert.assertEquals(itemName, itemDTO.getName());
        Assert.assertEquals(artNr, itemDTO.getArtNr());
        Assert.assertEquals(price, itemDTO.getPrice().intValue());
        Assert.assertEquals(localStock, itemDTO.getLocalStock().intValue());
        Assert.assertEquals(minLocalStock, itemDTO.getMinLocalStock().intValue());
        Assert.assertEquals(virtualLocalStock, itemDTO.getVirtualLocalStock().intValue());
    }

    @Test
    public void entityFromDTO_when_reorderDTO_then_reorderEntity() {
        // arrange
        final ReorderWrapper reorderWrapper = new ReorderWrapper();

        final int itemId = 1;
        final String itemName = "TestItem";
        final String artNr = "A10000";
        final int price = 10;
        final int localStock = 100;
        final int minLocalStock = 20;
        final int virtualLocalStock = 5;
        final ItemDTO itemDTO = new ItemDTO(itemId, itemName, price, artNr, localStock, minLocalStock, virtualLocalStock);

        final int reorderId = 2;
        final int quantity = 15;
        final Timestamp reorderDate = Timestamp.from(Instant.now());
        final Timestamp delivered = Timestamp.from(Instant.now());
        final ReorderDTO reorderDTO = new ReorderDTO(reorderId, itemDTO, quantity, reorderDate, delivered);

        //act
        final Reorder reorderEntity = reorderWrapper.entityFromDTO(reorderDTO);

        //assert
        Assert.assertEquals(reorderId, reorderEntity.getId().intValue());
        Assert.assertEquals(quantity, reorderEntity.getQuantity().intValue());
        Assert.assertEquals(reorderDate, reorderEntity.getReorderDate());
        Assert.assertEquals(delivered, reorderEntity.getDelivered());
        Assert.assertEquals(itemId, reorderEntity.getItemId().intValue());
    }
}
