package ch.hslu.edu.enapp.webshop.mapping;

import ch.hslu.edu.enapp.webshop.dto.Role;
import ch.hslu.edu.enapp.webshop.entity.CustomertoroleEntity;
import ch.hslu.edu.enapp.webshop.entity.RoleEntity;

public final class RoleWrapper {

    public static Role entityToDto(final RoleEntity roleEntity) {
        if (roleEntity == null) {
            throw new IllegalArgumentException("The provided role entity can't be null");
        }
        return new Role(roleEntity.getName());
    }

    public static Role entityToDto(final CustomertoroleEntity customerRoleEntity) {
        if (customerRoleEntity == null) {
            throw new IllegalArgumentException("The provided customer role entity can't be null");
        }
        return entityToDto(customerRoleEntity.getRoleByRole());
    }
}
