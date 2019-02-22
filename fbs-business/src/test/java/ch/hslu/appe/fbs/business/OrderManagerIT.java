package ch.hslu.appe.fbs.business;

import ch.hslu.appe.fbs.business.order.OrderManager;
import ch.hslu.appe.fbs.business.order.OrderManagerFactory;
import ch.hslu.appe.fbs.common.dto.*;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.data.customer.CustomerPersistor;
import ch.hslu.appe.fbs.data.customer.CustomerPersistorFactory;
import ch.hslu.appe.fbs.data.orderitem.OrderItemPersistor;
import ch.hslu.appe.fbs.data.orderitem.OrderItemPersistorFactory;
import ch.hslu.appe.fbs.data.orderstate.OrderStatePersistor;
import ch.hslu.appe.fbs.data.orderstate.OrderStatePersistorFactory;
import ch.hslu.appe.fbs.data.orderstate.OrderStates;
import ch.hslu.appe.fbs.data.user.UserPersistor;
import ch.hslu.appe.fbs.data.user.UserPersistorFactory;
import ch.hslu.appe.fbs.model.db.Customer;
import ch.hslu.appe.fbs.model.db.OrderState;
import ch.hslu.appe.fbs.wrapper.CustomerWrapper;
import ch.hslu.appe.fbs.wrapper.OrderItemWrapper;
import ch.hslu.appe.fbs.wrapper.OrderStateWrapper;
import ch.hslu.appe.fbs.wrapper.UserWrapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.*;

public class OrderManagerIT {

    private final OrderManager orderManager;
    private final CustomerPersistor customerPersistor;
    private final UserPersistor userPersistor;
    private final OrderStatePersistor orderStatePersistor;
    private final OrderItemPersistor orderItemPersistor;
    private final CustomerWrapper customerWrapper;
    private final UserWrapper userWrapper;
    private final OrderStateWrapper orderStateWrapper;
    private final OrderItemWrapper orderItemWrapper;
    private CustomerDTO testCustomer;
    private UserDTO testUser;
    private OrderStateDTO testOrderState;
    private List<OrderItemDTO> testOrderItems;

    public OrderManagerIT() {
        this.orderManager = OrderManagerFactory.getOrderManager();
        this.customerPersistor = CustomerPersistorFactory.createCustomerPersistor();
        this.userPersistor = UserPersistorFactory.createUserPersistor();
        this.orderStatePersistor = OrderStatePersistorFactory.createOrderStatePersistor();
        this.orderItemPersistor = OrderItemPersistorFactory.createOrderItemPersistor();
        this.customerWrapper = new CustomerWrapper();
        this.userWrapper = new UserWrapper();
        this.orderStateWrapper = new OrderStateWrapper();
        this.orderItemWrapper = new OrderItemWrapper();
    }

    @Before
    public void init() {
        this.createTestCustomer();
        List<Customer> customers = this.customerPersistor.getAll();
        this.testCustomer = this.customerWrapper.dtoFromEntity(customers.get(customers.size() - 1));
        this.testUser = this.userWrapper.dtoFromEntity(this.userPersistor.getByName("zhadm").get());
        Optional<OrderState> optionalOrderState = this.orderStatePersistor.getByState(OrderStates.IN_PROGRESS.getState());
        optionalOrderState.ifPresent(orderState -> this.testOrderState = this.orderStateWrapper.dtoFromEntity(orderState));
        this.testOrderItems = new ArrayList<>();
        this.testOrderItems.add(this.orderItemWrapper.dtoFromEntity(this.orderItemPersistor.getAll().get(0)));
    }

    @Test
    public void createOrder() throws UserNotAuthorisedException {
        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        date = cal.getTime();

        Timestamp timestamp = new Timestamp(date.getTime());

        OrderDTO orderDTO = new OrderDTO(-1,
                this.testCustomer,
                this.testUser,
                this.testOrderState,
                timestamp,
                this.testOrderItems);
        this.orderManager.createOrder(orderDTO, this.testUser);
        OrderDTO persistedOrder = null;
        for (OrderDTO order : this.orderManager.getOrders(this.testCustomer, this.testUser)) {
            if (order.getDateTime().equals(timestamp)) {
                persistedOrder = order;
                break;
            }
        }
        Assert.assertEquals(persistedOrder, orderDTO);
    }

    @After
    public void cleanup() {
        this.customerPersistor.delete(this.customerWrapper.entityFromDTO(this.testCustomer));
    }

    private void createTestCustomer() {
        Customer customer = new Customer();
        customer.setPrename("Peter");
        customer.setSurname("Muster");
        customer.setAdress("Musterstrasse 12");
        customer.setCity("Musterhausen");
        customer.setPlz(1234);

        this.customerPersistor.save(customer);
    }
}
