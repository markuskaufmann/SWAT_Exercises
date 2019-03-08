package ch.hslu.edu.enapp.webshop.business;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.dto.Role;
import ch.hslu.edu.enapp.webshop.entity.CustomertoroleEntity;
import ch.hslu.edu.enapp.webshop.exception.NoRoleFoundException;
import ch.hslu.edu.enapp.webshop.service.CustomerServiceLocal;
import ch.hslu.edu.enapp.webshop.service.RoleServiceLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CustomerRoleBean implements ch.hslu.edu.enapp.webshop.service.CustomerRoleServiceLocal {

    private static final Logger LOGGER = LogManager.getLogger(CustomerRoleBean.class);

    @PersistenceContext
    private EntityManager em;

    @Inject
    private CustomerServiceLocal customerService;

    @Inject
    private RoleServiceLocal roleService;

    public CustomerRoleBean() {
    }

    @Override
    public void addRoleToCustomer(final Customer customer, final String roleName) throws NoRoleFoundException {
        if (customer == null) {
            throw new IllegalArgumentException("The provided customer object can't be null");
        }
        final Role role = getRoleByName(roleName);
        final TypedQuery<CustomertoroleEntity> customerRoleQuery = this.em.createNamedQuery("getByCustomerAndRole", CustomertoroleEntity.class);
        customerRoleQuery.setParameter("name", customer.getName());
        customerRoleQuery.setParameter("role", role.getName());
        final List<CustomertoroleEntity> customerRoles = customerRoleQuery.getResultList();
        if (!customerRoles.isEmpty()) {
            return;
        }
        final CustomertoroleEntity customerRole = new CustomertoroleEntity();
        customerRole.setName(customer.getName());
        customerRole.setRole(role.getName());
        this.em.persist(customerRole);
    }

    @Override
    public List<Customer> getCustomersByRole(final String roleName) throws NoRoleFoundException {
        final Role role = getRoleByName(roleName);
        final TypedQuery<String> customerRoleQuery = this.em.createNamedQuery("getCustomersByRole", String.class);
        customerRoleQuery.setParameter("role", role.getName());
        final List<String> customerNames = customerRoleQuery.getResultList();
        if (customerNames.isEmpty()) {
            return new ArrayList<>();
        }
        final List<Customer> customers = new ArrayList<>();
        customerNames.forEach(customerName -> {
            try {
                customers.add(this.customerService.getCustomerByName(customerName));
            } catch (Exception e) {
                LOGGER.error(e);
            }
        });
        return customers;
    }

    private Role getRoleByName(final String roleName) throws NoRoleFoundException {
        if (roleName == null || roleName.trim().length() == 0) {
            throw new IllegalArgumentException("The provided role name can't be null");
        }
        return this.roleService.getRoleByName(roleName);
    }
}
