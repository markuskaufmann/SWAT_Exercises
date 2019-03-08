package ch.hslu.edu.enapp.webshop.business;

import ch.hslu.edu.enapp.webshop.dto.Role;
import ch.hslu.edu.enapp.webshop.entity.RoleEntity;
import ch.hslu.edu.enapp.webshop.exception.NoRoleFoundException;
import ch.hslu.edu.enapp.webshop.mapping.RoleWrapper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class RoleBean implements ch.hslu.edu.enapp.webshop.service.RoleServiceLocal {

    @PersistenceContext
    private EntityManager em;

    public RoleBean() {
    }

    @Override
    public List<Role> getRoles() {
        final TypedQuery<RoleEntity> roleQuery = this.em.createNamedQuery("getRoles", RoleEntity.class);
        final List<RoleEntity> roles = roleQuery.getResultList();
        final List<Role> rolesDTO = new ArrayList<>();
        roles.forEach(roleEntity -> {
            final Role role = RoleWrapper.entityToDto(roleEntity);
            rolesDTO.add(role);
        });
        return rolesDTO;
    }

    @Override
    public Role getRoleByName(final String name) throws NoRoleFoundException {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("The provided role name can't be null or empty");
        }
        final TypedQuery<RoleEntity> roleQuery = this.em.createNamedQuery("getRoleByName", RoleEntity.class);
        roleQuery.setParameter("name", name);
        final List<RoleEntity> roles = roleQuery.getResultList();
        if (roles.isEmpty()) {
            throw new NoRoleFoundException("No role with the provided name '" + name + "' found.");
        }
        return RoleWrapper.entityToDto(roles.get(0));
    }
}
