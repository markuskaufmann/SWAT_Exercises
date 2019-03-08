package ch.hslu.edu.enapp.webshop.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CustomertoroleEntityPK implements Serializable {
    private String name;
    private String role;

    @Column(name = "name", nullable = false, length = 255)
    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "role", nullable = false, length = 255)
    @Id
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomertoroleEntityPK that = (CustomertoroleEntityPK) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, role);
    }
}
