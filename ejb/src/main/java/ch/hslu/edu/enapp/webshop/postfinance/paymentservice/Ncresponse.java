
package ch.hslu.edu.enapp.webshop.postfinance.paymentservice;

import javax.annotation.Generated;
import javax.xml.bind.annotation.*;


/**
 * <p>Java-Klasse f�r anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="orderID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="PAYID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="NCSTATUS" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="NCERROR" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="NCERRORPLUS" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ACCEPTANCE" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="STATUS" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ECI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="amount" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="currency" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="PM" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="BRAND" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 *
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "value"
})
@XmlRootElement(name = "ncresponse")
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class Ncresponse {

    @XmlValue
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String value;
    @XmlAttribute(name = "orderID")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String orderID;
    @XmlAttribute(name = "PAYID")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String payid;
    @XmlAttribute(name = "NCSTATUS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String ncstatus;
    @XmlAttribute(name = "NCERROR")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String ncerror;
    @XmlAttribute(name = "NCERRORPLUS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String ncerrorplus;
    @XmlAttribute(name = "ACCEPTANCE")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String acceptance;
    @XmlAttribute(name = "STATUS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String status;
    @XmlAttribute(name = "ECI")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String eci;
    @XmlAttribute(name = "amount")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String amount;
    @XmlAttribute(name = "currency")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String currency;
    @XmlAttribute(name = "PM")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String pm;
    @XmlAttribute(name = "BRAND")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String brand;

    /**
     * Ruft den Wert der value-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getValue() {
        return value;
    }

    /**
     * Legt den Wert der value-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Ruft den Wert der orderID-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getOrderID() {
        return orderID;
    }

    /**
     * Legt den Wert der orderID-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOrderID(String value) {
        this.orderID = value;
    }

    /**
     * Ruft den Wert der payid-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getPAYID() {
        return payid;
    }

    /**
     * Legt den Wert der payid-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPAYID(String value) {
        this.payid = value;
    }

    /**
     * Ruft den Wert der ncstatus-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getNCSTATUS() {
        return ncstatus;
    }

    /**
     * Legt den Wert der ncstatus-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNCSTATUS(String value) {
        this.ncstatus = value;
    }

    /**
     * Ruft den Wert der ncerror-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getNCERROR() {
        return ncerror;
    }

    /**
     * Legt den Wert der ncerror-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNCERROR(String value) {
        this.ncerror = value;
    }

    /**
     * Ruft den Wert der ncerrorplus-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getNCERRORPLUS() {
        return ncerrorplus;
    }

    /**
     * Legt den Wert der ncerrorplus-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNCERRORPLUS(String value) {
        this.ncerrorplus = value;
    }

    /**
     * Ruft den Wert der acceptance-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getACCEPTANCE() {
        return acceptance;
    }

    /**
     * Legt den Wert der acceptance-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setACCEPTANCE(String value) {
        this.acceptance = value;
    }

    /**
     * Ruft den Wert der status-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getSTATUS() {
        return status;
    }

    /**
     * Legt den Wert der status-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSTATUS(String value) {
        this.status = value;
    }

    /**
     * Ruft den Wert der eci-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getECI() {
        return eci;
    }

    /**
     * Legt den Wert der eci-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setECI(String value) {
        this.eci = value;
    }

    /**
     * Ruft den Wert der amount-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getAmount() {
        return amount;
    }

    /**
     * Legt den Wert der amount-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setAmount(String value) {
        this.amount = value;
    }

    /**
     * Ruft den Wert der currency-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCurrency() {
        return currency;
    }

    /**
     * Legt den Wert der currency-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Ruft den Wert der pm-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getPM() {
        return pm;
    }

    /**
     * Legt den Wert der pm-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPM(String value) {
        this.pm = value;
    }

    /**
     * Ruft den Wert der brand-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getBRAND() {
        return brand;
    }

    /**
     * Legt den Wert der brand-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2018-11-28T03:11:14+01:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setBRAND(String value) {
        this.brand = value;
    }

}
