package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.common.dto.OrderItemDTO;
import ch.hslu.appe.fbs.model.db.Bill;
import ch.hslu.appe.fbs.model.db.Item;
import ch.hslu.appe.fbs.model.db.OrderItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class OrderItemWrapperTest {

    @Test
    public void dtoFromEntity_when_orderItemEntity_then_orderItemDTO() {
        // arrange
        final OrderItemWrapper orderItemWrapper = new OrderItemWrapper();

        final int itemId = 1;
        final Item itemEntity = new Item();
        itemEntity.setId(itemId);

        final int price = 10;
        final int quantity = 5;
        final OrderItem orderItemEntity = new OrderItem();
        orderItemEntity.setPrice(price);
        orderItemEntity.setQuantity(quantity);
        orderItemEntity.setItemByItemId(itemEntity);

        //act
        final OrderItemDTO orderItemDTO = orderItemWrapper.dtoFromEntity(orderItemEntity);
        final ItemDTO itemDTO = orderItemDTO.getItemByItemId();

        //assert
        Assert.assertEquals(price, orderItemDTO.getPrice().intValue());
        Assert.assertEquals(quantity, orderItemDTO.getQuantity().intValue());
        Assert.assertEquals(itemId, itemDTO.getId().intValue());
    }

    @Test
    public void entityFromDTO_when_orderItemDTO_then_orderItemEntity() {
        // arrange
        final OrderItemWrapper orderItemWrapper = new OrderItemWrapper();

        final int itemId = 1;
        final ItemDTO itemDTO = new ItemDTO(itemId, null, null, null, 0, 0, 0);

        final int price = 10;
        final int quantity = 5;
        final OrderItemDTO orderItemDTO = new OrderItemDTO(price, quantity, itemDTO);

        //act
        final OrderItem orderItemEntity = orderItemWrapper.entityFromDTO(orderItemDTO);

        //assert
        Assert.assertEquals(price, orderItemEntity.getPrice().intValue());
        Assert.assertEquals(quantity, orderItemEntity.getQuantity().intValue());
        Assert.assertEquals(itemId, orderItemEntity.getItemId().intValue());
    }
}
