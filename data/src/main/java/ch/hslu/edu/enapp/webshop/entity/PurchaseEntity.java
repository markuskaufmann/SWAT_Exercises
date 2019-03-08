package ch.hslu.edu.enapp.webshop.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "getPurchases", query = "SELECT p FROM PurchaseEntity p ORDER BY p.id"),
        @NamedQuery(name = "getPurchasesByCustomer", query = "SELECT p FROM PurchaseEntity p WHERE p.customer = :name ORDER BY p.datetime DESC"),
        @NamedQuery(name = "getPurchasesByState", query = "SELECT p FROM PurchaseEntity p WHERE p.state = :state"),
        @NamedQuery(name = "getPurchasesInTimespan", query = "SELECT p FROM PurchaseEntity p WHERE p.datetime BETWEEN :dateStart AND :dateEnd")
})
@Table(name = "purchase", schema = "webshop", catalog = "")
public class PurchaseEntity {
    private int id;
    private String customer;
    private Timestamp datetime;
    private BigDecimal totalcost;
    private String payid;
    private String correlationid;
    private String salesorderno;
    private String state;
    private CustomerEntity customerByCustomer;
    private Collection<PurchaseitemEntity> purchaseitemsById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "customer", nullable = false, length = 255)
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Basic
    @Column(name = "datetime", nullable = false)
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Basic
    @Column(name = "totalcost", nullable = false, precision = 2)
    public BigDecimal getTotalCost() {
        return totalcost;
    }

    public void setTotalCost(BigDecimal totalcost) {
        this.totalcost = totalcost;
    }

    @Basic
    @Column(name = "correlationid", length = 255)
    public String getCorrelationId() {
        return correlationid;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationid = correlationId;
    }

    @Basic
    @Column(name = "payid", length = 255)
    public String getPayId() {
        return payid;
    }

    public void setPayId(String payId) {
        this.payid = payId;
    }

    @Basic
    @Column(name = "salesorderno", length = 255)
    public String getSalesOrderNo() {
        return salesorderno;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesorderno = salesOrderNo;
    }

    @Basic
    @Column(name = "state", length = 255)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseEntity that = (PurchaseEntity) o;
        return id == that.id &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(datetime, that.datetime) &&
                Objects.equals(totalcost, that.totalcost) &&
                Objects.equals(payid, that.payid) &&
                Objects.equals(correlationid, that.correlationid) &&
                Objects.equals(salesorderno, that.salesorderno) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, datetime, totalcost, payid, correlationid, salesorderno, state);
    }

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "customer", referencedColumnName = "name")
    public CustomerEntity getCustomerByCustomer() {
        return customerByCustomer;
    }

    public void setCustomerByCustomer(CustomerEntity customerByCustomer) {
        this.customerByCustomer = customerByCustomer;
    }

    @OneToMany(mappedBy = "purchaseByPurchase", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    public Collection<PurchaseitemEntity> getPurchaseitemsById() {
        return purchaseitemsById;
    }

    public void setPurchaseitemsById(Collection<PurchaseitemEntity> purchaseitemsById) {
        this.purchaseitemsById = purchaseitemsById;
    }
}
