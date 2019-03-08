package ch.hslu.edu.enapp.webshop.jsf;

import ch.hslu.edu.enapp.webshop.dto.BasketItem;
import ch.hslu.edu.enapp.webshop.dto.PaymentAssembly;
import ch.hslu.edu.enapp.webshop.dto.PaymentMethod;
import ch.hslu.edu.enapp.webshop.dto.Product;
import ch.hslu.edu.enapp.webshop.service.BasketServiceLocal;
import ch.hslu.edu.enapp.webshop.service.CashierServiceLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class BasketJSF implements Serializable {
    private static final long serialVersionUID = 1492259801008765080L;

    private static final Logger LOGGER = LogManager.getLogger(BasketJSF.class);

    @Inject
    private BasketServiceLocal basketService;

    @Inject
    private CashierServiceLocal cashierService;

    private PaymentMethod selectedPaymentMethod;
    private String errorMessage;
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    public void addToBasket(final Product product, final int quantity) {
        this.basketService.addToBasket(product, quantity);
    }

    public void removeFromBasket(final Product product) {
        this.basketService.removeFromBasket(product);
    }

    public List<BasketItem> getBasketItems() {
        return this.basketService.getBasketItems();
    }

    public int getBasketSize() {
        return getBasketItems().size();
    }

    public void clearBasket() {
        this.basketService.clearBasket();
    }

    public void incrementItemQuantity(final Product product) {
        this.basketService.incrementItemQuantity(product);
    }

    public void decrementItemQuantity(final Product product) {
        this.basketService.decrementItemQuantity(product);
    }

    public PaymentAssembly getPaymentAssembly() {
        return this.cashierService.processPaymentAssembly(getBasketItems());
    }

    public String goToPaymentAssembly() {
        return "/secure/secured_payment_assembly?faces-redirect=true";
    }

    public String goToPaymentMethod() {
        this.paymentMethods = this.basketService.getAvailablePaymentMethods();
        updateSelectedPaymentMethod(this.paymentMethods.get(0));
        setErrorMessage(null);
        return "/secure/secured_payment?faces-redirect=true";
    }

    public String goToBasket() {
        return "/basket?faces-redirect=true";
    }

    public List<PaymentMethod> getPaymentMethods() {
        return this.paymentMethods;
    }

    public PaymentMethod getSelectedPaymentMethod() {
        return this.selectedPaymentMethod;
    }

    public void updateSelectedPaymentMethod(final PaymentMethod selectedPaymentMethod) {
        LOGGER.info("SET PAYMENTMETHOD: " + selectedPaymentMethod.getIssuer());
        this.selectedPaymentMethod = selectedPaymentMethod;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    private void setErrorMessage(final String message) {
        this.errorMessage = message;
    }

    public void checkout() {
        final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        final Principal principal = externalContext.getUserPrincipal();
        final String loggedInUser = (principal != null) ? principal.getName() : null;
        try {
            this.basketService.setAssociatedCustomer(loggedInUser);
        } catch (Exception e) {
            LOGGER.error("Error during checkout: " + e);
            try {
                externalContext.redirect("login.xhtml");
            } catch (IOException e1) {
                LOGGER.error("Error during checkout: " + e1);
            }
        }
        try {
            LOGGER.info("SET PAYMENTMETHOD: " + this.selectedPaymentMethod);
            this.basketService.setPaymentMethod(this.selectedPaymentMethod);
        } catch (Exception e) {
            LOGGER.error("Error during checkout: " + e);
            setErrorMessage(e.getMessage());
        }
        try {
            this.basketService.checkout();
        } catch (Exception e) {
            LOGGER.error("Error during checkout: " + e);
            setErrorMessage(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
            return;
        }
        try {
            externalContext.redirect("purchase_confirmation.xhtml");
        } catch (IOException e1) {
            LOGGER.error("Error during checkout: " + e1);
        }
    }
}
