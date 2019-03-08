package ch.hslu.edu.enapp.webshop.business;

import ch.hslu.edu.enapp.webshop.business.dynnav.JMSPurchaseServiceBean;
import ch.hslu.edu.enapp.webshop.business.dynnav.StatusCheckServiceBean;
import ch.hslu.edu.enapp.webshop.business.postfinance.PaymentServiceBean;
import ch.hslu.edu.enapp.webshop.dto.*;
import ch.hslu.edu.enapp.webshop.exception.CheckoutFailedException;
import ch.hslu.edu.enapp.webshop.exception.NoCustomerFoundException;
import ch.hslu.edu.enapp.webshop.exception.NoCustomerLoggedInException;
import ch.hslu.edu.enapp.webshop.exception.NoPaymentMethodException;
import ch.hslu.edu.enapp.webshop.postfinance.paymentservice.Ncresponse;
import ch.hslu.edu.enapp.webshop.service.CustomerServiceLocal;
import ch.hslu.edu.enapp.webshop.service.PurchaseServiceLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateful
public class BasketBean implements ch.hslu.edu.enapp.webshop.service.BasketServiceLocal {

    private static final Logger LOGGER = LogManager.getLogger(BasketBean.class);

    @Inject
    private CustomerServiceLocal customerService;

    @Inject
    private PurchaseServiceLocal purchaseService;

    @Inject
    private JMSPurchaseServiceBean jmsPurchaseService;

    @Inject
    private PaymentServiceBean paymentService;

    @Inject
    private StatusCheckServiceBean statusCheckService;

    private Customer loggedInCustomer;

    private PaymentMethod paymentMethod;

    private List<BasketItem> productsInBasket = new ArrayList<>();

    public BasketBean() {
    }

    @Override
    public void setAssociatedCustomer(final String username) throws NoCustomerFoundException {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("The provided username can't be null or empty");
        }
        this.loggedInCustomer = this.customerService.getCustomerByName(username);
    }

    @Override
    public void setPaymentMethod(final PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("The provided payment method can't be null");
        }
        this.paymentMethod = paymentMethod;
    }

    @Override
    public void addToBasket(final Product product, final int quantity) {
        addOrUpdateBasketItem(product, quantity);
    }

    @Override
    public void removeFromBasket(final Product product) {
        final BasketItem basketItem = doesBasketContain(product);
        this.productsInBasket.remove(basketItem);
    }

    @Override
    public void clearBasket() {
        this.productsInBasket.clear();
    }

    @Override
    public void incrementItemQuantity(final Product product) {
        addOrUpdateBasketItem(product, 1);
    }

    @Override
    public void decrementItemQuantity(final Product product) {
        addOrUpdateBasketItem(product, -1);
    }

    @Override
    public List<BasketItem> getBasketItems() {
        return this.productsInBasket;
    }

    @Override
    public void checkout() throws NoCustomerLoggedInException, NoPaymentMethodException, CheckoutFailedException {
        if (this.loggedInCustomer == null) {
            throw new NoCustomerLoggedInException("No customer is currently logged in");
        }
        if (this.paymentMethod == null) {
            throw new NoPaymentMethodException("No payment method has been selected");
        }
        try {
            final Ncresponse ncresponse = this.paymentService.sendPaymentRequest(this.paymentMethod.getId(), this.productsInBasket);
            Purchase purchase = this.purchaseService.persistPurchase(this.loggedInCustomer, this.productsInBasket);
            purchase = this.purchaseService.updatePayId(purchase.getId(), ncresponse.getPAYID());
            final String correlationId = this.jmsPurchaseService.sendJMSPurchaseMessage(purchase, ncresponse);
            purchase = this.purchaseService.updateCorrelationId(purchase.getId(), correlationId);
            this.statusCheckService.checkStatus(purchase);
            clearBasket();
        } catch (Exception e) {
            LOGGER.error("Error during checkout: " + e);
            throw new CheckoutFailedException("Error while performing checkout", e);
        }
    }

    @Override
    public List<PaymentMethod> getAvailablePaymentMethods() {
        return this.paymentService.getAvailablePaymentMethods();
    }

    private void addOrUpdateBasketItem(final Product product, final int quantity) {
        final BasketItem itemInBasket = doesBasketContain(product);
        if (itemInBasket == null) {
            if (quantity <= 0) {
                return;
            }
            final BasketItem newItem = new BasketItem(product, quantity);
            this.productsInBasket.add(newItem);
        } else {
            updateBasketItemQuantity(itemInBasket, quantity);
        }
    }

    private void updateBasketItemQuantity(final BasketItem basketItem, final int relativeQuantity) {
        final int updatedQuantity = basketItem.getQuantity() + relativeQuantity;
        if (updatedQuantity <= 0) {
            this.productsInBasket.remove(basketItem);
            return;
        }
        basketItem.setQuantity(updatedQuantity);
    }

    private BasketItem doesBasketContain(final Product product) {
        for (final BasketItem item : this.productsInBasket) {
            if (item.getProduct().getId() == product.getId()) {
                return item;
            }
        }
        return null;
    }
}
