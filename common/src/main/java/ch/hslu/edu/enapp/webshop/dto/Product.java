package ch.hslu.edu.enapp.webshop.dto;

import java.math.BigDecimal;

public final class Product {

    private final int id;
    private final String itemNo;
    private final String name;
    private final String description;
    private final String origdescription;
    private final String mediapath;
    private final BigDecimal unitprice;

    public Product(final int id, final String itemNo, final String name, final String description, final String originalDescription,
                   final String mediapath, final BigDecimal unitprice) {
        this.id = id;
        this.itemNo = itemNo;
        this.name = name;
        this.description = description;
        this.origdescription = originalDescription;
        this.mediapath = mediapath;
        this.unitprice = unitprice;
    }

    public int getId() {
        return this.id;
    }

    public String getItemNo() {
        return this.itemNo;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getOriginalDescription() {
        return this.origdescription;
    }

    public String getMediapath() {
        return this.mediapath;
    }

    public BigDecimal getUnitprice() {
        return this.unitprice;
    }
}
