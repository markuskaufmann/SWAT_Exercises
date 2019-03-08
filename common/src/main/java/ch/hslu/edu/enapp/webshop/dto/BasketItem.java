package ch.hslu.edu.enapp.webshop.dto;

public final class BasketItem {

    private final Product product;
    private int quantity;

    public BasketItem(final Product product, final int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
