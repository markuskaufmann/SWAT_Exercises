package ch.hslu.edu.enapp.webshop.business;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.exception.NoCustomerFoundException;
import ch.hslu.edu.enapp.webshop.service.CustomerServiceLocal;

import javax.ejb.Stateful;
import javax.inject.Inject;

@Stateful
public class CustomerSessionBean implements ch.hslu.edu.enapp.webshop.service.CustomerSessionServiceLocal {

    @Inject
    private CustomerServiceLocal customerService;

    private Customer loggedInCustomer;

    public CustomerSessionBean() {
    }

    @Override
    public Customer setLoggedInCustomer(final String username) throws NoCustomerFoundException {
        this.loggedInCustomer = this.customerService.getCustomerByName(username);
        return getLoggedInCustomer();
    }

    @Override
    public void updateLoggedInCustomer(final Customer customer) throws NoCustomerFoundException {
        if (customer == null) {
            throw new IllegalArgumentException("The provided customer object can't be null");
        }
        this.loggedInCustomer = this.customerService.updateCustomer(customer);
    }

    @Override
    public Customer getLoggedInCustomer() {
        return this.loggedInCustomer;
    }
}
