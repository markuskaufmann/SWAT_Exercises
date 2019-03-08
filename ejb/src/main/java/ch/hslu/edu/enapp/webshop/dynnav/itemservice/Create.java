
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
 *         &lt;element name="Item" type="{urn:microsoft-dynamics-schemas/page/item}Item"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "item"
})
@XmlRootElement(name = "Create")
public class Create {

    @XmlElement(name = "Item", required = true)
    protected Item item;

    /**
     * Ruft den Wert der item-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Item }
     */
    public Item getItem() {
        return item;
    }

    /**
     * Legt den Wert der item-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Item }
     */
    public void setItem(Item value) {
        this.item = value;
    }

}
