package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by MontaserQasem on 11/12/16.
 */

@Entity(name = "items")
public class Items implements DataBaseObject {
    private int id;
    private String name;
    private double price;
    private double discount;
    private int quantity;
    private String description;
    private Categories category;
    private Brand brand=null;
    private Set<Cart> cart=null;
    private Set<Photos> photos = null;
    private Set<OrderItem> orderedItems = null;
    private Set<WishList> wishLists = null;
    private Set<ItemHits> itemHitses = null;
    private Set<Specifications> specifications = null;

    public Items() {
        cart = new HashSet<>();
        photos = new HashSet<Photos>();
        orderedItems = new HashSet<OrderItem>();
        wishLists = new HashSet<WishList>();
        itemHitses = new HashSet<ItemHits>();
        specifications = new HashSet<Specifications>();
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


    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    public Set<Photos> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photos> photos) {
        this.photos = photos;
    }

    @OneToMany(mappedBy = "item",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    public Set<Cart> getCart() {
        return cart;
    }

    public void setCart(Set<Cart> cart) {
        this.cart = cart;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    public Set<OrderItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(Set<OrderItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    public Set<WishList> getWishLists() {
        return wishLists;
    }

    public void setWishLists(Set<WishList> wishLists) {
        this.wishLists = wishLists;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    public Set<ItemHits> getItemHitses() {
        return itemHitses;
    }

    public void setItemHitses(Set<ItemHits> itemHitses) {
        this.itemHitses = itemHitses;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    public Set<Specifications> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Set<Specifications> specifications) {
        this.specifications = specifications;
    }

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)return true;
        return false;
    }
}
