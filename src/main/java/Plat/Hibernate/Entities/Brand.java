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

@Entity(name = "brand")
@JsonIgnoreProperties(value = {"items", "categories","handler", "hibernateLazyInitializer"})
public class Brand implements DataBaseObject {
    private int id;
    private String name;
    private String arabicName;
    private List<Items> items = null;
    private List<Categories> categories = null;

    public Brand() {
        items = new ArrayList<>();
        categories = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "arabic_name", length = 200)
    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    @OneToMany(mappedBy = "brand", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brand)) return false;

        Brand brand = (Brand) o;

        if (id != brand.id) return false;
        if (name != null ? !name.equals(brand.name) : brand.name != null) return false;
        if (arabicName != null ? !arabicName.equals(brand.arabicName) : brand.arabicName != null) return false;
        if (items != null ? !items.equals(brand.items) : brand.items != null) return false;
        return categories != null ? categories.equals(brand.categories) : brand.categories == null;

    }

    @Override
    public int hashCode() {
        return id;
    }

}
