package ch.hslu.edu.enapp.webshop.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "getProducts", query = "SELECT p FROM ProductEntity p ORDER BY p.name"),
        @NamedQuery(name = "getProductById", query = "SELECT p FROM ProductEntity p WHERE p.id = :id"),
        @NamedQuery(name = "getProductByItemNo", query = "SELECT p FROM ProductEntity p WHERE p.itemNo = :itemNo")
})
@Table(name = "product", schema = "webshop", catalog = "")
public class ProductEntity {
    private int id;
    private String itemNo;
    private String name;
    private String description;
    private String origdescription;
    private String mediapath;
    private BigDecimal unitprice;

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
    @Column(name = "itemno", nullable = false, length = 255)
    public String getItemNo() {
        return this.itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "origdescription", nullable = false, length = 255)
    public String getOriginalDescription() {
        return origdescription;
    }

    public void setOriginalDescription(String originalDescription) {
        this.origdescription = originalDescription;
    }

    @Basic
    @Column(name = "mediapath", nullable = false, length = 255)
    public String getMediapath() {
        return mediapath;
    }

    public void setMediapath(String mediapath) {
        this.mediapath = mediapath;
    }

    @Basic
    @Column(name = "unitprice", nullable = false, precision = 2)
    public BigDecimal getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return id == that.id &&
                Objects.equals(itemNo, that.itemNo) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(origdescription, that.origdescription) &&
                Objects.equals(mediapath, that.mediapath) &&
                Objects.equals(unitprice, that.unitprice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemNo, name, description, origdescription, mediapath, unitprice);
    }
}
