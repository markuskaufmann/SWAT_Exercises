package ch.hslu.edu.enapp.webshop.business;

import ch.hslu.edu.enapp.webshop.dto.BasketItem;
import ch.hslu.edu.enapp.webshop.dto.PaymentAssembly;
import ch.hslu.edu.enapp.webshop.entity.ProductEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class CashierBean implements ch.hslu.edu.enapp.webshop.service.CashierServiceLocal {

    @PersistenceContext
    private EntityManager em;

    public CashierBean() {
    }

    @Override
    public PaymentAssembly processPaymentAssembly(final List<BasketItem> basketItems) {
        final Map<BasketItem, String> itemCosts = new HashMap<>();
        double totalCost = 0;
        if (basketItems != null) {
            for (final BasketItem item : basketItems) {
                final ProductEntity productEntity = this.em.find(ProductEntity.class, item.getProduct().getId());
                final double itemCost = productEntity.getUnitprice().doubleValue() * item.getQuantity();
                totalCost += itemCost;
                itemCosts.put(item, doubleToString(itemCost));
            }
        }
        return new PaymentAssembly(new ArrayList<>(itemCosts.entrySet()), doubleToString(totalCost));
    }

    private String doubleToString(final double value) {
        return String.format("%.2f", value);
    }
}
