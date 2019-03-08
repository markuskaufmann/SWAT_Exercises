package ch.hslu.edu.enapp.webshop.jsf;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.exception.NoCustomerFoundException;
import ch.hslu.edu.enapp.webshop.service.CustomerServiceLocal;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Named
@SessionScoped
public class EditCustomerJSF implements Serializable {

    @Inject
    private CustomerServiceLocal customerService;

    private Customer customer;

    @Size(min = 4, max = 20, message = "Length must be between 4 and 20")
    private String firstName;

    @Size(min = 4, max = 20, message = "Length must be between 4 and 20")
    private String lastName;

    @Size(min = 4, max = 20, message = "Length must be between 4 and 20")
    private String address;

    @Pattern(regexp = ".+@.+", message = "E-Mail is invalid - form has to be: [address]@[host]")
    private String email;

    private String dynNAVId;

    private String infoMessage;
    private String errorMessage;

    public String init(final Customer customer) {
        try {
            this.customer = this.customerService.getCustomerByName(customer.getName());
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
        this.infoMessage = null;
        this.errorMessage = null;
        return "/secure/edit_account?faces-redirect=true";
    }

    public void submit(final ActionEvent actionEvent) {
        if (!isFormDirty()) {
            setInfoMessage("The account information hasn't changed.");
            return;
        }
        this.customer.setFirstname(this.firstName);
        this.customer.setLastname(this.lastName);
        this.customer.setAddress(this.address);
        this.customer.setEmail(this.email);
        this.customer.setDynNAVId((this.dynNAVId == null || this.dynNAVId.trim().length() == 0) ? null : this.dynNAVId);
        try {
            this.customerService.updateCustomer(this.customer);
            setInfoMessage("The account has been updated.");
        } catch (NoCustomerFoundException e) {
            setErrorMessage(e.getMessage());
        }
    }

    public String getInfoMessage() {
        return this.infoMessage;
    }

    private void setInfoMessage(final String message) {
        this.infoMessage = message;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    private void setErrorMessage(final String message) {
        this.errorMessage = message;
    }

    public String getEmail() {
        return this.customer.getEmail();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDynNAVId() {
        return this.customer.getDynNAVId();
    }

    public void setDynNAVId(String dynNAVId) {
        this.dynNAVId = dynNAVId;
    }

    public String getUsername() {
        return this.customer.getName();
    }

    public String getFirstName() {
        return this.customer.getFirstname();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.customer.getLastname();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.customer.getAddress();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private boolean isFormDirty() {
        return doValuesDiffer(this.customer.getFirstname(), this.firstName)
                || doValuesDiffer(this.customer.getLastname(), this.lastName)
                || doValuesDiffer(this.customer.getAddress(), this.address)
                || doValuesDiffer(this.customer.getEmail(), this.email)
                || doValuesDiffer(this.customer.getDynNAVId(), this.dynNAVId);
    }

    private boolean doValuesDiffer(final String first, final String second) {
        if (first == null && second == null) {
            return false;
        }
        if ((first == null && second.trim().length() == 0) || (second == null && first.trim().length() == 0)) {
            return false;
        }
        final String val1 = first == null ? "" : first.trim();
        final String val2 = second == null ? "" : second.trim();
        return val1.compareTo(val2) != 0;
    }
}
