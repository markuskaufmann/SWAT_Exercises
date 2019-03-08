
package ch.hslu.edu.enapp.webshop.dynnav.itemservice;

import javax.xml.bind.annotation.*;


/**
 * <p>Java-Klasse f�r anonymous complex type.
 *
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Key" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "key"
})
@XmlRootElement(name = "IsUpdated")
public class IsUpdated {

    @XmlElement(name = "Key", required = true)
    protected String key;

    /**
     * Ruft den Wert der key-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getKey() {
        return key;
    }

    /**
     * Legt den Wert der key-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setKey(String value) {
        this.key = value;
    }

}
