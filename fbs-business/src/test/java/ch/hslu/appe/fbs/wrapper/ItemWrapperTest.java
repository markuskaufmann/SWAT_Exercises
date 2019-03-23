package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.model.db.Bill;
import ch.hslu.appe.fbs.model.db.Item;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class ItemWrapperTest {

    @Test
    public void dtoFromEntity_when_itemEntity_then_itemDTO() {
        // arrange
        final ItemWrapper itemWrapper = new ItemWrapper();

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

        //act
        final ItemDTO itemDTO = itemWrapper.dtoFromEntity(itemEntity);

        //assert
        Assert.assertEquals(itemId, itemDTO.getId().intValue());
        Assert.assertEquals(itemName, itemDTO.getName());
        Assert.assertEquals(artNr, itemDTO.getArtNr());
        Assert.assertEquals(price, itemDTO.getPrice().intValue());
        Assert.assertEquals(localStock, itemDTO.getLocalStock().intValue());
        Assert.assertEquals(minLocalStock, itemDTO.getMinLocalStock().intValue());
        Assert.assertEquals(virtualLocalStock, itemDTO.getVirtualLocalStock().intValue());
    }

    @Test
    public void entityFromDTO_when_itemDTO_then_itemEntity() {
        // arrange
        final ItemWrapper itemWrapper = new ItemWrapper();

        final int itemId = 1;
        final String itemName = "TestItem";
        final String artNr = "A10000";
        final int price = 10;
        final int localStock = 100;
        final int minLocalStock = 20;
        final int virtualLocalStock = 5;
        final ItemDTO itemDTO = new ItemDTO(itemId, itemName, price, artNr, localStock, minLocalStock, virtualLocalStock);

        //act
        final Item itemEntity = itemWrapper.entityFromDTO(itemDTO);

        //assert
        Assert.assertEquals(itemId, itemEntity.getId().intValue());
        Assert.assertEquals(itemName, itemEntity.getName());
        Assert.assertEquals(artNr, itemEntity.getArtNr());
        Assert.assertEquals(price, itemEntity.getPrice().intValue());
        Assert.assertEquals(localStock, itemEntity.getLocalStock().intValue());
        Assert.assertEquals(minLocalStock, itemEntity.getMinLocalStock().intValue());
        Assert.assertEquals(virtualLocalStock, itemEntity.getVirtualLocalStock().intValue());
    }
}
