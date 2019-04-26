package ch.hslu.appe.fbs.business.order;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationManager;
import ch.hslu.appe.fbs.business.bill.BillManager;
import ch.hslu.appe.fbs.business.item.ItemManager;
import ch.hslu.appe.fbs.business.logger.Logger;
import ch.hslu.appe.fbs.common.dto.*;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.data.order.OrderPersistor;
import ch.hslu.appe.fbs.data.orderitem.OrderItemPersistor;
import ch.hslu.appe.fbs.data.orderstate.OrderStatePersistor;
import ch.hslu.appe.fbs.data.orderstate.OrderStates;
import ch.hslu.appe.fbs.model.db.Bill;
import ch.hslu.appe.fbs.model.db.Order;
import ch.hslu.appe.fbs.model.db.OrderItem;
import ch.hslu.appe.fbs.model.db.OrderState;
import ch.hslu.appe.fbs.wrapper.OrderItemWrapper;
import ch.hslu.appe.fbs.wrapper.OrderWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public final class OrderManagerImpl implements OrderManager {

    private static final Object LOCK = new Object();

    private static final String ERROR_NULL_OBJ_REFERENCE = "object reference can't be null";

    private final OrderPersistor orderPersistor;
    private final OrderItemPersistor orderItemPersistor;
    private final OrderStatePersistor orderStatePersistor;
    private final OrderWrapper orderWrapper;
    private final OrderItemWrapper orderItemWrapper;
    private final ItemManager itemManager;
    private final BillManager billManager;

    public OrderManagerImpl(final OrderPersistor orderPersistor,
                            final OrderItemPersistor orderItemPersistor,
                            final OrderStatePersistor orderStatePersistor,
                            final ItemManager itemManager,
                            final BillManager billManager) {
        this.orderPersistor = orderPersistor;
        this.orderItemPersistor = orderItemPersistor;
        this.orderStatePersistor = orderStatePersistor;
        this.itemManager = itemManager;
        this.billManager = billManager;
        this.orderWrapper = new OrderWrapper();
        this.orderItemWrapper = new OrderItemWrapper();
    }

    @Override
    public List<OrderDTO> getOrders(final int customerId, final UserDTO userDTO) throws UserNotAuthorisedException {
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.GET_ORDER);
        synchronized (LOCK) {
            List<OrderDTO> orders = new ArrayList<>();
            this.orderPersistor.getByCustomer(customerId).forEach(order -> orders.add(orderWrapper.dtoFromEntity(order)));
            return orders;
        }
    }

    @Override
    public List<OrderDTO> getOrders(final CustomerDTO customer, final UserDTO userDTO) throws UserNotAuthorisedException {
        return this.getOrders(customer.getId(), userDTO);
    }

    @Override
    public List<OrderDTO> getOrdersByOrderState(final int customerId, final OrderStateDTO orderState, final UserDTO userDTO) throws UserNotAuthorisedException {
        if (orderState == null) {
            throw new IllegalArgumentException(ERROR_NULL_OBJ_REFERENCE);
        }
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.GET_ORDER);
        synchronized (LOCK) {
            List<OrderDTO> orders = new ArrayList<>();
            this.orderPersistor.getByCustomerAndOrderState(customerId, orderState.getId())
                    .forEach(order -> orders.add(orderWrapper.dtoFromEntity(order)));
            return orders;
        }
    }

    @Override
    public void createOrder(final OrderDTO order, final UserDTO userDTO) throws UserNotAuthorisedException {
        if (order == null) {
            throw new IllegalArgumentException(ERROR_NULL_OBJ_REFERENCE);
        }
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.CREATE_ORDER);
        synchronized (LOCK) {
            final Order persOrder = this.orderWrapper.entityFromDTO(order);
            this.orderPersistor.save(persOrder);
            final Collection<OrderItem> persOrderItems = persOrder.getOrderItemsById();
            final AtomicInteger price = new AtomicInteger();
            order.getOrderItems().forEach(orderItemDTO -> {
                final OrderItem orderItem = this.orderItemWrapper.entityFromDTO(orderItemDTO);
                orderItem.setOrderId(persOrder.getId());
                persOrderItems.add(orderItem);
                final ItemDTO item = this.itemManager.getItem(orderItemDTO.getItemByItemId().getId());
                this.itemManager.updateItemStock(item, orderItemDTO.getQuantity());
                price.addAndGet(orderItemDTO.getPrice());
            });
            persOrderItems.forEach(this.orderItemPersistor::save);
            final Bill bill = this.billManager.generateBill(persOrder.getId(), price.get());
            if(persOrder.getBillsById() == null) {
                persOrder.setBillsById(new ArrayList<>());
            }
            persOrder.getBillsById().add(bill);
            this.orderPersistor.update(persOrder);

            Logger.logInfo(getClass(), userDTO.getUserName(), "Created a new Order with Id: " + persOrder.getId());
        }
    }

    @Override
    public void cancelOrder(final OrderDTO order, final UserDTO userDTO) throws UserNotAuthorisedException {
        if (order == null) {
            throw new IllegalArgumentException(ERROR_NULL_OBJ_REFERENCE);
        }
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.CANCEL_ORDER);
        synchronized (LOCK) {
            final Order persOrder = this.orderWrapper.entityFromDTO(order);
            final Optional<OrderState> optOrderState = this.orderStatePersistor.getByState(OrderStates.CANCELLED.getState());
            if (optOrderState.isPresent()) {
                final OrderState osCancelled = optOrderState.get();
                persOrder.setOrderState(osCancelled.getId());
                this.orderPersistor.update(persOrder);
                order.getOrderItems().forEach(orderItemDTO ->
                        this.itemManager.refillItemStock(orderItemDTO.getItemByItemId(), orderItemDTO.getQuantity()));
            }
            Logger.logInfo(getClass(), userDTO.getUserName(), "Cancelled Order with Id: " + order.getId());
        }
    }

    @Override
    public void updateOrder(final OrderDTO order, final UserDTO userDTO) throws UserNotAuthorisedException {
        if (order == null) {
            throw new IllegalArgumentException(ERROR_NULL_OBJ_REFERENCE);
        }
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.EDIT_ORDER);
        synchronized (LOCK) {
            final Order persOrder = this.orderWrapper.entityFromDTO(order);
            final Optional<Order> oldOrder = this.orderPersistor.getById(persOrder.getId());
            if (oldOrder.isPresent()) {
                final OrderDTO orderDTO = this.orderWrapper.dtoFromEntity(oldOrder.get());
                final Optional<OrderState> optOrderState = this.orderStatePersistor.getByState(OrderStates.CANCELLED.getState());
                optOrderState.ifPresent(orderState -> {
                    if(oldOrder.get().getOrderStateByOrderState().equals(orderState)) {
                        orderDTO.getOrderItems().forEach(orderItemDTO ->
                                this.itemManager.refillItemStock(orderItemDTO.getItemByItemId(), orderItemDTO.getQuantity()));
                    }
                });
            }
            order.getOrderItems().forEach(orderItemDTO ->
                    this.itemManager.updateItemStock(orderItemDTO.getItemByItemId(), orderItemDTO.getQuantity()));
            this.orderPersistor.update(persOrder);
        }
    }

    @Override
    public List<OrderDTO> getAllOrders(final UserDTO userDTO) throws UserNotAuthorisedException {
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.GET_ALL_ORDERS);
        List<OrderDTO> orders = new ArrayList<>();
        synchronized (LOCK) {
            this.orderPersistor.getAll().forEach(order -> orders.add(this.orderWrapper.dtoFromEntity(order)));
        }
        return orders;
    }
}
