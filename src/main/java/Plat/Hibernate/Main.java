package Plat.Hibernate;

import Plat.Hibernate.Entities.*;
import Plat.Hibernate.Util.DataBaseManager;
import Plat.Hibernate.Util.DataBaseObject;
import Plat.Hibernate.Util.HibernateUtil;
import Plat.Hibernate.Util.RuleObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.HibernateException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MontaserQasem on 11/29/16.
 */
public class Main {
    public static void main(String args[]) throws IOException {
//  DO NOT DELETE ANYTHING IN THIS CLASS

        Store store = getStore();
        Address address = getAddress();
        store.getAddresses().add(address);
        address.setStore(store);
        Admins admin = getAdmin();
        admin.setStore(store);
        store.getAdmins().add(admin);
        Privileges privilege = getPrivilege();
        privilege.getAdmins().add(admin);
        admin.setPrivilege(privilege);
        Categories category = getCategory();
        category.setStore(store);
        store.getCategories().add(category);
        Brand brand = getBrand();
        brand.getCategories().add(category);
        category.getBrands().add(brand);
        Items item = getItem();
        item.setBrand(brand);
        brand.getItems().add(item);
        Guests guest = getGuest();
        ItemHits itemHits = getItemHit();
        itemHits.setItem(item);
        item.getItemHitses().add(itemHits);
        guest.getItemsHits().add(itemHits);
        itemHits.setGuest(guest);
        Photos photo = getPhoto();
        photo.setItem(item);
        item.getPhotos().add(photo);
        item.setCategory(category);
        category.getItems().add(item);
        WishList wishList = getWishList();
        wishList.setItem(item);
        item.getWishLists().add(wishList);
        Users user = getUser();
        user.getWishLists().add(wishList);
        wishList.setUser(user);
        Cart cart = getCart();
        cart.setItem(item);
        cart.setUser(user);
        user.getCart().add(cart);
        OrderItem orderItem = getOrderItem();
        orderItem.setItem(item);
        item.getOrderedItems().add(orderItem);
        Orders order = getOrder();
        order.getOrderItems().add(orderItem);
        orderItem.setOrder(order);
        user.getOrders().add(order);
        order.setUser(user);
        Log log = getLog();
        log.setOrder(order);
        order.setLog(log);
        Specifications specification = getSpecification();
        specification.setItem(item);
        item.getSpecifications().add(specification);


        ObjectMapper mapper = new ObjectMapper();
        String JSON = mapper.writeValueAsString(item);
        //   System.out.println(JSON);

        DataBaseManager manager = DataBaseManager.getInstance();
        List<DataBaseObject> object = manager.find(null, Store.class);
        Store plat = (Store) object.get(0);
        plat = (Store) manager.initialize(plat, "admins");
        System.out.println(plat.getAdmins().size());
    }

    public static Address getAddress() {
        Address address = new Address();
        address.setId(1);
        address.setDescription("This is a testing address");
        address.setLatitude("12.22");
        address.setLongitude("22.22");
        return address;
    }

    public static Admins getAdmin() {
        Admins admin = new Admins();
        admin.setId(1);
        admin.setContactNumber("00962786875133");
        admin.setEmail("montaserrqasem@gmail.com");
        admin.setFirstName("Montaser");
        admin.setLastName("Qasem");
        admin.setPassword("test");
        admin.setUsername("montaserqasem");
        return admin;
    }

    public static Brand getBrand() {
        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("Apple");
        return brand;
    }

    public static Cart getCart() {
        Cart cart = new Cart();
        cart.setId(1);
        cart.setQuantity(2);
        return cart;
    }

    public static Categories getCategory() {
        Categories category = new Categories();
        category.setId(1);
        category.setName("Laptops");
        return category;
    }

    public static Guests getGuest() {
        Guests guests = new Guests();
        guests.setId(1);
        return guests;
    }

    public static ItemHits getItemHit() {
        ItemHits itemHit = new ItemHits();
        itemHit.setId(1);
        itemHit.setDate("23123123");
        return itemHit;
    }

    public static Items getItem() {
        Items item = new Items();
        item.setId(1);
        item.setName("MacBook pro 2015");
        item.setDiscount(0);
        item.setPrice(600);
        item.setDescription("A Good laptop description");
        item.setQuantity(12);
        return item;
    }

    public static Log getLog() {
        Log log = new Log();
        log.setId(1);
        log.setAdminName("Montaser");
        log.setDeliveryData(123213);
        return log;
    }

    public static OrderItem getOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setQuantity(21);
        orderItem.setDiscount(0.0);
        orderItem.setPrice(12);
        return orderItem;
    }

    public static Orders getOrder() {
        Orders order = new Orders();
        order.setId(1);
        order.setDeliveryDate("123123");
        order.setDescription("Make it fast");
        order.setStatus(2);
        order.setAdditionalNote("no notes");
        order.setPayment(1);
        order.setTotal(123.12);
        return order;
    }

    public static Photos getPhoto() {
        Photos photo = new Photos();
        photo.setId(1);
        photo.setPath("/123213/123");
        photo.setPrimary(true);
        return photo;
    }

    public static Privileges getPrivilege() {
        Privileges privileges = new Privileges();
        privileges.setId(1);
        privileges.setName("Root");
        return privileges;
    }

    public static Specifications getSpecification() {
        Specifications specification = new Specifications();
        specification.setId(1);
        specification.setSpecificationKey("CPU-GEN");
        specification.setSpecificationValue("6th Generation");
        return specification;
    }

    public static Store getStore() {
        Store store = new Store();
        store.setId(1);
        store.setName("Platinum");
        store.setOwner("Ahmad");
        store.setSubdomain("mall.platinum.com");
        return store;
    }

    public static Users getUser() {
        Users user = new Users();
        user.setId(1);
        user.setActivate(true);
        user.setAddress1("add1");
        user.setAddress2("add2");
        user.setCellPhone("0098277128");
        user.setCity("Zarqa");
        user.setDate("123123");
        user.setEmail("montaser.tutu@gmail.com");
        user.setFirstName("gogback");
        user.setLastName("winston");
        user.setSubscribe(true);
        user.setPassword("test2");
        return user;
    }

    public static WishList getWishList() {
        WishList wishList = new WishList();
        wishList.setId(1);
        return wishList;
    }

}
