package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.*;
import ch.hslu.appe.fbs.model.db.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public final class OrderWrapperTest {

    @Test
    public void dtoFromEntity_when_orderEntity_then_orderDTO() {
        // arrange
        final OrderWrapper orderWrapper = new OrderWrapper();

        final int orderId = 1;
        final Timestamp dateTime = Timestamp.from(Instant.now());
        final int orderState = 2;
        final int customerId = 3;
        final int userId = 4;
        final int itemId = 5;

        final Customer customerEntity = new Customer();
        customerEntity.setId(customerId);
        customerEntity.setPlz(0);

        final UserRole userRoleEntity = new UserRole();
        userRoleEntity.setId(0);
        final User userEntity = new User();
        userEntity.setId(userId);
        userEntity.setUserRoleByUserRole(userRoleEntity);

        final OrderState orderStateEntity = new OrderState();
        orderStateEntity.setId(orderState);

        final Item itemEntity = new Item();
        itemEntity.setId(itemId);

        final List<OrderItem> orderItemEntities = new ArrayList<>();
        final OrderItem orderItemEntity = new OrderItem();
        orderItemEntity.setItemByItemId(itemEntity);
        orderItemEntities.add(orderItemEntity);

        final Order orderEntity = new Order();
        orderEntity.setId(orderId);
        orderEntity.setDateTime(dateTime);
        orderEntity.setOrderState(orderState);
        orderEntity.setCustomerId(customerId);
        orderEntity.setUserId(userId);
        orderEntity.setOrderItemsById(orderItemEntities);
        orderEntity.setCustomerByCustomerId(customerEntity);
        orderEntity.setUserByUserId(userEntity);
        orderEntity.setOrderStateByOrderState(orderStateEntity);

        //act
        final OrderDTO orderDTO = orderWrapper.dtoFromEntity(orderEntity);
        final OrderItemDTO orderItemDTO = new ArrayList<>(orderDTO.getOrderItems()).get(0);

        //assert
        Assert.assertEquals(orderId, orderDTO.getId());
        Assert.assertEquals(dateTime, orderDTO.getDateTime());
        Assert.assertEquals(orderState, orderDTO.getOrderState().getId());
        Assert.assertEquals(customerId, orderDTO.getCustomer().getId());
        Assert.assertEquals(userId, orderDTO.getUser().getId());
        Assert.assertEquals(orderItemEntities.size(), orderDTO.getOrderItems().size());
        Assert.assertEquals(itemId, orderItemDTO.getItemByItemId().getId().intValue());
    }

    @Test
    public void entityFromDTO_when_orderDTO_then_orderEntity() {
        // arrange
        final OrderWrapper orderWrapper = new OrderWrapper();

        final int orderId = 1;
        final Timestamp dateTime = Timestamp.from(Instant.now());
        final int orderState = 2;
        final int customerId = 3;
        final int userId = 4;

        final CustomerDTO customerDTO = new CustomerDTO(customerId, null, null, 0, null, null);
        final UserDTO userDTO = new UserDTO(userId, null, null);
        final OrderStateDTO orderStateDTO = new OrderStateDTO(orderState, null);
        final OrderDTO orderDTO = new OrderDTO(orderId, customerDTO, userDTO, orderStateDTO, dateTime, Collections.emptyList());

        //act
        final Order orderEntity = orderWrapper.entityFromDTO(orderDTO);

        //assert
        Assert.assertEquals(orderId, orderEntity.getId().intValue());
        Assert.assertEquals(dateTime, orderEntity.getDateTime());
        Assert.assertEquals(orderState, orderEntity.getOrderState().intValue());
        Assert.assertEquals(customerId, orderEntity.getCustomerId().intValue());
        Assert.assertEquals(userId, orderEntity.getUserId().intValue());
    }
}
