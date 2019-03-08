package ch.hslu.edu.enapp.webshop.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class Purchase {

    private final int id;
    private final Customer customer;
    private final Timestamp datetime;
    private final BigDecimal totalCost;
    private final String payId;
    private final String correlationId;
    private final String salesOrderNo;
    private final String state;
    private final List<PurchaseItem> purchaseItems;

    public Purchase(final int id, final Customer customer, final Timestamp datetime, final BigDecimal totalCost, final String payId,
                    final String correlationId, final String salesOrderNo, final String state) {
        this.id = id;
        this.customer = customer;
        this.datetime = datetime;
        this.totalCost = totalCost;
        this.payId = payId;
        this.correlationId = correlationId;
        this.salesOrderNo = salesOrderNo;
        this.state = state;
        this.purchaseItems = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Timestamp getDatetime() {
        return this.datetime;
    }

    public BigDecimal getTotalCost() {
        return this.totalCost;
    }

    public String getPayId() {
        return this.payId;
    }

    public String getCorrelationId() {
        return this.correlationId;
    }

    public String getSalesOrderNo() {
        return this.salesOrderNo;
    }

    public String getState() {
        return this.state;
    }

    public List<PurchaseItem> getPurchaseItems() {
        return this.purchaseItems;
    }
}
