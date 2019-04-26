package ch.hslu.appe.fbs.remote.service.customer;

import ch.hslu.appe.fbs.business.bill.BillManager;
import ch.hslu.appe.fbs.business.customer.CustomerManager;
import ch.hslu.appe.fbs.business.order.OrderManager;
import ch.hslu.appe.fbs.common.rmi.CustomerService;
import ch.hslu.appe.fbs.remote.rmi.RmiClient;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;

public final class CustomerServiceFactory {

    private CustomerServiceFactory() {
    }

    public static CustomerService createCustomerService(final UserSessionMap userSessionMap,
                                                        final CustomerManager customerManager,
                                                        final OrderManager orderManager,
                                                        final BillManager billManager) {
        return new CustomerServiceImpl(new RmiClient(), userSessionMap, customerManager, orderManager, billManager);
    }
}
