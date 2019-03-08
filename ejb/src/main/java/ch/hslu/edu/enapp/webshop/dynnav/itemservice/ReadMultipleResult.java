
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
 *         &lt;element name="ReadMultiple_Result" type="{urn:microsoft-dynamics-schemas/page/item}Item_List" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "readMultipleResult"
})
@XmlRootElement(name = "ReadMultiple_Result")
public class ReadMultipleResult {

    @XmlElement(name = "ReadMultiple_Result")
    protected ItemList readMultipleResult;

    /**
     * Ruft den Wert der readMultipleResult-Eigenschaft ab.
     *
     * @return possible object is
     * {@link ItemList }
     */
    public ItemList getReadMultipleResult() {
        return readMultipleResult;
    }

    /**
     * Legt den Wert der readMultipleResult-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link ItemList }
     */
    public void setReadMultipleResult(ItemList value) {
        this.readMultipleResult = value;
    }

}
