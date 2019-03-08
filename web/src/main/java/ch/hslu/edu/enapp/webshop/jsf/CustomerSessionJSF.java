package ch.hslu.edu.enapp.webshop.jsf;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.exception.NoCustomerFoundException;
import ch.hslu.edu.enapp.webshop.service.CustomerSessionServiceLocal;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class CustomerSessionJSF implements Serializable {

    @Inject
    private CustomerSessionServiceLocal customerSessionService;

    private Customer customer;

    public Customer getLoggedInCustomer() {
        if (this.customer == null) {
            try {
                final FacesContext facesContext = FacesContext.getCurrentInstance();
                final String username = facesContext.getExternalContext().getUserPrincipal().getName();
                this.customer = this.customerSessionService.setLoggedInCustomer(username);
            } catch (NoCustomerFoundException | NullPointerException ex) {
                this.customer = null;
            }
        }
        return this.customer;
    }

    public String logout() {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }
}

