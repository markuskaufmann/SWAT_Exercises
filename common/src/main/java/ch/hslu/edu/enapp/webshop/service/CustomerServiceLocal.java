package ch.hslu.edu.enapp.webshop.service;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.exception.InvalidRegistrationException;
import ch.hslu.edu.enapp.webshop.exception.NoCustomerFoundException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CustomerServiceLocal {
    boolean doesUsernameExist(String username);

    Customer getCustomerByName(String username) throws NoCustomerFoundException;

    List<Customer> getCustomers();

    void registerCustomer(Customer customer) throws InvalidRegistrationException;

    Customer updateCustomer(Customer customer) throws NoCustomerFoundException;

    Customer updateDynNAVId(String username, String dynNAVId) throws NoCustomerFoundException;
}
