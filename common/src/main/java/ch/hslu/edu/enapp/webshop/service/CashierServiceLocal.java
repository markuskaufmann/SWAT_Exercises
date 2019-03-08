package ch.hslu.edu.enapp.webshop.service;

import ch.hslu.edu.enapp.webshop.dto.BasketItem;
import ch.hslu.edu.enapp.webshop.dto.PaymentAssembly;

import java.util.List;

public interface CashierServiceLocal {
    PaymentAssembly processPaymentAssembly(List<BasketItem> basketItems);
}
