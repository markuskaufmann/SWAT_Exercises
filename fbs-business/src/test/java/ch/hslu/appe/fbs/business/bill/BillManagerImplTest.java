package ch.hslu.appe.fbs.business.bill;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifier;
import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifierFactory;
import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.dto.UserRoleDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.data.bill.BillPersistor;
import ch.hslu.appe.fbs.data.reminder.ReminderPersistor;
import ch.hslu.appe.fbs.business.authorisation.model.UserRoles;
import ch.hslu.appe.fbs.model.db.*;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class BillManagerImplTest {

    private static AuthorisationVerifier authorisationVerifier;

    @Mock
    private BillPersistor billPersistor;

    @Mock
    private ReminderPersistor reminderPersistor;

    private BillManager billManager;

    private List<Bill> billTestees;

    private UserDTO userTestee;

    @BeforeClass
    public static void setUpClass() {
        authorisationVerifier = AuthorisationVerifierFactory.createAuthorisationVerifier();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.billManager = new BillManagerImpl(authorisationVerifier, this.billPersistor, this.reminderPersistor);
        this.billTestees = getBillTestees();
        this.userTestee = getUserTestee();
        when(this.billPersistor.getAll()).thenReturn(this.billTestees);
        when(this.billPersistor.getById(this.billTestees.get(0).getId())).thenReturn(Optional.of(this.billTestees.get(0)));
        when(this.billPersistor.getByOrderId(this.billTestees.get(0).getOrderId()))
                .thenReturn(Arrays.asList(this.billTestees.get(0), this.billTestees.get(1)));
        when(this.billPersistor.getByOrderId(this.billTestees.get(2).getOrderId()))
                .thenReturn(Collections.singletonList(this.billTestees.get(2)));
        when(this.reminderPersistor.getByBillId(this.billTestees.get(0).getId())).thenReturn(new ArrayList<>(this.billTestees.get(0).getRemindersById()));
    }

    @Test
    public void getAllBills_WhenBillsExist_ThenReturnThemAll() {
        final List<BillDTO> billsDTO = this.billManager.getAllBills();

        assertEquals(this.billTestees.size(), billsDTO.size());
    }

    @Test
    public void getBillById_WhenBillWithValidId_ThenReturnIt() {
        final Bill billTestee = this.billTestees.get(0);
        final BillDTO billDTO = this.billManager.getBillById(billTestee.getId());

        assertEquals(billTestee.getId(), billDTO.getId());
        assertEquals(billTestee.getOrderId(), billDTO.getOrderId());
        assertEquals(billTestee.getPrice(), billDTO.getPrice());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBillById_WhenBillWithInvalidId_ThrowException() {
        final Bill billTestee = this.billTestees.get(1);
        this.billManager.getBillById(billTestee.getId());
    }

    @Test
    public void getBillsByOrderId_WhenGivenOrderId_ThenReturnAssociatedBills() {
        final int orderId = this.billTestees.get(0).getOrderId();
        final List<BillDTO> billsDTO = this.billManager.getBillsByOrderId(orderId);

        assertEquals(2, billsDTO.size());
    }

    @Test
    public void getBillsByCustomerId_WhenGivenCustomerId_ThenReturnAssociatedBills() {
        final int customerId = this.billTestees.get(0).getOrderByOrderId().getCustomerId();
        final List<BillDTO> billsDTO = this.billManager.getBillsByCustomerId(customerId);

        assertEquals(3, billsDTO.size());
    }

    @Test
    public void getRemindedBillsByCustomerId_WhenRemindersByCustomerIdExist_ReturnAssociatedBills() throws UserNotAuthorisedException {
        final Bill billTestee = this.billTestees.get(0);
        final List<BillDTO> billsDTO = this.billManager.getRemindedBillsByCustomerId(billTestee.getOrderByOrderId().getCustomerId(), this.userTestee);

        assertEquals(1, billsDTO.size());
        assertEquals(billTestee.getId(), billsDTO.get(0).getId());
    }

    @Test
    public void getRemindedBillsByOrderId_WhenRemindersByOrderIdExist_ReturnAssociatedBills() {
        final Bill billTestee = this.billTestees.get(0);
        final List<BillDTO> billsDTO = this.billManager.getRemindedBillsByOrderId(billTestee.getOrderId());

        assertEquals(1, billsDTO.size());
        assertEquals(billTestee.getId(), billsDTO.get(0).getId());
    }

    @Test
    public void generateBill_WhenGivenOrderIdAndPrice_ThenCreateNewBill() {
        final int orderId = 10;
        final int price = 100;

        doAnswer(invocationOnMock -> {
            final Bill bill = invocationOnMock.getArgument(0);
            this.billTestees.add(bill);

            assertEquals(orderId, bill.getOrderId().intValue());
            assertEquals(price, bill.getPrice().intValue());

            return null;
        }).when(this.billPersistor).save(any(Bill.class));

        final int oldSizeBillTestees = this.billTestees.size();
        this.billManager.generateBill(orderId, price);

        assertEquals(oldSizeBillTestees + 1, this.billTestees.size());
    }

    private UserDTO getUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, UserRoles.SALESPERSON.getRole());
        return new UserDTO(1, userRoleDTO, "maxmuster");
    }

    private List<Bill> getBillTestees() {
        final List<Bill> billTestees = new ArrayList<>();

        final Order orderTestee1 = createOrderTestee(1, 2, 3, 4);
        final Order orderTestee2 = createOrderTestee(2, 2, 4, 5);
        final Order orderTestee3 = createOrderTestee(3, 3, 2, 6);

        final Bill testee1 = new Bill();
        testee1.setId(1);
        testee1.setOrderId(orderTestee1.getId());
        testee1.setPrice(10);
        testee1.setOrderByOrderId(orderTestee1);
        testee1.setRemindersById(new ArrayList<>());
        billTestees.add(testee1);

        final Reminder reminderForBillTestee1 = createReminderTestee(testee1);
        testee1.getRemindersById().add(reminderForBillTestee1);

        final Bill testee2 = new Bill();
        testee2.setId(2);
        testee2.setOrderId(orderTestee1.getId());
        testee2.setPrice(20);
        testee2.setOrderByOrderId(orderTestee1);
        testee2.setRemindersById(new ArrayList<>());
        billTestees.add(testee2);

        final Bill testee3 = new Bill();
        testee3.setId(3);
        testee3.setOrderId(orderTestee2.getId());
        testee3.setPrice(30);
        testee3.setOrderByOrderId(orderTestee2);
        testee3.setRemindersById(new ArrayList<>());
        billTestees.add(testee3);

        final Bill testee4 = new Bill();
        testee4.setId(4);
        testee4.setOrderId(orderTestee3.getId());
        testee4.setPrice(50);
        testee4.setOrderByOrderId(orderTestee3);
        testee4.setRemindersById(new ArrayList<>());
        billTestees.add(testee4);

        return billTestees;
    }

    private Reminder createReminderTestee(final Bill billTestee) {
        final Reminder reminder = new Reminder();
        reminder.setId(1);
        reminder.setBillId(billTestee.getId());
        reminder.setBillByBillId(billTestee);
        return reminder;
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
}
