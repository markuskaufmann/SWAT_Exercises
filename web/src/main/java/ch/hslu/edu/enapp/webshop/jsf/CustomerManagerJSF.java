package ch.hslu.edu.enapp.webshop.jsf;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.exception.NoRoleFoundException;
import ch.hslu.edu.enapp.webshop.service.CustomerRoleServiceLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class CustomerManagerJSF {

    @Inject
    private CustomerRoleServiceLocal customerRoleService;

    public List<Customer> getCustomers(final Customer loggedInCustomer) {
        if (loggedInCustomer == null) {
            throw new IllegalArgumentException("The provided logged in customer can't be a null reference");
        }
        final List<Customer> customers = new ArrayList<>();
        try {
            final List<Customer> customersDB = this.customerRoleService.getCustomersByRole("webshop-user");
            customersDB.forEach(customer -> {
                if (!customer.getName().equals(loggedInCustomer.getName())) {
                    customers.add(customer);
                }
            });
        } catch (NoRoleFoundException e) {
            e.printStackTrace();
        }
        return customers;
    }
}
