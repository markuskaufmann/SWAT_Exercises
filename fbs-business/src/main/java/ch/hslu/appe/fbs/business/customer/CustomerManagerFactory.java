package ch.hslu.appe.fbs.business.customer;

import ch.hslu.appe.fbs.data.customer.CustomerPersistorFactory;

public final class CustomerManagerFactory {

    private CustomerManagerFactory() {
    }

    public static CustomerManager createCustomerManager() {
        return new CustomerManagerImpl(CustomerPersistorFactory.createCustomerPersistor());
    }
}
