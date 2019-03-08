package ch.hslu.edu.enapp.webshop.service;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.exception.NoRoleFoundException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CustomerRoleServiceLocal {
    void addRoleToCustomer(Customer customer, String roleName) throws NoRoleFoundException;

    List<Customer> getCustomersByRole(String roleName) throws NoRoleFoundException;
}
