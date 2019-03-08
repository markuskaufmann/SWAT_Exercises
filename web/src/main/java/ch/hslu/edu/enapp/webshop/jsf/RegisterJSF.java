package ch.hslu.edu.enapp.webshop.jsf;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.service.CustomerServiceLocal;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.IOException;

@Named
@RequestScoped
public class RegisterJSF {

    @Inject
    private CustomerServiceLocal customerService;

    @Size(min = 4, max = 20, message = "Length must be between 4 and 20")
    private String username;

    @Pattern(regexp = ".+@.+", message = "E-Mail is invalid - form has to be: [address]@[host]")
    private String email;

    @Size(min = 4, max = 20, message = "Length must be between 4 and 20")
    private String password;

    @Size(min = 4, max = 20, message = "Length must be between 4 and 20")
    private String firstName;

    @Size(min = 4, max = 20, message = "Length must be between 4 and 20")
    private String lastName;

    @Size(min = 4, max = 20, message = "Length must be between 4 and 20")
    private String address;

    private String infoMessage;
    private String errorMessage;

    public void submit(final ActionEvent actionEvent) {
        final Customer customer = new Customer(this.username, this.password, this.firstName, this.lastName, this.address, this.email, null);
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            this.customerService.registerCustomer(customer);
//            setInfoMessage("Congratulations " + this.username + ", you have been registered!");
            clearFields();
        } catch (Exception e) {
            final UIInput input = (UIInput) facesContext.getViewRoot().findComponent("registerForm:usernameInput");
            input.setValid(false);
            setErrorMessage(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
            return;
        }
        try {
            facesContext.getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            setErrorMessage(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
        }
    }

    private void clearFields() {
        setUsername("");
        setPassword("");
        setFirstName("");
        setLastName("");
        setAddress("");
        setEmail("");
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
