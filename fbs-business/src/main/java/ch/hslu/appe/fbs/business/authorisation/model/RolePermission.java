package ch.hslu.appe.fbs.business.authorisation.model;

import java.util.List;

public final class RolePermission {

    private Role role;
    private List<Permission> permissions;

    public RolePermission() {
        // used by JSON Mapper
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public void setPermissions(final List<Permission> permissions) {
        this.permissions = permissions;
    }

    public Role getRole() {
        return this.role;
    }

    public List<Permission> getPermissions() {
        return this.permissions;
    }
}
