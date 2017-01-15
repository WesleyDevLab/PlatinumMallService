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

@Entity(name = "items")
@JsonIgnoreProperties(value = {"categories", "cart", "orderitem"})
public class Items implements DataBaseObject {
    private int id;
    private String name;
    private double price;
    private double discount;
    private int quantity;
    private String description;
    private Categories category;
    private Brand brand = null;
    private List<Cart> cart = null;
    private List<Photos> photos = null;
    private List<OrderItem> orderedItems = null;
    private List<WishList> wishLists = null;
    private List<ItemHits> itemHits = null;
    private List<Specifications> specifications = null;

    public Items() {
        cart = new ArrayList<>();
        photos = new ArrayList<Photos>();
        orderedItems = new ArrayList<OrderItem>();
        wishLists = new ArrayList<WishList>();
        itemHits = new ArrayList<ItemHits>();
        specifications = new ArrayList<Specifications>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 120)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price", nullable = false)
    @Basic
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "discount", nullable = false)
    @Basic
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Column(name = "quantity", nullable = false)
    @Basic
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "description", length = 300, nullable = false)
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @ManyToOne(cascade = CascadeType.PERSIST ,fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    public List<Photos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photos> photos) {
        this.photos = photos;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JsonIgnore
    public List<OrderItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<OrderItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JsonIgnore
    public List<WishList> getWishLists() {
        return wishLists;
    }

    public void setWishLists(List<WishList> wishLists) {
        this.wishLists = wishLists;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JsonIgnore
    public List<ItemHits> getItemHitses() {
        return itemHits;
    }

    public void setItemHitses(List<ItemHits> itemHitses) {
        this.itemHits = itemHitses;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    public List<Specifications> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<Specifications> specifications) {
        this.specifications = specifications;
    }


    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

}
