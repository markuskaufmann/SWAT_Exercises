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
import java.util.Optional;

@Stateless
public class CustomerBean implements ch.hslu.edu.enapp.webshop.service.CustomerServiceLocal {

    @PersistenceContext
    private EntityManager em;

    public CustomerBean() {
    }

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
        final Optional<CustomerEntity> customerOptional = getCustomerByUsername(username);
        if (!customerOptional.isPresent()) {
            throw new NoCustomerFoundException("No customer with the provided username '" + username + "' found.");
        }
        return CustomerWrapper.entityToDto(customerOptional.get());
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
        final Optional<CustomerEntity> customerOptional = getCustomerByUsername(username);
        if (!customerOptional.isPresent()) {
            throw new NoCustomerFoundException("No customer with the provided username '" + username + "' found.");
        }
        final CustomerEntity customerEntity = customerOptional.get();
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
        final Optional<CustomerEntity> customerOptional = getCustomerByUsername(username);
        if (!customerOptional.isPresent()) {
            throw new NoCustomerFoundException("No customer with the provided username '" + username + "' found.");
        }
        final CustomerEntity customerEntity = customerOptional.get();
        customerEntity.setDynNAVId(dynNAVId);
        this.em.flush();
        return CustomerWrapper.entityToDto(customerEntity);
    }

    private Optional<CustomerEntity> getCustomerByUsername(final String username) {
        final TypedQuery<CustomerEntity> customerQuery = this.em.createNamedQuery("getCustomerByName", CustomerEntity.class);
        customerQuery.setParameter("name", username);
        final List<CustomerEntity> customers = customerQuery.getResultList();
        if (customers.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(customers.get(0));
    }
}
