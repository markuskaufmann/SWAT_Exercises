package ch.hslu.edu.enapp.webshop.service;

import ch.hslu.edu.enapp.webshop.dto.BasketItem;
import ch.hslu.edu.enapp.webshop.dto.PaymentMethod;
import ch.hslu.edu.enapp.webshop.dto.Product;
import ch.hslu.edu.enapp.webshop.exception.CheckoutFailedException;
import ch.hslu.edu.enapp.webshop.exception.NoCustomerFoundException;
import ch.hslu.edu.enapp.webshop.exception.NoCustomerLoggedInException;
import ch.hslu.edu.enapp.webshop.exception.NoPaymentMethodException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface BasketServiceLocal {
    void setAssociatedCustomer(String username) throws NoCustomerFoundException;

    void setPaymentMethod(PaymentMethod paymentMethod);

    void addToBasket(Product product, int quantity);

    void removeFromBasket(Product product);

    void clearBasket();

    void incrementItemQuantity(Product product);

    void decrementItemQuantity(Product product);

    List<BasketItem> getBasketItems();

    void checkout() throws NoCustomerLoggedInException, NoPaymentMethodException, CheckoutFailedException;

    List<PaymentMethod> getAvailablePaymentMethods();
}
