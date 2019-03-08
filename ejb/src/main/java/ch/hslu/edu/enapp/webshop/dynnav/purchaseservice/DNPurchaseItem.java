package ch.hslu.edu.enapp.webshop.dynnav.purchaseservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@XmlAccessorType(XmlAccessType.FIELD)
public final class DNPurchaseItem implements Serializable {

    @XmlElement(name = "msDynNAVItemNo")
    private String msDynNAVItemNo;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "quantity")
    private Integer quantity;

    @XmlElement(name = "totalLinePrice")
    private String totalLinePrice;

    public DNPurchaseItem(final String msDynNAVItemNo, final String description, final Integer quantity, final BigDecimal totalLinePrice) {
        this.msDynNAVItemNo = msDynNAVItemNo;
        this.description = description;
        this.quantity = quantity;
        this.totalLinePrice = new DecimalFormat("0.00").format(totalLinePrice);
    }

    public String getMsDynNAVItemNo() {
        return this.msDynNAVItemNo;
    }

    public void setMsDynNAVItemNo(final String msDynNAVItemNo) {
        this.msDynNAVItemNo = msDynNAVItemNo;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public String getTotalLinePrice() {
        return this.totalLinePrice;
    }

    public void setTotalLinePrice(final String totalLinePrice) {
        this.totalLinePrice = totalLinePrice;
    }
}
