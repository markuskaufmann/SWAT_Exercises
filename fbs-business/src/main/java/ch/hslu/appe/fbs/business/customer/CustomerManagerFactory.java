package ch.hslu.appe.fbs.business.customer;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifier;
import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifierFactory;
import ch.hslu.appe.fbs.data.customer.CustomerPersistorFactory;

public final class CustomerManagerFactory {

    private CustomerManagerFactory() {
    }

    public static CustomerManager createCustomerManager() {
        return createCustomerManager(AuthorisationVerifierFactory.createAuthorisationVerifier());
    }

    public static CustomerManager createCustomerManager(final AuthorisationVerifier authorisationVerifier) {
        return new CustomerManagerImpl(authorisationVerifier,
                CustomerPersistorFactory.createCustomerPersistor());
    }
}
