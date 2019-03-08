package ch.hslu.edu.enapp.webshop.dynnav.purchaseservice;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;

@XmlRootElement(name = "purchaseMessage")
@XmlAccessorType(XmlAccessType.FIELD)
public final class PurchaseMessage implements Serializable {

    @XmlElement(name = "payId")
    private String payId;

    @XmlElement(name = "purchaseId")
    private String purchaseId;

    @XmlElement(name = "student")
    private String student;

    @XmlElement(name = "totalPrice")
    private String totalPrice;

    @XmlElement(name = "date")
    private String date;

    @XmlElement(name = "customer")
    private DNPurchaseCustomer purchaseCustomer;

    @XmlElementWrapper(name = "lines")
    @XmlElement(name = "line")
    private List<DNPurchaseItem> purchaseItems;

    public PurchaseMessage() {
    }

    public PurchaseMessage(final String payId, final int purchaseId, final String student, final BigDecimal totalPrice,
                           final Timestamp date) {
        this.payId = payId;
        this.purchaseId = String.valueOf(purchaseId);
        this.student = student;
        this.totalPrice = new DecimalFormat("0.00").format(totalPrice);
        this.date = date.toString();
    }

    public String getPayId() {
        return this.payId;
    }

    public String getPurchaseId() {
        return this.purchaseId;
    }

    public String getStudent() {
        return this.student;
    }

    public String getTotalPrice() {
        return this.totalPrice;
    }

    public String getDate() {
        return this.date;
    }

    public DNPurchaseCustomer getPurchaseCustomer() {
        return this.purchaseCustomer;
    }

    public void setPurchaseCustomer(final DNPurchaseCustomer purchaseCustomer) {
        this.purchaseCustomer = purchaseCustomer;
    }

    public List<DNPurchaseItem> getPurchaseItems() {
        return this.purchaseItems;
    }

    public void setPurchaseItems(final List<DNPurchaseItem> purchaseItems) {
        this.purchaseItems = purchaseItems;
    }
}
