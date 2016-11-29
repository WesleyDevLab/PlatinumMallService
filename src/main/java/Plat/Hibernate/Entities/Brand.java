package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by MontaserQasem on 11/12/16.
 */

@Entity(name = "brand")
public class Brand implements DataBaseObject {
    private int id;
    private String name;
    private Set<Items> items = null;
    private Set<Categories> categories=null;

    public Brand(){
        items  = new HashSet<Items>();
        categories = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @OneToMany(mappedBy = "brand",cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    public Set<Items> getItems() {
        return items;
    }

    public void setItems(Set<Items> items) {
        this.items = items;
    }

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    public Set<Categories> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categories> categories) {
        this.categories = categories;
    }
}
