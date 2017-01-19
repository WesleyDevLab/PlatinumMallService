package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MontaserQasem on 11/12/16.
 */
@Entity(name = "categories")
@JsonIgnoreProperties(value = {"brand", "items", "store", "handler", "hibernateLazyInitializer"})
public class Categories implements DataBaseObject {
    private int id;
    private String name;
    private String arabicName;
    private Store store;
    private List<Brand> brands = null;
    private List<Items> items = null;

    public Categories() {
        brands = new ArrayList<>();
        items = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", length = 50, nullable = false)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "arabic_name", length = 200)
    @Basic
    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categories)) return false;

        Categories that = (Categories) o;

        if (getId() != that.getId()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getArabicName() != null ? !getArabicName().equals(that.getArabicName()) : that.getArabicName() != null)
            return false;
        if (getStore() != null ? !getStore().equals(that.getStore()) : that.getStore() != null) return false;
        if (getBrands() != null ? !getBrands().equals(that.getBrands()) : that.getBrands() != null) return false;
        return getItems() != null ? getItems().equals(that.getItems()) : that.getItems() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getArabicName() != null ? getArabicName().hashCode() : 0);
        result = 31 * result + (getStore() != null ? getStore().hashCode() : 0);
        result = 31 * result + (getBrands() != null ? getBrands().hashCode() : 0);
        result = 31 * result + (getItems() != null ? getItems().hashCode() : 0);
        return result;
    }
}
