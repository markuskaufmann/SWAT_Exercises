package ch.hslu.edu.enapp.webshop.service;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.exception.NoCustomerFoundException;

import javax.ejb.Local;

@Local
public interface CustomerSessionServiceLocal {
    Customer setLoggedInCustomer(String username) throws NoCustomerFoundException;

    void updateLoggedInCustomer(Customer customer) throws NoCustomerFoundException;

    Customer getLoggedInCustomer();
}
