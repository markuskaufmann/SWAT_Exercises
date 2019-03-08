
package ch.hslu.edu.enapp.webshop.dynnav.itemservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


/**
 * <p>Java-Klasse f�r Item complex type.
 *
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 *
 * <pre>
 * &lt;complexType name="Item">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="No" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Owner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Kind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Mediafile_Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Manufacturer_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Base_Unit_of_Measure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Bill_of_Materials" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Shelf_No" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Automatic_Ext_Texts" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Created_From_Nonstock_Item" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Item_Category_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Product_Group_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Last_Date_Modified" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="Search_Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Inventory" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Qty_on_Purch_Order" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Qty_on_Prod_Order" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Qty_on_Component_Lines" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Qty_on_Sales_Order" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Qty_on_Service_Order" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Service_Item_Group" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Blocked" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="No_Stockkeeping" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Costing_Method" type="{urn:microsoft-dynamics-schemas/page/item}Costing_Method" minOccurs="0"/>
 *         &lt;element name="Cost_is_Adjusted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Cost_is_Posted_to_G_L" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Standard_Cost" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Unit_Cost" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Overhead_Rate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Indirect_Cost_Percent" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Last_Direct_Cost" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Price_Profit_Calculation" type="{urn:microsoft-dynamics-schemas/page/item}Price_Profit_Calculation" minOccurs="0"/>
 *         &lt;element name="Profit_Percent" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Unit_Price" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Gen_Prod_Posting_Group" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VAT_Prod_Posting_Group" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Inventory_Posting_Group" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Net_Invoiced_Qty" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Allow_Invoice_Disc" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Item_Disc_Group" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sales_Unit_of_Measure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sale_blocked" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Replenishment_System" type="{urn:microsoft-dynamics-schemas/page/item}Replenishment_System" minOccurs="0"/>
 *         &lt;element name="Vendor_No" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Vendor_Item_No" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Purch_Unit_of_Measure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Lead_Time_Calculation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Manufacturing_Policy" type="{urn:microsoft-dynamics-schemas/page/item}Manufacturing_Policy" minOccurs="0"/>
 *         &lt;element name="Routing_No" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Production_BOM_No" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rounding_Precision" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Flushing_Method" type="{urn:microsoft-dynamics-schemas/page/item}Flushing_Method" minOccurs="0"/>
 *         &lt;element name="Scrap_Percent" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Lot_Size" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Purchase_blocked" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Reordering_Policy" type="{urn:microsoft-dynamics-schemas/page/item}Reordering_Policy" minOccurs="0"/>
 *         &lt;element name="Include_Inventory" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Reserve" type="{urn:microsoft-dynamics-schemas/page/item}Reserve" minOccurs="0"/>
 *         &lt;element name="Order_Tracking_Policy" type="{urn:microsoft-dynamics-schemas/page/item}Order_Tracking_Policy" minOccurs="0"/>
 *         &lt;element name="Stockkeeping_Unit_Exists" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Critical" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Reorder_Cycle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Safety_Lead_Time" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Safety_Stock_Quantity" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Reorder_Point" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Reorder_Quantity" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Maximum_Inventory" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Minimum_Order_Quantity" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Maximum_Order_Quantity" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Order_Multiple" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Tariff_No" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Country_Region_of_Origin_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Net_Weight" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Gross_Weight" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Item_Tracking_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Serial_Nos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Lot_Nos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Expiration_Calculation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Common_Item_No" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Special_Equipment_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Put_away_Template_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Put_away_Unit_of_Measure_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Phys_Invt_Counting_Period_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Last_Phys_Invt_Date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="Last_Counting_Period_Update" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="Next_Counting_Period" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Identifier_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Use_Cross_Docking" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Item", propOrder = {
        "key",
        "no",
        "description",
        "owner",
        "kind",
        "mediafileName",
        "manufacturerCode",
        "baseUnitOfMeasure",
        "billOfMaterials",
        "shelfNo",
        "automaticExtTexts",
        "createdFromNonstockItem",
        "itemCategoryCode",
        "productGroupCode",
        "lastDateModified",
        "searchDescription",
        "inventory",
        "qtyOnPurchOrder",
        "qtyOnProdOrder",
        "qtyOnComponentLines",
        "qtyOnSalesOrder",
        "qtyOnServiceOrder",
        "serviceItemGroup",
        "blocked",
        "noStockkeeping",
        "costingMethod",
        "costIsAdjusted",
        "costIsPostedToGL",
        "standardCost",
        "unitCost",
        "overheadRate",
        "indirectCostPercent",
        "lastDirectCost",
        "priceProfitCalculation",
        "profitPercent",
        "unitPrice",
        "genProdPostingGroup",
        "vatProdPostingGroup",
        "inventoryPostingGroup",
        "netInvoicedQty",
        "allowInvoiceDisc",
        "itemDiscGroup",
        "salesUnitOfMeasure",
        "saleBlocked",
        "replenishmentSystem",
        "vendorNo",
        "vendorItemNo",
        "purchUnitOfMeasure",
        "leadTimeCalculation",
        "manufacturingPolicy",
        "routingNo",
        "productionBOMNo",
        "roundingPrecision",
        "flushingMethod",
        "scrapPercent",
        "lotSize",
        "purchaseBlocked",
        "reorderingPolicy",
        "includeInventory",
        "reserve",
        "orderTrackingPolicy",
        "stockkeepingUnitExists",
        "critical",
        "reorderCycle",
        "safetyLeadTime",
        "safetyStockQuantity",
        "reorderPoint",
        "reorderQuantity",
        "maximumInventory",
        "minimumOrderQuantity",
        "maximumOrderQuantity",
        "orderMultiple",
        "tariffNo",
        "countryRegionOfOriginCode",
        "netWeight",
        "grossWeight",
        "itemTrackingCode",
        "serialNos",
        "lotNos",
        "expirationCalculation",
        "commonItemNo",
        "specialEquipmentCode",
        "putAwayTemplateCode",
        "putAwayUnitOfMeasureCode",
        "physInvtCountingPeriodCode",
        "lastPhysInvtDate",
        "lastCountingPeriodUpdate",
        "nextCountingPeriod",
        "identifierCode",
        "useCrossDocking"
})
public class Item {

    @XmlElement(name = "Key")
    protected String key;
    @XmlElement(name = "No")
    protected String no;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Owner")
    protected String owner;
    @XmlElement(name = "Kind")
    protected String kind;
    @XmlElement(name = "Mediafile_Name")
    protected String mediafileName;
    @XmlElement(name = "Manufacturer_Code")
    protected String manufacturerCode;
    @XmlElement(name = "Base_Unit_of_Measure")
    protected String baseUnitOfMeasure;
    @XmlElement(name = "Bill_of_Materials")
    protected Boolean billOfMaterials;
    @XmlElement(name = "Shelf_No")
    protected String shelfNo;
    @XmlElement(name = "Automatic_Ext_Texts")
    protected Boolean automaticExtTexts;
    @XmlElement(name = "Created_From_Nonstock_Item")
    protected Boolean createdFromNonstockItem;
    @XmlElement(name = "Item_Category_Code")
    protected String itemCategoryCode;
    @XmlElement(name = "Product_Group_Code")
    protected String productGroupCode;
    @XmlElement(name = "Last_Date_Modified")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastDateModified;
    @XmlElement(name = "Search_Description")
    protected String searchDescription;
    @XmlElement(name = "Inventory")
    protected BigDecimal inventory;
    @XmlElement(name = "Qty_on_Purch_Order")
    protected BigDecimal qtyOnPurchOrder;
    @XmlElement(name = "Qty_on_Prod_Order")
    protected BigDecimal qtyOnProdOrder;
    @XmlElement(name = "Qty_on_Component_Lines")
    protected BigDecimal qtyOnComponentLines;
    @XmlElement(name = "Qty_on_Sales_Order")
    protected BigDecimal qtyOnSalesOrder;
    @XmlElement(name = "Qty_on_Service_Order")
    protected BigDecimal qtyOnServiceOrder;
    @XmlElement(name = "Service_Item_Group")
    protected String serviceItemGroup;
    @XmlElement(name = "Blocked")
    protected Boolean blocked;
    @XmlElement(name = "No_Stockkeeping")
    protected Boolean noStockkeeping;
    @XmlElement(name = "Costing_Method")
    protected CostingMethod costingMethod;
    @XmlElement(name = "Cost_is_Adjusted")
    protected Boolean costIsAdjusted;
    @XmlElement(name = "Cost_is_Posted_to_G_L")
    protected Boolean costIsPostedToGL;
    @XmlElement(name = "Standard_Cost")
    protected BigDecimal standardCost;
    @XmlElement(name = "Unit_Cost")
    protected BigDecimal unitCost;
    @XmlElement(name = "Overhead_Rate")
    protected BigDecimal overheadRate;
    @XmlElement(name = "Indirect_Cost_Percent")
    protected BigDecimal indirectCostPercent;
    @XmlElement(name = "Last_Direct_Cost")
    protected BigDecimal lastDirectCost;
    @XmlElement(name = "Price_Profit_Calculation")
    protected PriceProfitCalculation priceProfitCalculation;
    @XmlElement(name = "Profit_Percent")
    protected BigDecimal profitPercent;
    @XmlElement(name = "Unit_Price")
    protected BigDecimal unitPrice;
    @XmlElement(name = "Gen_Prod_Posting_Group")
    protected String genProdPostingGroup;
    @XmlElement(name = "VAT_Prod_Posting_Group")
    protected String vatProdPostingGroup;
    @XmlElement(name = "Inventory_Posting_Group")
    protected String inventoryPostingGroup;
    @XmlElement(name = "Net_Invoiced_Qty")
    protected BigDecimal netInvoicedQty;
    @XmlElement(name = "Allow_Invoice_Disc")
    protected Boolean allowInvoiceDisc;
    @XmlElement(name = "Item_Disc_Group")
    protected String itemDiscGroup;
    @XmlElement(name = "Sales_Unit_of_Measure")
    protected String salesUnitOfMeasure;
    @XmlElement(name = "Sale_blocked")
    protected Boolean saleBlocked;
    @XmlElement(name = "Replenishment_System")
    protected ReplenishmentSystem replenishmentSystem;
    @XmlElement(name = "Vendor_No")
    protected String vendorNo;
    @XmlElement(name = "Vendor_Item_No")
    protected String vendorItemNo;
    @XmlElement(name = "Purch_Unit_of_Measure")
    protected String purchUnitOfMeasure;
    @XmlElement(name = "Lead_Time_Calculation")
    protected String leadTimeCalculation;
    @XmlElement(name = "Manufacturing_Policy")
    protected ManufacturingPolicy manufacturingPolicy;
    @XmlElement(name = "Routing_No")
    protected String routingNo;
    @XmlElement(name = "Production_BOM_No")
    protected String productionBOMNo;
    @XmlElement(name = "Rounding_Precision")
    protected BigDecimal roundingPrecision;
    @XmlElement(name = "Flushing_Method")
    protected FlushingMethod flushingMethod;
    @XmlElement(name = "Scrap_Percent")
    protected BigDecimal scrapPercent;
    @XmlElement(name = "Lot_Size")
    protected BigDecimal lotSize;
    @XmlElement(name = "Purchase_blocked")
    protected Boolean purchaseBlocked;
    @XmlElement(name = "Reordering_Policy")
    protected ReorderingPolicy reorderingPolicy;
    @XmlElement(name = "Include_Inventory")
    protected Boolean includeInventory;
    @XmlElement(name = "Reserve")
    protected Reserve reserve;
    @XmlElement(name = "Order_Tracking_Policy")
    protected OrderTrackingPolicy orderTrackingPolicy;
    @XmlElement(name = "Stockkeeping_Unit_Exists")
    protected Boolean stockkeepingUnitExists;
    @XmlElement(name = "Critical")
    protected Boolean critical;
    @XmlElement(name = "Reorder_Cycle")
    protected String reorderCycle;
    @XmlElement(name = "Safety_Lead_Time")
    protected String safetyLeadTime;
    @XmlElement(name = "Safety_Stock_Quantity")
    protected BigDecimal safetyStockQuantity;
    @XmlElement(name = "Reorder_Point")
    protected BigDecimal reorderPoint;
    @XmlElement(name = "Reorder_Quantity")
    protected BigDecimal reorderQuantity;
    @XmlElement(name = "Maximum_Inventory")
    protected BigDecimal maximumInventory;
    @XmlElement(name = "Minimum_Order_Quantity")
    protected BigDecimal minimumOrderQuantity;
    @XmlElement(name = "Maximum_Order_Quantity")
    protected BigDecimal maximumOrderQuantity;
    @XmlElement(name = "Order_Multiple")
    protected BigDecimal orderMultiple;
    @XmlElement(name = "Tariff_No")
    protected String tariffNo;
    @XmlElement(name = "Country_Region_of_Origin_Code")
    protected String countryRegionOfOriginCode;
    @XmlElement(name = "Net_Weight")
    protected BigDecimal netWeight;
    @XmlElement(name = "Gross_Weight")
    protected BigDecimal grossWeight;
    @XmlElement(name = "Item_Tracking_Code")
    protected String itemTrackingCode;
    @XmlElement(name = "Serial_Nos")
    protected String serialNos;
    @XmlElement(name = "Lot_Nos")
    protected String lotNos;
    @XmlElement(name = "Expiration_Calculation")
    protected String expirationCalculation;
    @XmlElement(name = "Common_Item_No")
    protected String commonItemNo;
    @XmlElement(name = "Special_Equipment_Code")
    protected String specialEquipmentCode;
    @XmlElement(name = "Put_away_Template_Code")
    protected String putAwayTemplateCode;
    @XmlElement(name = "Put_away_Unit_of_Measure_Code")
    protected String putAwayUnitOfMeasureCode;
    @XmlElement(name = "Phys_Invt_Counting_Period_Code")
    protected String physInvtCountingPeriodCode;
    @XmlElement(name = "Last_Phys_Invt_Date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastPhysInvtDate;
    @XmlElement(name = "Last_Counting_Period_Update")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastCountingPeriodUpdate;
    @XmlElement(name = "Next_Counting_Period")
    protected String nextCountingPeriod;
    @XmlElement(name = "Identifier_Code")
    protected String identifierCode;
    @XmlElement(name = "Use_Cross_Docking")
    protected Boolean useCrossDocking;

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

    /**
     * Ruft den Wert der no-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNo() {
        return no;
    }

    /**
     * Legt den Wert der no-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNo(String value) {
        this.no = value;
    }

    /**
     * Ruft den Wert der description-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDescription() {
        return description;
    }

    /**
     * Legt den Wert der description-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Ruft den Wert der owner-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Legt den Wert der owner-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOwner(String value) {
        this.owner = value;
    }

    /**
     * Ruft den Wert der kind-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getKind() {
        return kind;
    }

    /**
     * Legt den Wert der kind-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setKind(String value) {
        this.kind = value;
    }

    /**
     * Ruft den Wert der mediafileName-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMediafileName() {
        return mediafileName;
    }

    /**
     * Legt den Wert der mediafileName-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMediafileName(String value) {
        this.mediafileName = value;
    }

    /**
     * Ruft den Wert der manufacturerCode-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getManufacturerCode() {
        return manufacturerCode;
    }

    /**
     * Legt den Wert der manufacturerCode-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setManufacturerCode(String value) {
        this.manufacturerCode = value;
    }

    /**
     * Ruft den Wert der baseUnitOfMeasure-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBaseUnitOfMeasure() {
        return baseUnitOfMeasure;
    }

    /**
     * Legt den Wert der baseUnitOfMeasure-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBaseUnitOfMeasure(String value) {
        this.baseUnitOfMeasure = value;
    }

    /**
     * Ruft den Wert der billOfMaterials-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isBillOfMaterials() {
        return billOfMaterials;
    }

    /**
     * Legt den Wert der billOfMaterials-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setBillOfMaterials(Boolean value) {
        this.billOfMaterials = value;
    }

    /**
     * Ruft den Wert der shelfNo-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getShelfNo() {
        return shelfNo;
    }

    /**
     * Legt den Wert der shelfNo-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShelfNo(String value) {
        this.shelfNo = value;
    }

    /**
     * Ruft den Wert der automaticExtTexts-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isAutomaticExtTexts() {
        return automaticExtTexts;
    }

    /**
     * Legt den Wert der automaticExtTexts-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setAutomaticExtTexts(Boolean value) {
        this.automaticExtTexts = value;
    }

    /**
     * Ruft den Wert der createdFromNonstockItem-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isCreatedFromNonstockItem() {
        return createdFromNonstockItem;
    }

    /**
     * Legt den Wert der createdFromNonstockItem-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setCreatedFromNonstockItem(Boolean value) {
        this.createdFromNonstockItem = value;
    }

    /**
     * Ruft den Wert der itemCategoryCode-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getItemCategoryCode() {
        return itemCategoryCode;
    }

    /**
     * Legt den Wert der itemCategoryCode-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemCategoryCode(String value) {
        this.itemCategoryCode = value;
    }

    /**
     * Ruft den Wert der productGroupCode-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getProductGroupCode() {
        return productGroupCode;
    }

    /**
     * Legt den Wert der productGroupCode-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProductGroupCode(String value) {
        this.productGroupCode = value;
    }

    /**
     * Ruft den Wert der lastDateModified-Eigenschaft ab.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getLastDateModified() {
        return lastDateModified;
    }

    /**
     * Legt den Wert der lastDateModified-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setLastDateModified(XMLGregorianCalendar value) {
        this.lastDateModified = value;
    }

    /**
     * Ruft den Wert der searchDescription-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSearchDescription() {
        return searchDescription;
    }

    /**
     * Legt den Wert der searchDescription-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSearchDescription(String value) {
        this.searchDescription = value;
    }

    /**
     * Ruft den Wert der inventory-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getInventory() {
        return inventory;
    }

    /**
     * Legt den Wert der inventory-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setInventory(BigDecimal value) {
        this.inventory = value;
    }

    /**
     * Ruft den Wert der qtyOnPurchOrder-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getQtyOnPurchOrder() {
        return qtyOnPurchOrder;
    }

    /**
     * Legt den Wert der qtyOnPurchOrder-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setQtyOnPurchOrder(BigDecimal value) {
        this.qtyOnPurchOrder = value;
    }

    /**
     * Ruft den Wert der qtyOnProdOrder-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getQtyOnProdOrder() {
        return qtyOnProdOrder;
    }

    /**
     * Legt den Wert der qtyOnProdOrder-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setQtyOnProdOrder(BigDecimal value) {
        this.qtyOnProdOrder = value;
    }

    /**
     * Ruft den Wert der qtyOnComponentLines-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getQtyOnComponentLines() {
        return qtyOnComponentLines;
    }

    /**
     * Legt den Wert der qtyOnComponentLines-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setQtyOnComponentLines(BigDecimal value) {
        this.qtyOnComponentLines = value;
    }

    /**
     * Ruft den Wert der qtyOnSalesOrder-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getQtyOnSalesOrder() {
        return qtyOnSalesOrder;
    }

    /**
     * Legt den Wert der qtyOnSalesOrder-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setQtyOnSalesOrder(BigDecimal value) {
        this.qtyOnSalesOrder = value;
    }

    /**
     * Ruft den Wert der qtyOnServiceOrder-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getQtyOnServiceOrder() {
        return qtyOnServiceOrder;
    }

    /**
     * Legt den Wert der qtyOnServiceOrder-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setQtyOnServiceOrder(BigDecimal value) {
        this.qtyOnServiceOrder = value;
    }

    /**
     * Ruft den Wert der serviceItemGroup-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getServiceItemGroup() {
        return serviceItemGroup;
    }

    /**
     * Legt den Wert der serviceItemGroup-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setServiceItemGroup(String value) {
        this.serviceItemGroup = value;
    }

    /**
     * Ruft den Wert der blocked-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isBlocked() {
        return blocked;
    }

    /**
     * Legt den Wert der blocked-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setBlocked(Boolean value) {
        this.blocked = value;
    }

    /**
     * Ruft den Wert der noStockkeeping-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isNoStockkeeping() {
        return noStockkeeping;
    }

    /**
     * Legt den Wert der noStockkeeping-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setNoStockkeeping(Boolean value) {
        this.noStockkeeping = value;
    }

    /**
     * Ruft den Wert der costingMethod-Eigenschaft ab.
     *
     * @return possible object is
     * {@link CostingMethod }
     */
    public CostingMethod getCostingMethod() {
        return costingMethod;
    }

    /**
     * Legt den Wert der costingMethod-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link CostingMethod }
     */
    public void setCostingMethod(CostingMethod value) {
        this.costingMethod = value;
    }

    /**
     * Ruft den Wert der costIsAdjusted-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isCostIsAdjusted() {
        return costIsAdjusted;
    }

    /**
     * Legt den Wert der costIsAdjusted-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setCostIsAdjusted(Boolean value) {
        this.costIsAdjusted = value;
    }

    /**
     * Ruft den Wert der costIsPostedToGL-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isCostIsPostedToGL() {
        return costIsPostedToGL;
    }

    /**
     * Legt den Wert der costIsPostedToGL-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setCostIsPostedToGL(Boolean value) {
        this.costIsPostedToGL = value;
    }

    /**
     * Ruft den Wert der standardCost-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getStandardCost() {
        return standardCost;
    }

    /**
     * Legt den Wert der standardCost-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setStandardCost(BigDecimal value) {
        this.standardCost = value;
    }

    /**
     * Ruft den Wert der unitCost-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getUnitCost() {
        return unitCost;
    }

    /**
     * Legt den Wert der unitCost-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setUnitCost(BigDecimal value) {
        this.unitCost = value;
    }

    /**
     * Ruft den Wert der overheadRate-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getOverheadRate() {
        return overheadRate;
    }

    /**
     * Legt den Wert der overheadRate-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setOverheadRate(BigDecimal value) {
        this.overheadRate = value;
    }

    /**
     * Ruft den Wert der indirectCostPercent-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getIndirectCostPercent() {
        return indirectCostPercent;
    }

    /**
     * Legt den Wert der indirectCostPercent-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setIndirectCostPercent(BigDecimal value) {
        this.indirectCostPercent = value;
    }

    /**
     * Ruft den Wert der lastDirectCost-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getLastDirectCost() {
        return lastDirectCost;
    }

    /**
     * Legt den Wert der lastDirectCost-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setLastDirectCost(BigDecimal value) {
        this.lastDirectCost = value;
    }

    /**
     * Ruft den Wert der priceProfitCalculation-Eigenschaft ab.
     *
     * @return possible object is
     * {@link PriceProfitCalculation }
     */
    public PriceProfitCalculation getPriceProfitCalculation() {
        return priceProfitCalculation;
    }

    /**
     * Legt den Wert der priceProfitCalculation-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link PriceProfitCalculation }
     */
    public void setPriceProfitCalculation(PriceProfitCalculation value) {
        this.priceProfitCalculation = value;
    }

    /**
     * Ruft den Wert der profitPercent-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getProfitPercent() {
        return profitPercent;
    }

    /**
     * Legt den Wert der profitPercent-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setProfitPercent(BigDecimal value) {
        this.profitPercent = value;
    }

    /**
     * Ruft den Wert der unitPrice-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * Legt den Wert der unitPrice-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setUnitPrice(BigDecimal value) {
        this.unitPrice = value;
    }

    /**
     * Ruft den Wert der genProdPostingGroup-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getGenProdPostingGroup() {
        return genProdPostingGroup;
    }

    /**
     * Legt den Wert der genProdPostingGroup-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGenProdPostingGroup(String value) {
        this.genProdPostingGroup = value;
    }

    /**
     * Ruft den Wert der vatProdPostingGroup-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVATProdPostingGroup() {
        return vatProdPostingGroup;
    }

    /**
     * Legt den Wert der vatProdPostingGroup-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVATProdPostingGroup(String value) {
        this.vatProdPostingGroup = value;
    }

    /**
     * Ruft den Wert der inventoryPostingGroup-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getInventoryPostingGroup() {
        return inventoryPostingGroup;
    }

    /**
     * Legt den Wert der inventoryPostingGroup-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInventoryPostingGroup(String value) {
        this.inventoryPostingGroup = value;
    }

    /**
     * Ruft den Wert der netInvoicedQty-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getNetInvoicedQty() {
        return netInvoicedQty;
    }

    /**
     * Legt den Wert der netInvoicedQty-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setNetInvoicedQty(BigDecimal value) {
        this.netInvoicedQty = value;
    }

    /**
     * Ruft den Wert der allowInvoiceDisc-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isAllowInvoiceDisc() {
        return allowInvoiceDisc;
    }

    /**
     * Legt den Wert der allowInvoiceDisc-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setAllowInvoiceDisc(Boolean value) {
        this.allowInvoiceDisc = value;
    }

    /**
     * Ruft den Wert der itemDiscGroup-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getItemDiscGroup() {
        return itemDiscGroup;
    }

    /**
     * Legt den Wert der itemDiscGroup-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemDiscGroup(String value) {
        this.itemDiscGroup = value;
    }

    /**
     * Ruft den Wert der salesUnitOfMeasure-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSalesUnitOfMeasure() {
        return salesUnitOfMeasure;
    }

    /**
     * Legt den Wert der salesUnitOfMeasure-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSalesUnitOfMeasure(String value) {
        this.salesUnitOfMeasure = value;
    }

    /**
     * Ruft den Wert der saleBlocked-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isSaleBlocked() {
        return saleBlocked;
    }

    /**
     * Legt den Wert der saleBlocked-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setSaleBlocked(Boolean value) {
        this.saleBlocked = value;
    }

    /**
     * Ruft den Wert der replenishmentSystem-Eigenschaft ab.
     *
     * @return possible object is
     * {@link ReplenishmentSystem }
     */
    public ReplenishmentSystem getReplenishmentSystem() {
        return replenishmentSystem;
    }

    /**
     * Legt den Wert der replenishmentSystem-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link ReplenishmentSystem }
     */
    public void setReplenishmentSystem(ReplenishmentSystem value) {
        this.replenishmentSystem = value;
    }

    /**
     * Ruft den Wert der vendorNo-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVendorNo() {
        return vendorNo;
    }

    /**
     * Legt den Wert der vendorNo-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVendorNo(String value) {
        this.vendorNo = value;
    }

    /**
     * Ruft den Wert der vendorItemNo-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVendorItemNo() {
        return vendorItemNo;
    }

    /**
     * Legt den Wert der vendorItemNo-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVendorItemNo(String value) {
        this.vendorItemNo = value;
    }

    /**
     * Ruft den Wert der purchUnitOfMeasure-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPurchUnitOfMeasure() {
        return purchUnitOfMeasure;
    }

    /**
     * Legt den Wert der purchUnitOfMeasure-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPurchUnitOfMeasure(String value) {
        this.purchUnitOfMeasure = value;
    }

    /**
     * Ruft den Wert der leadTimeCalculation-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLeadTimeCalculation() {
        return leadTimeCalculation;
    }

    /**
     * Legt den Wert der leadTimeCalculation-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLeadTimeCalculation(String value) {
        this.leadTimeCalculation = value;
    }

    /**
     * Ruft den Wert der manufacturingPolicy-Eigenschaft ab.
     *
     * @return possible object is
     * {@link ManufacturingPolicy }
     */
    public ManufacturingPolicy getManufacturingPolicy() {
        return manufacturingPolicy;
    }

    /**
     * Legt den Wert der manufacturingPolicy-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link ManufacturingPolicy }
     */
    public void setManufacturingPolicy(ManufacturingPolicy value) {
        this.manufacturingPolicy = value;
    }

    /**
     * Ruft den Wert der routingNo-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRoutingNo() {
        return routingNo;
    }

    /**
     * Legt den Wert der routingNo-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRoutingNo(String value) {
        this.routingNo = value;
    }

    /**
     * Ruft den Wert der productionBOMNo-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getProductionBOMNo() {
        return productionBOMNo;
    }

    /**
     * Legt den Wert der productionBOMNo-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProductionBOMNo(String value) {
        this.productionBOMNo = value;
    }

    /**
     * Ruft den Wert der roundingPrecision-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getRoundingPrecision() {
        return roundingPrecision;
    }

    /**
     * Legt den Wert der roundingPrecision-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setRoundingPrecision(BigDecimal value) {
        this.roundingPrecision = value;
    }

    /**
     * Ruft den Wert der flushingMethod-Eigenschaft ab.
     *
     * @return possible object is
     * {@link FlushingMethod }
     */
    public FlushingMethod getFlushingMethod() {
        return flushingMethod;
    }

    /**
     * Legt den Wert der flushingMethod-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link FlushingMethod }
     */
    public void setFlushingMethod(FlushingMethod value) {
        this.flushingMethod = value;
    }

    /**
     * Ruft den Wert der scrapPercent-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getScrapPercent() {
        return scrapPercent;
    }

    /**
     * Legt den Wert der scrapPercent-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setScrapPercent(BigDecimal value) {
        this.scrapPercent = value;
    }

    /**
     * Ruft den Wert der lotSize-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getLotSize() {
        return lotSize;
    }

    /**
     * Legt den Wert der lotSize-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setLotSize(BigDecimal value) {
        this.lotSize = value;
    }

    /**
     * Ruft den Wert der purchaseBlocked-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isPurchaseBlocked() {
        return purchaseBlocked;
    }

    /**
     * Legt den Wert der purchaseBlocked-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setPurchaseBlocked(Boolean value) {
        this.purchaseBlocked = value;
    }

    /**
     * Ruft den Wert der reorderingPolicy-Eigenschaft ab.
     *
     * @return possible object is
     * {@link ReorderingPolicy }
     */
    public ReorderingPolicy getReorderingPolicy() {
        return reorderingPolicy;
    }

    /**
     * Legt den Wert der reorderingPolicy-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link ReorderingPolicy }
     */
    public void setReorderingPolicy(ReorderingPolicy value) {
        this.reorderingPolicy = value;
    }

    /**
     * Ruft den Wert der includeInventory-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isIncludeInventory() {
        return includeInventory;
    }

    /**
     * Legt den Wert der includeInventory-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setIncludeInventory(Boolean value) {
        this.includeInventory = value;
    }

    /**
     * Ruft den Wert der reserve-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Reserve }
     */
    public Reserve getReserve() {
        return reserve;
    }

    /**
     * Legt den Wert der reserve-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Reserve }
     */
    public void setReserve(Reserve value) {
        this.reserve = value;
    }

    /**
     * Ruft den Wert der orderTrackingPolicy-Eigenschaft ab.
     *
     * @return possible object is
     * {@link OrderTrackingPolicy }
     */
    public OrderTrackingPolicy getOrderTrackingPolicy() {
        return orderTrackingPolicy;
    }

    /**
     * Legt den Wert der orderTrackingPolicy-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link OrderTrackingPolicy }
     */
    public void setOrderTrackingPolicy(OrderTrackingPolicy value) {
        this.orderTrackingPolicy = value;
    }

    /**
     * Ruft den Wert der stockkeepingUnitExists-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isStockkeepingUnitExists() {
        return stockkeepingUnitExists;
    }

    /**
     * Legt den Wert der stockkeepingUnitExists-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setStockkeepingUnitExists(Boolean value) {
        this.stockkeepingUnitExists = value;
    }

    /**
     * Ruft den Wert der critical-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isCritical() {
        return critical;
    }

    /**
     * Legt den Wert der critical-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setCritical(Boolean value) {
        this.critical = value;
    }

    /**
     * Ruft den Wert der reorderCycle-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getReorderCycle() {
        return reorderCycle;
    }

    /**
     * Legt den Wert der reorderCycle-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReorderCycle(String value) {
        this.reorderCycle = value;
    }

    /**
     * Ruft den Wert der safetyLeadTime-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSafetyLeadTime() {
        return safetyLeadTime;
    }

    /**
     * Legt den Wert der safetyLeadTime-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSafetyLeadTime(String value) {
        this.safetyLeadTime = value;
    }

    /**
     * Ruft den Wert der safetyStockQuantity-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getSafetyStockQuantity() {
        return safetyStockQuantity;
    }

    /**
     * Legt den Wert der safetyStockQuantity-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setSafetyStockQuantity(BigDecimal value) {
        this.safetyStockQuantity = value;
    }

    /**
     * Ruft den Wert der reorderPoint-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getReorderPoint() {
        return reorderPoint;
    }

    /**
     * Legt den Wert der reorderPoint-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setReorderPoint(BigDecimal value) {
        this.reorderPoint = value;
    }

    /**
     * Ruft den Wert der reorderQuantity-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getReorderQuantity() {
        return reorderQuantity;
    }

    /**
     * Legt den Wert der reorderQuantity-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setReorderQuantity(BigDecimal value) {
        this.reorderQuantity = value;
    }

    /**
     * Ruft den Wert der maximumInventory-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getMaximumInventory() {
        return maximumInventory;
    }

    /**
     * Legt den Wert der maximumInventory-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setMaximumInventory(BigDecimal value) {
        this.maximumInventory = value;
    }

    /**
     * Ruft den Wert der minimumOrderQuantity-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getMinimumOrderQuantity() {
        return minimumOrderQuantity;
    }

    /**
     * Legt den Wert der minimumOrderQuantity-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setMinimumOrderQuantity(BigDecimal value) {
        this.minimumOrderQuantity = value;
    }

    /**
     * Ruft den Wert der maximumOrderQuantity-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getMaximumOrderQuantity() {
        return maximumOrderQuantity;
    }

    /**
     * Legt den Wert der maximumOrderQuantity-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setMaximumOrderQuantity(BigDecimal value) {
        this.maximumOrderQuantity = value;
    }

    /**
     * Ruft den Wert der orderMultiple-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getOrderMultiple() {
        return orderMultiple;
    }

    /**
     * Legt den Wert der orderMultiple-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setOrderMultiple(BigDecimal value) {
        this.orderMultiple = value;
    }

    /**
     * Ruft den Wert der tariffNo-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTariffNo() {
        return tariffNo;
    }

    /**
     * Legt den Wert der tariffNo-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTariffNo(String value) {
        this.tariffNo = value;
    }

    /**
     * Ruft den Wert der countryRegionOfOriginCode-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCountryRegionOfOriginCode() {
        return countryRegionOfOriginCode;
    }

    /**
     * Legt den Wert der countryRegionOfOriginCode-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCountryRegionOfOriginCode(String value) {
        this.countryRegionOfOriginCode = value;
    }

    /**
     * Ruft den Wert der netWeight-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getNetWeight() {
        return netWeight;
    }

    /**
     * Legt den Wert der netWeight-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setNetWeight(BigDecimal value) {
        this.netWeight = value;
    }

    /**
     * Ruft den Wert der grossWeight-Eigenschaft ab.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    /**
     * Legt den Wert der grossWeight-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setGrossWeight(BigDecimal value) {
        this.grossWeight = value;
    }

    /**
     * Ruft den Wert der itemTrackingCode-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getItemTrackingCode() {
        return itemTrackingCode;
    }

    /**
     * Legt den Wert der itemTrackingCode-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemTrackingCode(String value) {
        this.itemTrackingCode = value;
    }

    /**
     * Ruft den Wert der serialNos-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSerialNos() {
        return serialNos;
    }

    /**
     * Legt den Wert der serialNos-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSerialNos(String value) {
        this.serialNos = value;
    }

    /**
     * Ruft den Wert der lotNos-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLotNos() {
        return lotNos;
    }

    /**
     * Legt den Wert der lotNos-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLotNos(String value) {
        this.lotNos = value;
    }

    /**
     * Ruft den Wert der expirationCalculation-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getExpirationCalculation() {
        return expirationCalculation;
    }

    /**
     * Legt den Wert der expirationCalculation-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setExpirationCalculation(String value) {
        this.expirationCalculation = value;
    }

    /**
     * Ruft den Wert der commonItemNo-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCommonItemNo() {
        return commonItemNo;
    }

    /**
     * Legt den Wert der commonItemNo-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCommonItemNo(String value) {
        this.commonItemNo = value;
    }

    /**
     * Ruft den Wert der specialEquipmentCode-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSpecialEquipmentCode() {
        return specialEquipmentCode;
    }

    /**
     * Legt den Wert der specialEquipmentCode-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSpecialEquipmentCode(String value) {
        this.specialEquipmentCode = value;
    }

    /**
     * Ruft den Wert der putAwayTemplateCode-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPutAwayTemplateCode() {
        return putAwayTemplateCode;
    }

    /**
     * Legt den Wert der putAwayTemplateCode-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPutAwayTemplateCode(String value) {
        this.putAwayTemplateCode = value;
    }

    /**
     * Ruft den Wert der putAwayUnitOfMeasureCode-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPutAwayUnitOfMeasureCode() {
        return putAwayUnitOfMeasureCode;
    }

    /**
     * Legt den Wert der putAwayUnitOfMeasureCode-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPutAwayUnitOfMeasureCode(String value) {
        this.putAwayUnitOfMeasureCode = value;
    }

    /**
     * Ruft den Wert der physInvtCountingPeriodCode-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPhysInvtCountingPeriodCode() {
        return physInvtCountingPeriodCode;
    }

    /**
     * Legt den Wert der physInvtCountingPeriodCode-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPhysInvtCountingPeriodCode(String value) {
        this.physInvtCountingPeriodCode = value;
    }

    /**
     * Ruft den Wert der lastPhysInvtDate-Eigenschaft ab.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getLastPhysInvtDate() {
        return lastPhysInvtDate;
    }

    /**
     * Legt den Wert der lastPhysInvtDate-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setLastPhysInvtDate(XMLGregorianCalendar value) {
        this.lastPhysInvtDate = value;
    }

    /**
     * Ruft den Wert der lastCountingPeriodUpdate-Eigenschaft ab.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getLastCountingPeriodUpdate() {
        return lastCountingPeriodUpdate;
    }

    /**
     * Legt den Wert der lastCountingPeriodUpdate-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setLastCountingPeriodUpdate(XMLGregorianCalendar value) {
        this.lastCountingPeriodUpdate = value;
    }

    /**
     * Ruft den Wert der nextCountingPeriod-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNextCountingPeriod() {
        return nextCountingPeriod;
    }

    /**
     * Legt den Wert der nextCountingPeriod-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNextCountingPeriod(String value) {
        this.nextCountingPeriod = value;
    }

    /**
     * Ruft den Wert der identifierCode-Eigenschaft ab.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIdentifierCode() {
        return identifierCode;
    }

    /**
     * Legt den Wert der identifierCode-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIdentifierCode(String value) {
        this.identifierCode = value;
    }

    /**
     * Ruft den Wert der useCrossDocking-Eigenschaft ab.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isUseCrossDocking() {
        return useCrossDocking;
    }

    /**
     * Legt den Wert der useCrossDocking-Eigenschaft fest.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setUseCrossDocking(Boolean value) {
        this.useCrossDocking = value;
    }

}
