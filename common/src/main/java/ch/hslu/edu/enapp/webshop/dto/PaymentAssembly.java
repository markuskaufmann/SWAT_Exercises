package ch.hslu.edu.enapp.webshop.dto;

import java.util.List;
import java.util.Map;

public final class PaymentAssembly {

    private final List<Map.Entry<BasketItem, String>> itemCosts;
    private final String totalCost;

    public PaymentAssembly(final List<Map.Entry<BasketItem, String>> itemCosts, final String totalCost) {
        this.itemCosts = itemCosts;
        this.totalCost = totalCost;
    }

    public List<Map.Entry<BasketItem, String>> getItemCosts() {
        return itemCosts;
    }

    public String getTotalCost() {
        return totalCost;
    }
}
