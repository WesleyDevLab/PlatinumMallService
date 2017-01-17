package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MontaserQasem on 11/12/16.
 */
@Entity(name = "users")
@JsonIgnoreProperties(value = {"cart", "orders", "wishlist","handler", "hibernateLazyInitializer"})
public class Users implements DataBaseObject {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String cellPhone;
    private String address1;
    private String address2;
    private String city;
    private boolean subscribe;
    private String password;
    private boolean activate;
    private String date;
    private List<Cart> cart = null;
    private List<Orders> orders = null;
    private List<WishList> wishLists = null;

    public Users() {
        cart = new ArrayList<>();
        orders = new ArrayList<Orders>();
        wishLists = new ArrayList<WishList>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "first_name", length = 45, nullable = false)
    @Basic
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", length = 45, nullable = false)
    @Basic
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email", length = 30, nullable = false)
    @Basic
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Column(name = "cellphone", length = 20, nullable = false)
    @Basic
    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    @Column(name = "address1", length = 30, nullable = true)
    @Basic
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(name = "address2", length = 45, nullable = true)
    @Basic
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Column(name = "city", nullable = false)
    @Basic
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "subscribe", nullable = true)
    @Basic
    public boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    @Column(name = "password", length = 500, nullable = false)
    @Basic
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "activate", nullable = true)
    @Basic
    public boolean getActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    @Column(name = "date", length = 100, nullable = false)
    @Basic
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JsonIgnore
    public List<WishList> getWishLists() {
        return wishLists;
    }

    public void setWishLists(List<WishList> wishLists) {
        this.wishLists = wishLists;
    }
}
