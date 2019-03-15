package ch.hslu.edu.enapp.webshop.business;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.entity.CustomerEntity;
import ch.hslu.edu.enapp.webshop.entity.CustomertoroleEntity;
import ch.hslu.edu.enapp.webshop.exception.InvalidRegistrationException;
import ch.hslu.edu.enapp.webshop.exception.NoCustomerFoundException;
import ch.hslu.edu.enapp.webshop.mapping.CustomerWrapper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CustomerBean implements ch.hslu.edu.enapp.webshop.service.CustomerServiceLocal {

    private static final String ERR_NO_CUSTOMER_KEY = "{customer}";
    private static final String ERR_NO_CUSTOMER_FOUND = "No customer with the provided username '" + ERR_NO_CUSTOMER_KEY + "' found.";

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean doesUsernameExist(final String username) {
        try {
            getCustomerByName(username);
            return true;
        } catch (NoCustomerFoundException e) {
            return false;
        }
    }

    @Override
    public Customer getCustomerByName(final String username) throws NoCustomerFoundException {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("The provided username can't be null or empty");
        }
        final CustomerEntity customerEntity = getCustomerByUsername(username);
        return CustomerWrapper.entityToDto(customerEntity);
    }

    @Override
    public List<Customer> getCustomers() {
        final TypedQuery<CustomerEntity> customerQuery = this.em.createNamedQuery("getCustomers", CustomerEntity.class);
        final List<CustomerEntity> customers = customerQuery.getResultList();
        final List<Customer> customersDTO = new ArrayList<>();
        customers.forEach(customerEntity -> {
            final Customer customer = CustomerWrapper.entityToDto(customerEntity);
            customersDTO.add(customer);
        });
        return customersDTO;
    }

    @Override
    public void registerCustomer(final Customer customer) throws InvalidRegistrationException {
        if (customer == null) {
            throw new IllegalArgumentException("The provided customer object can't be null");
        }
        if (doesUsernameExist(customer.getName())) {
            throw new InvalidRegistrationException("The specified username is already taken.");
        }
        final CustomerEntity customerEntity = CustomerWrapper.dtoToEntity(customer);
        this.em.persist(customerEntity);
        final CustomertoroleEntity customerRoleEntity = new CustomertoroleEntity();
        customerRoleEntity.setName(customer.getName());
        customerRoleEntity.setRole("webshop-user");
        this.em.persist(customerRoleEntity);
        this.em.flush();
    }

    @Override
    public Customer updateCustomer(final Customer customer) throws NoCustomerFoundException {
        if (customer == null) {
            throw new IllegalArgumentException("The provided customer object can't be null");
        }
        final String username = customer.getName();
        final CustomerEntity customerEntity = getCustomerByUsername(username);
        customerEntity.setFirstname(customer.getFirstname());
        customerEntity.setLastname(customer.getLastname());
        customerEntity.setAddress(customer.getAddress());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setDynNAVId(customer.getDynNAVId());
        this.em.flush();
        return CustomerWrapper.entityToDto(customerEntity);
    }

    @Override
    public Customer updateDynNAVId(final String username, final String dynNAVId) throws NoCustomerFoundException {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("The provided username can't be null or empty");
        }
        if (dynNAVId == null || dynNAVId.trim().length() == 0) {
            throw new IllegalArgumentException("The provided DynNAVId can't be null or empty");
        }
        final CustomerEntity customerEntity = getCustomerByUsername(username);
        customerEntity.setDynNAVId(dynNAVId);
        this.em.flush();
        return CustomerWrapper.entityToDto(customerEntity);
    }

    private CustomerEntity getCustomerByUsername(final String username) throws NoCustomerFoundException {
        final TypedQuery<CustomerEntity> customerQuery = this.em.createNamedQuery("getCustomerByName", CustomerEntity.class);
        customerQuery.setParameter("name", username);
        final List<CustomerEntity> customers = customerQuery.getResultList();
        if (customers.isEmpty()) {
            throw new NoCustomerFoundException(ERR_NO_CUSTOMER_FOUND.replace(ERR_NO_CUSTOMER_KEY, username));
        }
        return customers.get(0);
    }
}
