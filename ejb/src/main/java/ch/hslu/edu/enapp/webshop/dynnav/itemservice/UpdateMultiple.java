
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
 *         &lt;element name="Item_List" type="{urn:microsoft-dynamics-schemas/page/item}Item_List"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "itemList"
})
@XmlRootElement(name = "UpdateMultiple")
public class UpdateMultiple {

    @XmlElement(name = "Item_List", required = true)
    protected ItemList itemList;

    /**
     * Ruft den Wert der itemList-Eigenschaft ab.
     *
     * @return possible object is
     * {@link ItemList }
     */
    public ItemList getItemList() {
        return itemList;
    }

    /**
     * Legt den Wert der itemList-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link ItemList }
     */
    public void setItemList(ItemList value) {
        this.itemList = value;
    }

}
