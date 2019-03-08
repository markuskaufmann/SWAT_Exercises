
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
 *         &lt;element name="Delete_Result" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "deleteResult"
})
@XmlRootElement(name = "Delete_Result")
public class DeleteResult {

    @XmlElement(name = "Delete_Result")
    protected boolean deleteResult;

    /**
     * Ruft den Wert der deleteResult-Eigenschaft ab.
     */
    public boolean isDeleteResult() {
        return deleteResult;
    }

    /**
     * Legt den Wert der deleteResult-Eigenschaft fest.
     */
    public void setDeleteResult(boolean value) {
        this.deleteResult = value;
    }

}
