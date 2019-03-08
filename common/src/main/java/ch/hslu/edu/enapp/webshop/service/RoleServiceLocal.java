package ch.hslu.edu.enapp.webshop.service;

import ch.hslu.edu.enapp.webshop.dto.Role;
import ch.hslu.edu.enapp.webshop.exception.NoRoleFoundException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface RoleServiceLocal {
    List<Role> getRoles();

    Role getRoleByName(String name) throws NoRoleFoundException;
}
