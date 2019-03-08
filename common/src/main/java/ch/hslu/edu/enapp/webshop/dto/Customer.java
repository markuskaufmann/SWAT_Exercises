package ch.hslu.edu.enapp.webshop.dto;

import java.util.ArrayList;
import java.util.List;

public final class Customer {

    private final String name;
    private final String password;
    private String firstname;
    private String lastname;
    private String address;
    private String email;
    private String dynNAVId;
    private boolean isAdmin;
    private boolean isUser;
    private List<Role> roles;

    public Customer(final String name, final String password, final String firstname, final String lastname, final String address,
                    final String email, final String dynNAVId) {
        this.name = name;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
        this.dynNAVId = dynNAVId;
        this.isAdmin = false;
        this.isUser = false;
        this.roles = new ArrayList<>();
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setDynNAVId(final String dynNAVId) {
        this.dynNAVId = dynNAVId;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
    }

    public void setRoles(final List<Role> roles) {
        this.roles = roles;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getAddress() {
        return this.address;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDynNAVId() {
        return this.dynNAVId;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    public boolean getIsUser() {
        return this.isUser;
    }

    public List<Role> getRoles() {
        return this.roles;
    }
}
