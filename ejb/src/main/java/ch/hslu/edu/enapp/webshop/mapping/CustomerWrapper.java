package ch.hslu.edu.enapp.webshop.mapping;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.dto.Role;
import ch.hslu.edu.enapp.webshop.entity.CustomerEntity;
import ch.hslu.edu.enapp.webshop.entity.CustomertoroleEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CustomerWrapper {

    private CustomerWrapper() {
    }

    public static Customer entityToDto(final CustomerEntity customerEntity) {
        if (customerEntity == null) {
            throw new IllegalArgumentException("The provided customer entity can't be null");
        }
        final Customer customer = new Customer(customerEntity.getName(), customerEntity.getPassword(), customerEntity.getFirstname(), customerEntity.getLastname(),
                customerEntity.getAddress(), customerEntity.getEmail(), customerEntity.getDynNAVId());
        final Collection<CustomertoroleEntity> customerToRole = customerEntity.getCustomertorolesByName();
        final List<CustomertoroleEntity> customerRoles = customerToRole != null
                ? new ArrayList<>(customerEntity.getCustomertorolesByName())
                : new ArrayList<>();
        final List<Role> roles = new ArrayList<>();
        customerRoles.forEach(customertoroleEntity -> {
            final Role role = RoleWrapper.entityToDto(customertoroleEntity);
            final String roleName = role.getName();
            if (roleName.equals("webshop-admin")) {
                customer.setIsAdmin(true);
            } else if (roleName.equals("webshop-user")) {
                customer.setIsUser(true);
            }
            roles.add(role);
        });
        customer.setRoles(roles);
        return customer;
    }

    public static CustomerEntity dtoToEntity(final Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("The provided customer can't be null");
        }
        final CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customer.getName());
        customerEntity.setPassword(customer.getPassword());
        customerEntity.setFirstname(customer.getFirstname());
        customerEntity.setLastname(customer.getLastname());
        customerEntity.setAddress(customer.getAddress());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setDynNAVId(customer.getDynNAVId());
        return customerEntity;
    }
}
