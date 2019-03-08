package ch.hslu.edu.enapp.webshop.dynnav.purchaseservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public final class DNPurchaseCustomer implements Serializable {

    @XmlElement(name = "dynNavCustNo")
    private String dynNavCustNo;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "address")
    private String address;

    @XmlElement(name = "postCode")
    private String postCode;

    @XmlElement(name = "city")
    private String city;

    @XmlElement(name = "shopLoginname")
    private String shopLoginname;

    public DNPurchaseCustomer(final String dynNavCustNo, final String name, final String address, final String postCode, final String city,
                              final String shopLoginname) {
        this.dynNavCustNo = dynNavCustNo;
        this.name = name;
        this.address = address;
        this.postCode = postCode;
        this.city = city;
        this.shopLoginname = shopLoginname;
    }

    public String getDynNavCustNo() {
        return this.dynNavCustNo;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public String getCity() {
        return this.city;
    }

    public String getShopLoginname() {
        return this.shopLoginname;
    }
}
