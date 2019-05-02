package ch.hslu.appe.fbs.business.authorisation.model;

import java.util.List;

public final class AuthorisationStorage {

    private List<RolePermission> rolePermissions;

    public AuthorisationStorage() {
        // used by JSON Mapper
    }

    public void setRolePermissions(final List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public List<RolePermission> getRolePermissions() {
        return this.rolePermissions;
    }
}
