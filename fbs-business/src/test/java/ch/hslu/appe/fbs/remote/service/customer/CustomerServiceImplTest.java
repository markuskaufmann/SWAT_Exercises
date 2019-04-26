package ch.hslu.appe.fbs.remote.service.customer;

import ch.hslu.appe.fbs.business.bill.BillManager;
import ch.hslu.appe.fbs.business.customer.CustomerManager;
import ch.hslu.appe.fbs.business.order.OrderManager;
import ch.hslu.appe.fbs.common.dto.*;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.CustomerService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.data.orderstate.OrderStates;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import ch.hslu.appe.fbs.model.db.*;
import ch.hslu.appe.fbs.remote.rmi.ClientHost;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;
import ch.hslu.appe.fbs.wrapper.BillWrapper;
import ch.hslu.appe.fbs.wrapper.CustomerWrapper;
import ch.hslu.appe.fbs.wrapper.OrderWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public final class CustomerServiceImplTest {

    private static final String LOCALHOST = "localhost";

    @Mock
    private CustomerManager customerManager;

    @Mock
    private OrderManager orderManager;

    @Mock
    private BillManager billManager;

    @Mock
    private ClientHost clientHost;

    @Mock
    private UserSessionMap userSessionMap;

    private CustomerService customerService;
    private List<CustomerDTO> customerTestees;
    private List<OrderDTO> orderTestees;
    private List<BillDTO> billTestees;

    @Before
    public void setUp() throws UserNotAuthorisedException {
        MockitoAnnotations.initMocks(this);
        this.customerService = new CustomerServiceImpl(this.clientHost, this.userSessionMap, this.customerManager,
                this.orderManager, this.billManager);
        final UserDTO userTestee = getUserTestee();
        this.customerTestees = getCustomerTestees();
        this.orderTestees = getOrderTestees();
        this.billTestees = getBillTestees();
        when(this.clientHost.getHostAddress()).thenReturn(LOCALHOST);
        when(this.userSessionMap.getUserSession(LOCALHOST)).thenReturn(Optional.of(userTestee));
        when(this.customerManager.getAllCustomers(userTestee)).thenReturn(this.customerTestees);
        when(this.customerManager.getCustomer(this.customerTestees.get(0).getId(), userTestee)).thenReturn(this.customerTestees.get(0));
        when(this.customerManager.getCustomer(this.customerTestees.get(1).getId(), userTestee)).thenReturn(this.customerTestees.get(1));
        when(this.customerManager.getCustomer(this.customerTestees.get(2).getId(), userTestee)).thenReturn(this.customerTestees.get(2));
        when(this.customerManager.getCustomer(-1, userTestee)).thenThrow(new IllegalArgumentException());
        when(this.orderManager.getAllOrders(userTestee)).thenReturn(this.orderTestees);
        when(this.orderManager.getOrders(this.orderTestees.get(0).getCustomer().getId(), userTestee))
                .thenReturn(Arrays.asList(this.orderTestees.get(0), this.orderTestees.get(1)));
        when(this.billManager.getRemindedBillsByCustomerId(2, userTestee))
                .thenReturn(Collections.singletonList(this.billTestees.get(0)));
    }

    @Test
    public void getCustomer_WhenValidCustomerId_ThenReturnAssociatedCustomer() throws RemoteException, UserNotAuthorisedException {
        final CustomerDTO validCustomer = this.customerTestees.get(0);
        final CustomerDTO customerDTO = this.customerService.getCustomer(validCustomer.getId());

        assertEquals(validCustomer.getId(), customerDTO.getId());
        assertEquals(validCustomer.getPrename(), customerDTO.getPrename());
        assertEquals(validCustomer.getSurname(), customerDTO.getSurname());
        assertEquals(validCustomer.getPlz(), customerDTO.getPlz());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCustomer_WhenInvalidCustomerId_ThrowException() throws RemoteException, UserNotAuthorisedException {
        final int invalidCustomerId = -1;
        this.customerService.getCustomer(invalidCustomerId);
    }

    @Test
    public void getAllCustomers_WhenCustomersExist_ThenReturnThemAll() throws RemoteException, UserNotAuthorisedException {
        final List<CustomerDTO> customers = this.customerService.getAllCustomers();
        assertEquals(this.customerTestees.size(), customers.size());
    }

    @Test
    public void getOrders_WhenValidCustomerId_ThenReturnAssociatedOrders() throws RemoteException, UserNotAuthorisedException {
        final CustomerDTO customer = this.orderTestees.get(0).getCustomer();
        final int expectedOrdersFound = 2;
        final List<OrderDTO> orders = this.customerService.getOrders(customer.getId());

        assertEquals(expectedOrdersFound, orders.size());
    }

    @Test
    public void getRemindedBills_WhenValidCustomerId_ThenReturnAssociatedRemindedBills()
            throws RemoteException, UserNotAuthorisedException {
        final int customerId = 2;
        final int expectedBillSize = 1;
        final BillDTO expectedBill = this.billTestees.get(0);

        final List<BillDTO> bills = this.customerService.getRemindedBills(customerId);
        final BillDTO billFound = bills.get(0);

        assertEquals(expectedBillSize, bills.size());

        assertEquals(expectedBill.getId(), billFound.getId());
        assertEquals(expectedBill.getOrderId(), billFound.getOrderId());
        assertEquals(expectedBill.getPrice(), billFound.getPrice());
    }

    @Test
    public void createOrder_WhenValidOrderDTO_ThenCreateNewOrder() throws RemoteException, UserNotAuthorisedException {
        final Order orderTestee = createOrderTestee(5, 5, 5, 5);
        final OrderDTO orderDTOTestee = new OrderWrapper().dtoFromEntity(orderTestee);
        final int expectedOrdersSize = this.orderTestees.size() + 1;

        doAnswer(invocationOnMock -> {
            final OrderDTO orderToCreate = invocationOnMock.getArgument(0);

            assertEquals(orderDTOTestee.getId(), orderToCreate.getId());
            assertEquals(orderDTOTestee.getDateTime(), orderToCreate.getDateTime());
            assertEquals(orderDTOTestee.getCustomer(), orderToCreate.getCustomer());
            assertEquals(orderDTOTestee.getOrderState(), orderToCreate.getOrderState());
            assertEquals(orderDTOTestee.getUser(), orderToCreate.getUser());
            assertEquals(orderDTOTestee.getOrderItems().size(), orderToCreate.getOrderItems().size());

            this.orderTestees.add(orderToCreate);

            return null;
        }).when(this.orderManager).createOrder(any(OrderDTO.class), any(UserDTO.class));

        this.customerService.createOrder(orderDTOTestee);

        assertEquals(expectedOrdersSize, this.orderTestees.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOrder_WhenInvalidOrderDTO_ThrowException() throws RemoteException, UserNotAuthorisedException {
        this.customerService.createOrder(null);
    }

    @Test
    public void createCustomer_WhenValidCustomerDTO_ThenCreateNewCustomer() throws RemoteException, UserNotAuthorisedException {
        final int customerId = 3;
        final String prename = "Herbert";
        final String surname = "Mueller";
        final int plz = 6210;
        final CustomerDTO newCustomer = new CustomerDTO(customerId, prename, surname, plz, null, null);
        final int expectedCustomerSize = this.customerTestees.size() + 1;

        doAnswer(invocationOnMock -> {
            final CustomerDTO customerToCreate = invocationOnMock.getArgument(0);

            assertEquals(customerId, customerToCreate.getId());
            assertEquals(prename, customerToCreate.getPrename());
            assertEquals(surname, customerToCreate.getSurname());
            assertEquals(plz, customerToCreate.getPlz());

            this.customerTestees.add(customerToCreate);

            return null;
        }).when(this.customerManager).createCustomer(any(CustomerDTO.class), any(UserDTO.class));

        this.customerService.createCustomer(newCustomer);

        assertEquals(expectedCustomerSize, this.customerTestees.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCustomer_WhenInvalidCustomerDTO_ThrowException() throws RemoteException, UserNotAuthorisedException {
        customerService.createCustomer(null);
    }

    @Test
    public void cancelOrder_WhenValidOrderDTO_ThenCancelAssociatedOrder() throws RemoteException, UserNotAuthorisedException {
        final String expectedOrderState = OrderStates.CANCELLED.getState();

        doAnswer(invocationOnMock -> {
            final OrderDTO orderToCancel = invocationOnMock.getArgument(0);
            final OrderStateDTO stateCancelled = new OrderStateDTO(1, OrderStates.CANCELLED.getState());
            final OrderDTO orderUpdate = new OrderDTO(orderToCancel.getId(), orderToCancel.getCustomer(), orderToCancel.getUser(),
                    stateCancelled, orderToCancel.getDateTime(), orderToCancel.getOrderItems());

            this.orderTestees.set(0, orderUpdate);

            return null;
        }).when(this.orderManager).cancelOrder(any(OrderDTO.class), any(UserDTO.class));

        this.customerService.cancelOrder(this.orderTestees.get(0));

        assertEquals(expectedOrderState, this.orderTestees.get(0).getOrderState().getOrderState());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cancelOrder_WhenInvalidOrderDTO_ThrowException() throws RemoteException, UserNotAuthorisedException {
        this.customerService.cancelOrder(null);
    }

    @Test
    public void getAllOrders_WhenOrdersExist_ThenReturnThem() throws RemoteException, UserNotAuthorisedException {
        final List<OrderDTO> orders = this.customerService.getAllOrders();
        assertEquals(this.orderTestees.size(), orders.size());
    }

    @Test
    public void getServiceName_WhenServiceNameExists_ThenReturnIt() throws RemoteException {
        final String serviceName = this.customerService.getServiceName();
        assertEquals(RmiLookupTable.getCustomerServiceName(), serviceName);
    }

    private UserDTO getUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, UserRoles.SYSADMIN.getRole());
        return new UserDTO(1, userRoleDTO, "maxmuster");
    }

    private List<CustomerDTO> getCustomerTestees() {
        final List<CustomerDTO> customerTestees = new ArrayList<>();
        final CustomerWrapper customerWrapper = new CustomerWrapper();

        final Customer testee1 = new Customer();
        testee1.setId(1);
        testee1.setPrename("Max");
        testee1.setSurname("Muster");
        testee1.setPlz(6210);
        customerTestees.add(customerWrapper.dtoFromEntity(testee1));

        final Customer testee2 = new Customer();
        testee2.setId(2);
        testee2.setPrename("Fritz");
        testee2.setSurname("Meier");
        testee2.setPlz(6210);
        customerTestees.add(customerWrapper.dtoFromEntity(testee2));

        final Customer testee3 = new Customer();
        testee3.setId(3);
        testee3.setPrename("Herbert");
        testee3.setSurname("Mueller");
        testee3.setPlz(6210);
        customerTestees.add(customerWrapper.dtoFromEntity(testee3));

        return customerTestees;
    }

    private List<OrderDTO> getOrderTestees() {
        final List<OrderDTO> orderTestees = new ArrayList<>();
        final OrderWrapper orderWrapper = new OrderWrapper();

        final Order orderTestee1 = createOrderTestee(1, 2, 3, 4);
        orderTestees.add(orderWrapper.dtoFromEntity(orderTestee1));

        final Order orderTestee2 = createOrderTestee(2, 2, 4, 5);
        orderTestees.add(orderWrapper.dtoFromEntity(orderTestee2));

        final Order orderTestee3 = createOrderTestee(3, 3, 2, 1);
        orderTestees.add(orderWrapper.dtoFromEntity(orderTestee3));

        final Order orderTestee4 = createOrderTestee(4, 4, 1, 6);
        orderTestees.add(orderWrapper.dtoFromEntity(orderTestee4));

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

    private List<BillDTO> getBillTestees() {
        final List<BillDTO> billTestees = new ArrayList<>();
        final BillWrapper billWrapper = new BillWrapper();

        final Order orderTestee1 = createOrderTestee(1, 2, 3, 4);

        final Bill testee1 = new Bill();
        testee1.setId(1);
        testee1.setOrderId(orderTestee1.getId());
        testee1.setPrice(10);
        testee1.setOrderByOrderId(orderTestee1);
        testee1.setRemindersById(new ArrayList<>());
        billTestees.add(billWrapper.dtoFromEntity(testee1));

        final Reminder reminderForBillTestee1 = createReminderTestee(testee1);
        testee1.getRemindersById().add(reminderForBillTestee1);

        final Bill testee2 = new Bill();
        testee2.setId(2);
        testee2.setOrderId(orderTestee1.getId());
        testee2.setPrice(20);
        testee2.setOrderByOrderId(orderTestee1);
        testee2.setRemindersById(new ArrayList<>());
        billTestees.add(billWrapper.dtoFromEntity(testee2));

        return billTestees;
    }

    private Reminder createReminderTestee(final Bill billTestee) {
        final Reminder reminder = new Reminder();
        reminder.setId(1);
        reminder.setBillId(billTestee.getId());
        reminder.setBillByBillId(billTestee);
        return reminder;
    }
}
