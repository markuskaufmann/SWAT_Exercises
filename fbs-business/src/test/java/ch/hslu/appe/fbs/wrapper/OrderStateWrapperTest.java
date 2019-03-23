package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.OrderItemDTO;
import ch.hslu.appe.fbs.common.dto.OrderStateDTO;
import ch.hslu.appe.fbs.model.db.OrderItem;
import ch.hslu.appe.fbs.model.db.OrderState;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class OrderStateWrapperTest {

    @Test
    public void dtoFromEntity_when_orderStateEntity_then_orderStateDTO() {
        // arrange
        final OrderStateWrapper orderStateWrapper = new OrderStateWrapper();

        final int orderStateId = 1;
        final String orderState = "open";
        final OrderState orderStateEntity = new OrderState();
        orderStateEntity.setId(orderStateId);
        orderStateEntity.setState(orderState);

        //act
        final OrderStateDTO orderStateDTO = orderStateWrapper.dtoFromEntity(orderStateEntity);

        //assert
        Assert.assertEquals(orderStateId, orderStateDTO.getId());
        Assert.assertEquals(orderState, orderStateDTO.getOrderState());
    }

    @Test
    public void entityFromDTO_when_orderStateDTO_then_orderStateEntity() {
        // arrange
        final OrderStateWrapper orderStateWrapper = new OrderStateWrapper();

        final int orderStateId = 1;
        final String orderState = "open";
        final OrderStateDTO orderStateDTO = new OrderStateDTO(orderStateId, orderState);

        //act
        final OrderState orderStateEntity = orderStateWrapper.entityFromDTO(orderStateDTO);

        //assert
        Assert.assertEquals(orderStateId, orderStateEntity.getId().intValue());
        Assert.assertEquals(orderState, orderStateEntity.getState());
    }
}
