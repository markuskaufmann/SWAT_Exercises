package ch.hslu.edu.enapp.webshop.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "getPurchaseItems", query = "SELECT pi FROM PurchaseitemEntity pi ORDER BY pi.id"),
        @NamedQuery(name = "getPurchaseItemsByPurchase", query = "SELECT pi FROM PurchaseitemEntity pi WHERE pi.purchase = :purchaseId"),
        @NamedQuery(name = "getPurchaseItemsByProduct", query = "SELECT pi FROM PurchaseitemEntity pi WHERE pi.product = :productNo"),
})
@Table(name = "purchaseitem", schema = "webshop", catalog = "")
public class PurchaseitemEntity {
    private int id;
    private int purchase;
    private String product;
    private int quantity;
    private PurchaseEntity purchaseByPurchase;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "purchase", nullable = false)
    public int getPurchase() {
        return purchase;
    }

    public void setPurchase(int purchase) {
        this.purchase = purchase;
    }

    @Basic
    @Column(name = "product", nullable = false)
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Basic
    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseitemEntity that = (PurchaseitemEntity) o;
        return id == that.id &&
                purchase == that.purchase &&
                Objects.equals(product, that.product) &&
                quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, purchase, product, quantity);
    }

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "purchase", referencedColumnName = "id")
    public PurchaseEntity getPurchaseByPurchase() {
        return purchaseByPurchase;
    }

    public void setPurchaseByPurchase(PurchaseEntity purchaseByPurchase) {
        this.purchaseByPurchase = purchaseByPurchase;
    }
}
