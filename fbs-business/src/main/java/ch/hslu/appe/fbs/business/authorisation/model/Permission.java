package ch.hslu.appe.fbs.business.authorisation.model;

public final class Permission {

    private String name;

    public Permission() {
        // used by JSON Mapper
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
