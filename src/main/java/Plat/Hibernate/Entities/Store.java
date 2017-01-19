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
@Entity(name = "store")
@JsonIgnoreProperties(value = {"categories","admins","handler", "hibernateLazyInitializer"})
public class Store implements DataBaseObject {
    private int id;
    private String name;
    private String owner;
    private String subdomain;
    private List<Categories> categories = null;
    private List<Admins> admins = null;
    private List<Address> addresses = null;


    public Store() {
        categories = new ArrayList<>();
        admins = new ArrayList<Admins>();
        addresses = new ArrayList<Address>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "owner", nullable = false)
    @Basic
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "subdomain", nullable = false)
    @Basic
    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    @OneToMany(mappedBy = "store", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    @OneToMany(mappedBy = "store",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    public List<Admins> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admins> admins) {
        this.admins = admins;
    }

    @OneToMany(mappedBy = "store", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
