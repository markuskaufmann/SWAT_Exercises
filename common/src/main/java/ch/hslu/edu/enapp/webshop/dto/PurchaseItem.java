package ch.hslu.edu.enapp.webshop.dto;

public final class PurchaseItem {

    private final int id;
    private final int purchaseId;
    private final String productNo;
    private final int quantity;

    public PurchaseItem(final int id, final int purchaseId, final String productNo, final int quantity) {
        this.id = id;
        this.purchaseId = purchaseId;
        this.productNo = productNo;
        this.quantity = quantity;
    }

    public int getId() {
        return this.id;
    }

    public int getPurchaseId() {
        return this.purchaseId;
    }

    public String getProductNo() {
        return this.productNo;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
