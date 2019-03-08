package ch.hslu.edu.enapp.webshop.service;

import ch.hslu.edu.enapp.webshop.dto.BasketItem;
import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.dto.Purchase;
import ch.hslu.edu.enapp.webshop.exception.NoCustomerFoundException;

import java.util.List;

public interface PurchaseServiceLocal {
    List<Purchase> getPurchasesByCustomer(final Customer customer);

    List<Purchase> getPurchasesByState(final String state);

    Purchase persistPurchase(final Customer customer, final List<BasketItem> basketItems) throws NoCustomerFoundException;

    Purchase updatePayId(final int purchaseId, final String payId);

    Purchase updateCorrelationId(final int purchaseId, final String correlationId);

    Purchase updateSalesOrderNo(final int purchaseId, final String salesOrderNo);

    Purchase updateState(int purchaseId, String state);
}
