package ch.hslu.appe.fbs.business.order;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifier;
import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifierFactory;
import ch.hslu.appe.fbs.business.bill.BillManager;
import ch.hslu.appe.fbs.business.item.ItemManager;
import ch.hslu.appe.fbs.common.dto.*;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.data.order.OrderPersistor;
import ch.hslu.appe.fbs.data.orderitem.OrderItemPersistor;
import ch.hslu.appe.fbs.data.orderstate.OrderStatePersistor;
import ch.hslu.appe.fbs.data.orderstate.OrderStates;
import ch.hslu.appe.fbs.business.authorisation.model.UserRoles;
import ch.hslu.appe.fbs.model.db.*;
import ch.hslu.appe.fbs.wrapper.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public final class OrderManagerImplTest {

    private static AuthorisationVerifier authorisationVerifier;

    @Mock
    private OrderPersistor orderPersistor;

    @Mock
    private OrderItemPersistor orderItemPersistor;

    @Mock
    private OrderStatePersistor orderStatePersistor;

    @Mock
    private ItemManager itemManager;

    @Mock
    private BillManager billManager;

    private OrderManager orderManager;

    private List<Order> orderTestees;

    private UserDTO userTestee;
    private UserDTO unauthorizedUserTestee;

    @BeforeClass
    public static void setUpClass() {
        authorisationVerifier = AuthorisationVerifierFactory.createAuthorisationVerifier();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.orderManager = new OrderManagerImpl(authorisationVerifier, this.orderPersistor, this.orderItemPersistor,
                this.orderStatePersistor, this.itemManager, this.billManager);
        this.orderTestees = getOrderTestees();
        this.userTestee = getUserTestee();
        this.unauthorizedUserTestee = getUnauthorizedUserTestee();
        when(this.orderPersistor.getAll()).thenReturn(this.orderTestees);
        when(this.orderPersistor.getById(this.orderTestees.get(0).getId())).thenReturn(Optional.of(this.orderTestees.get(0)));
        when(this.orderPersistor.getById(this.orderTestees.get(1).getId())).thenReturn(Optional.of(this.orderTestees.get(1)));
        when(this.orderPersistor.getById(this.orderTestees.get(2).getId())).thenReturn(Optional.of(this.orderTestees.get(2)));
        when(this.orderPersistor.getById(this.orderTestees.get(3).getId())).thenReturn(Optional.of(this.orderTestees.get(3)));
        when(this.orderPersistor.getById(this.orderTestees.get(4).getId())).thenReturn(Optional.of(this.orderTestees.get(4)));
        when(this.orderPersistor.getByCustomer(this.orderTestees.get(0).getCustomerId()))
                .thenReturn(Arrays.asList(this.orderTestees.get(0), this.orderTestees.get(1)));
        when(this.orderPersistor.getByCustomer(this.orderTestees.get(2).getCustomerId()))
                .thenReturn(Collections.singletonList(this.orderTestees.get(2)));
        when(this.orderPersistor.getByCustomerAndOrderState(this.orderTestees.get(0).getCustomerId(),
                                                            this.orderTestees.get(0).getOrderState()))
                .thenReturn(Collections.singletonList(this.orderTestees.get(0)));
        when(this.orderPersistor.getByCustomerAndOrderState(this.orderTestees.get(3).getCustomerId(),
                                                            this.orderTestees.get(3).getOrderState()))
                .thenReturn(Arrays.asList(this.orderTestees.get(3), this.orderTestees.get(4)));
    }

    @Test
    public void getAllOrders_WhenOrdersExist_ThenReturnThemAll() throws UserNotAuthorisedException {
        final List<OrderDTO> ordersDTO = this.orderManager.getAllOrders(this.userTestee);

        assertEquals(this.orderTestees.size(), ordersDTO.size());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void getAllOrders_WhenOrdersExistButUserNotAuthorized_ThrowException() throws UserNotAuthorisedException {
        this.orderManager.getAllOrders(this.unauthorizedUserTestee);
    }

    @Test
    public void getOrders_WhenValidCustomerId_ThenReturnAssociatedOrders() throws UserNotAuthorisedException {
        int customerId = 2;
        int expectedOrders = 2;
        List<OrderDTO> ordersDTO = this.orderManager.getOrders(customerId, this.userTestee);

        assertEquals(expectedOrders, ordersDTO.size());

        customerId = 3;
        expectedOrders = 1;
        ordersDTO = this.orderManager.getOrders(customerId, this.userTestee);

        assertEquals(expectedOrders, ordersDTO.size());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void getOrders_WhenValidCustomerIdButNotAuthorizedUser_ThrowException() throws UserNotAuthorisedException {
        final int customerId = this.orderTestees.get(0).getCustomerId();
        this.orderManager.getOrders(customerId, this.unauthorizedUserTestee);
    }

    @Test
    public void getOrders_WhenInvalidCustomerId_ThenReturnEmptyList() throws UserNotAuthorisedException {
        final int customerId = -1;
        final List<OrderDTO> ordersDTO = this.orderManager.getOrders(customerId, this.userTestee);

        assertEquals(0, ordersDTO.size());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void getOrders_WhenInvalidCustomerIdAndNotAuthorizedUser_ThrowException() throws UserNotAuthorisedException {
        final int customerId = -1;
        this.orderManager.getOrders(customerId, this.unauthorizedUserTestee);
    }

    @Test
    public void getOrders_WhenValidCustomerDTO_ThenReturnAssociatedOrders() throws UserNotAuthorisedException {
        CustomerDTO customerDTO = new CustomerWrapper().dtoFromEntity(this.orderTestees.get(0).getCustomerByCustomerId());
        int expectedOrders = 2;
        List<OrderDTO> ordersDTO = this.orderManager.getOrders(customerDTO, this.userTestee);

        assertEquals(expectedOrders, ordersDTO.size());

        customerDTO = new CustomerWrapper().dtoFromEntity(this.orderTestees.get(2).getCustomerByCustomerId());
        expectedOrders = 1;
        ordersDTO = this.orderManager.getOrders(customerDTO, this.userTestee);

        assertEquals(expectedOrders, ordersDTO.size());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void getOrders_WhenValidCustomerDTOButNotAuthorizedUser_ThrowException() throws UserNotAuthorisedException {
        final CustomerDTO customerDTO = new CustomerWrapper().dtoFromEntity(this.orderTestees.get(0).getCustomerByCustomerId());
        this.orderManager.getOrders(customerDTO, this.unauthorizedUserTestee);
    }

    @Test
    public void getOrders_WhenInvalidCustomerDTO_ThenReturnEmptyList() throws UserNotAuthorisedException {
        final CustomerDTO customerDTO = new CustomerDTO(-1, "", "", 0, "", "");
        final int expectedOrders = 0;
        final List<OrderDTO> ordersDTO = this.orderManager.getOrders(customerDTO, this.userTestee);

        assertEquals(expectedOrders, ordersDTO.size());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void getOrders_WhenInvalidCustomerDTOAndNotAuthorizedUser_ThrowException() throws UserNotAuthorisedException {
        final CustomerDTO customerDTO = new CustomerDTO(-1, "", "", 0, "", "");
        this.orderManager.getOrders(customerDTO, this.unauthorizedUserTestee);
    }

    @Test
    public void getOrdersByOrderState_WhenValidCustomerIdAndOrderStateDTO_ThenReturnAssociatedOrders() throws UserNotAuthorisedException {
        int customerId = this.orderTestees.get(0).getCustomerId();
        OrderStateDTO orderStateDTO = new OrderStateWrapper().dtoFromEntity(this.orderTestees.get(0).getOrderStateByOrderState());
        int expectedOrders = 1;
        List<OrderDTO> ordersDTO = this.orderManager.getOrdersByOrderState(customerId, orderStateDTO, this.userTestee);

        assertEquals(expectedOrders, ordersDTO.size());

        customerId = this.orderTestees.get(3).getCustomerId();
        orderStateDTO = new OrderStateWrapper().dtoFromEntity(this.orderTestees.get(3).getOrderStateByOrderState());
        expectedOrders = 2;
        ordersDTO = this.orderManager.getOrdersByOrderState(customerId, orderStateDTO, this.userTestee);

        assertEquals(expectedOrders, ordersDTO.size());
    }

    @Test
    public void getOrdersByOrderState_WhenInvalidCustomerIdButValidOrderStateDTO_ThenReturnEmptyList() throws UserNotAuthorisedException {
        final int customerId = -1;
        final OrderStateDTO orderStateDTO = new OrderStateWrapper().dtoFromEntity(this.orderTestees.get(0).getOrderStateByOrderState());
        final int expectedOrders = 0;
        final List<OrderDTO> ordersDTO = this.orderManager.getOrdersByOrderState(customerId, orderStateDTO, this.userTestee);

        assertEquals(expectedOrders, ordersDTO.size());
    }

    @Test
    public void getOrdersByOrderState_WhenValidCustomerIdButInvalidOrderStateDTO_ThenReturnEmptyList() throws UserNotAuthorisedException {
        final int customerId = this.orderTestees.get(0).getCustomerId();
        final OrderStateDTO orderStateDTO = new OrderStateDTO(-1, "randomOrderState");
        final int expectedOrders = 0;
        final List<OrderDTO> ordersDTO = this.orderManager.getOrdersByOrderState(customerId, orderStateDTO, this.userTestee);

        assertEquals(expectedOrders, ordersDTO.size());
    }

    @Test
    public void getOrdersByOrderState_WhenInvalidCustomerIdAndInvalidOrderStateDTO_ThenReturnEmptyList() throws UserNotAuthorisedException {
        final int customerId = -1;
        final OrderStateDTO orderStateDTO = new OrderStateDTO(-1, "randomOrderState");
        final int expectedOrders = 0;
        final List<OrderDTO> ordersDTO = this.orderManager.getOrdersByOrderState(customerId, orderStateDTO, this.userTestee);

        assertEquals(expectedOrders, ordersDTO.size());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void getOrdersByOrderState_WhenValidCustomerIdAndOrderStateDTOButNotAuthorizedUser_ThrowException()
            throws UserNotAuthorisedException {
        int customerId = this.orderTestees.get(0).getCustomerId();
        OrderStateDTO orderStateDTO = new OrderStateWrapper().dtoFromEntity(this.orderTestees.get(0).getOrderStateByOrderState());
        this.orderManager.getOrdersByOrderState(customerId, orderStateDTO, this.unauthorizedUserTestee);
    }

    @Test
    public void createOrder_WhenValidOrderDTO_ThenCreateNewOrder() throws UserNotAuthorisedException {
        final Order order = createOrderTestee(10, 10, 10, 10);
        final List<OrderItemDTO> orderItemsDTO = generateOrderItemDTOs(2);
        final CustomerDTO customerDTO = new CustomerWrapper().dtoFromEntity(order.getCustomerByCustomerId());
        final UserDTO userDTO = new UserWrapper().dtoFromEntity(order.getUserByUserId());
        final OrderStateDTO orderStateDTO = new OrderStateWrapper().dtoFromEntity(order.getOrderStateByOrderState());
        final OrderDTO orderDTO = new OrderDTO(order.getId(), customerDTO, userDTO, orderStateDTO, order.getDateTime(), orderItemsDTO);
        final AtomicInteger orderItemsPrice = new AtomicInteger();
        orderItemsDTO.forEach(orderItemDTO -> orderItemsPrice.addAndGet(orderItemDTO.getPrice()));

        doAnswer(invocationOnMock -> {
            final Order argOrder = invocationOnMock.getArgument(0);
            this.orderTestees.add(argOrder);
            return null;
        }).when(this.orderPersistor).save(any(Order.class));
        doAnswer(invocationOnMock -> {
            final int orderId = invocationOnMock.getArgument(0);
            final int price = invocationOnMock.getArgument(1);

            assertEquals(order.getId().intValue(), orderId);
            assertEquals(orderItemsPrice.get(), price);

            final BillDTO billDTO = new BillDTO(-1, orderId, price);
            return new BillWrapper().entityFromDTO(billDTO);
        }).when(this.billManager).generateBill(any(Integer.class), any(Integer.class));
        doAnswer(invocationOnMock -> {
            final Order updateOrder = invocationOnMock.getArgument(0);
            final Bill bill = new ArrayList<>(updateOrder.getBillsById()).get(0);

            assertEquals(1, updateOrder.getBillsById().size());
            assertEquals(order.getId(), bill.getOrderId());
            assertEquals(orderItemsPrice.get(), bill.getPrice().intValue());

            return null;
        }).when(this.orderPersistor).update(any(Order.class));
        when(this.itemManager.getItem(orderItemsDTO.get(0).getItemByItemId().getId())).thenReturn(orderItemsDTO.get(0).getItemByItemId());
        when(this.itemManager.getItem(orderItemsDTO.get(1).getItemByItemId().getId())).thenReturn(orderItemsDTO.get(1).getItemByItemId());
        doNothing().when(this.itemManager).updateItemStock(any(ItemDTO.class), any(Integer.class));
        doNothing().when(this.orderItemPersistor).save(any(OrderItem.class));

        this.orderManager.createOrder(orderDTO, this.userTestee);

        assertEquals(getOrderTestees().size() + 1, this.orderTestees.size());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void createOrder_WhenValidOrderDTOButNotAuthorizedUser_ThrowException() throws UserNotAuthorisedException {
        final Order order = createOrderTestee(10, 10, 10, 10);
        final List<OrderItemDTO> orderItemsDTO = generateOrderItemDTOs(2);
        final CustomerDTO customerDTO = new CustomerWrapper().dtoFromEntity(order.getCustomerByCustomerId());
        final UserDTO userDTO = new UserWrapper().dtoFromEntity(order.getUserByUserId());
        final OrderStateDTO orderStateDTO = new OrderStateWrapper().dtoFromEntity(order.getOrderStateByOrderState());
        final OrderDTO orderDTO = new OrderDTO(order.getId(), customerDTO, userDTO, orderStateDTO, order.getDateTime(), orderItemsDTO);

        this.orderManager.createOrder(orderDTO, this.unauthorizedUserTestee);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOrder_WhenNullOrderDTO_ThrowException() throws UserNotAuthorisedException {
        this.orderManager.createOrder(null, this.userTestee);
    }

    @Test
    public void cancelOrder_WhenValidOrderDTO_ThenCancelAssociatedOrder() throws UserNotAuthorisedException {
        final OrderDTO orderDTO = new OrderWrapper().dtoFromEntity(this.orderTestees.get(0));
        final OrderState orderState = new OrderState();
        orderState.setId(1);
        orderState.setState(OrderStates.CANCELLED.getState());

        when(this.orderStatePersistor.getByState(OrderStates.CANCELLED.getState())).thenReturn(Optional.of(orderState));

        doAnswer(invocationOnMock -> {
            final Order updateOrder = invocationOnMock.getArgument(0);

            assertEquals(orderDTO.getId(), updateOrder.getId().intValue());
            assertEquals(orderState.getId(), updateOrder.getOrderState());

            return null;
        }).when(this.orderPersistor).update(any(Order.class));

        doNothing().when(this.itemManager).refillItemStock(any(ItemDTO.class), any(Integer.class));

        this.orderManager.cancelOrder(orderDTO, this.userTestee);
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void cancelOrder_WhenValidOrderDTOButNotAuthorizedUser_ThrowException() throws UserNotAuthorisedException {
        final OrderDTO orderDTO = new OrderWrapper().dtoFromEntity(this.orderTestees.get(0));
        this.orderManager.cancelOrder(orderDTO, this.unauthorizedUserTestee);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cancelOrder_WhenNullOrderDTO_ThrowException() throws UserNotAuthorisedException {
        this.orderManager.cancelOrder(null, this.userTestee);
    }

    @Test
    public void updateOrder_WhenValidOrderDTO_ThenUpdateAssociatedOrder() throws UserNotAuthorisedException {
        final Order order = this.orderTestees.get(0);
        final OrderState orderState = new OrderState();
        orderState.setId(1);
        orderState.setState(OrderStates.CANCELLED.getState());
        order.setOrderStateByOrderState(orderState);

        final OrderState newOrderState = new OrderState();
        newOrderState.setId(2);
        newOrderState.setState("randomState");

        final List<OrderItemDTO> orderItemsDTO = generateOrderItemDTOs(2);
        final CustomerDTO customerDTO = new CustomerWrapper().dtoFromEntity(order.getCustomerByCustomerId());
        final UserDTO userDTO = new UserWrapper().dtoFromEntity(order.getUserByUserId());
        final OrderStateDTO orderStateDTO = new OrderStateWrapper().dtoFromEntity(newOrderState);
        final OrderDTO orderDTO = new OrderDTO(order.getId(), customerDTO, userDTO, orderStateDTO, order.getDateTime(), orderItemsDTO);

        when(this.orderStatePersistor.getByState(OrderStates.CANCELLED.getState())).thenReturn(Optional.of(orderState));

        doAnswer(invocationOnMock -> {
            final Order updateOrder = invocationOnMock.getArgument(0);

            assertEquals(orderDTO.getId(), updateOrder.getId().intValue());
            assertEquals(orderDTO.getDateTime(), updateOrder.getDateTime());
            assertEquals(newOrderState.getId(), updateOrder.getOrderState());

            return null;
        }).when(this.orderPersistor).update(any(Order.class));

        doNothing().when(this.itemManager).refillItemStock(any(ItemDTO.class), any(Integer.class));
        doNothing().when(this.itemManager).updateItemStock(any(ItemDTO.class), any(Integer.class));

        this.orderManager.updateOrder(orderDTO, this.userTestee);
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void updateOrder_WhenValidOrderDTOButNotAuthorizedUser_ThrowException() throws UserNotAuthorisedException {
        final OrderDTO orderDTO = new OrderWrapper().dtoFromEntity(this.orderTestees.get(0));
        this.orderManager.updateOrder(orderDTO, this.unauthorizedUserTestee);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateOrder_WhenNullOrderDTO_ThrowException() throws UserNotAuthorisedException {
        this.orderManager.updateOrder(null, this.userTestee);
    }

    private UserDTO getUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, UserRoles.SALESPERSON.getRole());
        return new UserDTO(1, userRoleDTO, "maxmuster");
    }

    private UserDTO getUnauthorizedUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, "unauthorized");
        return new UserDTO(42, userRoleDTO, "unauthorizedUser");
    }

    private List<Order> getOrderTestees() {
        final List<Order> orderTestees = new ArrayList<>();

        final Order orderTestee1 = createOrderTestee(1, 2, 3, 4);
        orderTestees.add(orderTestee1);

        final Order orderTestee2 = createOrderTestee(2, 2, 4, 5);
        orderTestees.add(orderTestee2);

        final Order orderTestee3 = createOrderTestee(3, 3, 2, 1);
        orderTestees.add(orderTestee3);

        final Order orderTestee4 = createOrderTestee(4, 4, 1, 6);
        orderTestees.add(orderTestee4);

        final Order orderTestee5 = createOrderTestee(5, 4, 2, 6);
        orderTestees.add(orderTestee5);

        return orderTestees;
    }

    private Order createOrderTestee(final int orderId, final int customerId, final int userId, final int orderStateId) {
        final Order order = new Order();
        order.setId(orderId);
        order.setCustomerId(customerId);
        order.setUserId(userId);
        order.setDateTime(Timestamp.from(Instant.now()));
        order.setOrderState(orderStateId);
        order.setBillsById(new ArrayList<>());
        order.setOrderItemsById(new ArrayList<>());

        setCustomerForOrder(order);
        setUserForOrder(order);
        setOrderStateForOrder(order);

        return order;
    }

    private void setCustomerForOrder(final Order order) {
        final Customer customer = new Customer();
        customer.setId(order.getCustomerId());
        customer.setPrename("randomCustomerPN");
        customer.setSurname("randomCustomerSN");
        customer.setPlz(42);
        customer.setOrdersById(Collections.singletonList(order));

        order.setCustomerByCustomerId(customer);
    }

    private void setUserForOrder(final Order order) {
        final User user = new User();
        user.setId(order.getUserId());
        user.setUserName("randomUsername");
        user.setPassword("randomPassword");
        user.setDeleted((byte) 0);
        user.setOrdersById(Collections.singletonList(order));

        final UserRole userRole = new UserRole();
        userRole.setId(1);
        userRole.setRoleName("randomUserRole");

        user.setUserRole(userRole.getId());
        user.setUserRoleByUserRole(userRole);

        order.setUserByUserId(user);
    }

    private void setOrderStateForOrder(final Order order) {
        final OrderState orderState = new OrderState();
        orderState.setId(order.getOrderState());
        orderState.setState("randomState");
        orderState.setOrdersById(Collections.singletonList(order));

        order.setOrderStateByOrderState(orderState);
    }

    private List<OrderItemDTO> generateOrderItemDTOs(final int numberOfOrderItems) {
        final List<OrderItemDTO> orderItems = new ArrayList<>();
        for(int i = 0; i < numberOfOrderItems; i++) {
            final ItemDTO itemDTO = new ItemDTO(i, "", 10, "", 10, 5, 5);
            final int quantity = 1;
            final int price = quantity * itemDTO.getPrice();
            final OrderItemDTO orderItemDTO = new OrderItemDTO(price, quantity, itemDTO);
            orderItems.add(orderItemDTO);
        }
        return orderItems;
    }
}
