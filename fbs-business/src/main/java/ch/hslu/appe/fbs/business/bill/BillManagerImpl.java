package ch.hslu.appe.fbs.business.bill;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifier;
import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.common.dto.OrderDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.data.bill.BillPersistor;
import ch.hslu.appe.fbs.data.reminder.ReminderPersistor;
import ch.hslu.appe.fbs.model.db.Bill;
import ch.hslu.appe.fbs.model.db.Order;
import ch.hslu.appe.fbs.model.db.Reminder;
import ch.hslu.appe.fbs.wrapper.BillWrapper;
import ch.hslu.appe.fbs.wrapper.OrderWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class BillManagerImpl implements BillManager {

    private static final Object LOCK = new Object();

    private final AuthorisationVerifier authorisationVerifier;
    private final BillPersistor billPersistor;
    private final ReminderPersistor reminderPersistor;
    private final BillWrapper billWrapper;
    private final OrderWrapper orderWrapper;

    public BillManagerImpl(final AuthorisationVerifier authorisationVerifier, final BillPersistor billPersistor,
                           final ReminderPersistor reminderPersistor) {
        this.authorisationVerifier = authorisationVerifier;
        this.billPersistor = billPersistor;
        this.reminderPersistor = reminderPersistor;
        this.billWrapper = new BillWrapper();
        this.orderWrapper = new OrderWrapper();
    }

    @Override
    public List<BillDTO> getAllBills() {
        synchronized (LOCK) {
            final List<BillDTO> bills = new ArrayList<>();
            this.billPersistor.getAll().forEach(bill -> bills.add(this.billWrapper.dtoFromEntity(bill)));
            return bills;
        }
    }

    @Override
    public BillDTO getBillById(final int billId) {
        synchronized (LOCK) {
            final Optional<Bill> bill = this.billPersistor.getById(billId);
            if (bill.isPresent()) {
                return this.billWrapper.dtoFromEntity(bill.get());
            }
        }
        throw new IllegalArgumentException("Bill with id " + billId + " not found!");
    }

    @Override
    public List<BillDTO> getBillsByOrderId(final int orderId) {
        synchronized (LOCK) {
            final List<BillDTO> bills = new ArrayList<>();
            this.billPersistor.getByOrderId(orderId).forEach(bill -> bills.add(this.billWrapper.dtoFromEntity(bill)));
            return bills;
        }
    }

    @Override
    public List<BillDTO> getBillsByCustomerId(final int customerId) {
        synchronized (LOCK) {
            final List<BillDTO> bills = new ArrayList<>();
            final List<OrderDTO> orders = this.getOrdersByCustomerId(customerId);
            orders.forEach(orderDTO -> bills.addAll(this.getBillsByOrderId(orderDTO.getId())));
            return bills;
        }
    }

    @Override
    public List<BillDTO> getRemindedBillsByCustomerId(final int customerId, final UserDTO userDTO) throws UserNotAuthorisedException {
        this.authorisationVerifier.checkUserAuthorisation(userDTO, UserPermissions.GET_REMINDER);
        synchronized (LOCK) {
            final List<BillDTO> bills = new ArrayList<>();
            final List<OrderDTO> orders = this.getOrdersByCustomerId(customerId);
            orders.forEach(orderDTO -> bills.addAll(this.getRemindedBillsByOrderId(orderDTO.getId())));
            return bills;
        }
    }

    @Override
    public List<BillDTO> getRemindedBillsByOrderId(final int orderId) {
        synchronized (LOCK) {
            final List<BillDTO> billsWithReminders = new ArrayList<>();
            this.billPersistor.getByOrderId(orderId).forEach(bill -> {
                final List<Reminder> reminders = this.reminderPersistor.getByBillId(bill.getId());
                if (!reminders.isEmpty()) {
                    billsWithReminders.add(this.billWrapper.dtoFromEntity(bill));
                }
            });
            return billsWithReminders;
        }
    }

    @Override
    public Bill generateBill(final int orderId, final int price) {
        synchronized (LOCK) {
            final BillDTO billDTO = new BillDTO(-1, orderId, price);
            final Bill bill = this.billWrapper.entityFromDTO(billDTO);
            this.billPersistor.save(bill);
            return bill;
        }
    }

    private List<OrderDTO> getOrdersByCustomerId(final int customerId) {
        final List<OrderDTO> orders = new ArrayList<>();
        for (Bill bill : this.billPersistor.getAll()) {
            Order orderByOrderId = bill.getOrderByOrderId();
            if (orderByOrderId.getCustomerId() == customerId) {
                boolean alreadyInList = false;
                for(OrderDTO orderDTO : orders) {
                    if(orderDTO.getId() == orderByOrderId.getId()) {
                        alreadyInList = true;
                        break;
                    }
                }
                if(!alreadyInList) {
                    orders.add(this.orderWrapper.dtoFromEntity(orderByOrderId));
                }
            }
        }
        return orders;
    }
}
