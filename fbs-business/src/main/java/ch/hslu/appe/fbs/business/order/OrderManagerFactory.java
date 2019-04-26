package ch.hslu.appe.fbs.business.order;

import ch.hslu.appe.fbs.business.bill.BillManagerFactory;
import ch.hslu.appe.fbs.business.item.ItemManagerFactory;
import ch.hslu.appe.fbs.data.order.OrderPersistorFactory;
import ch.hslu.appe.fbs.data.orderitem.OrderItemPersistorFactory;
import ch.hslu.appe.fbs.data.orderstate.OrderStatePersistorFactory;

public final class OrderManagerFactory {

    private OrderManagerFactory() {
    }

    public static OrderManager createOrderManager() {
        return new OrderManagerImpl(OrderPersistorFactory.createOrderPersistor(),
                                    OrderItemPersistorFactory.createOrderItemPersistor(),
                                    OrderStatePersistorFactory.createOrderStatePersistor(),
                                    ItemManagerFactory.createItemManager(),
                                    BillManagerFactory.createBillManager());
    }
}
