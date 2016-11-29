package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by MontaserQasem on 11/12/16.
 */
@Entity(name = "store")
public class Store implements DataBaseObject {
    private int id;
    private String name;
    private String owner;
    private String subdomain;
    private Set<Categories> categories =null;
    private Set<Admins> admins = null;
    private Set<Address> addresses=null;


    public Store() {
        categories = new HashSet<Categories>();
        admins = new HashSet<Admins>();
        addresses = new HashSet<Address>();
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


    @OneToMany(mappedBy = "store",cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    public Set<Categories> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categories> categories) {
        this.categories = categories;
    }

    @OneToMany(mappedBy = "store",cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    public Set<Admins> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<Admins> admins) {
        this.admins = admins;
    }


    @OneToMany(mappedBy = "store",cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
}
