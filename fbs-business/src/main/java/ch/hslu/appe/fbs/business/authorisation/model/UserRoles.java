package ch.hslu.appe.fbs.business.authorisation.model;

public enum UserRoles {
    SYSTEM_ADMINISTRATOR("Systemadministrator"),
    DATA_MANAGER("Datentypist"),
    SALESPERSON("Verkaufspersonal"),
    BRANCH_MANAGER("Filialleiter");

    private final String role;

    UserRoles(final String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
