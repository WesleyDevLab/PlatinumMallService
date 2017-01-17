package Plat.Hibernate.Util;

import Plat.Hibernate.Entities.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MontaserQasem on 1/15/17.
 */
public class EntityInitializer {
    private static DataBaseManager manager = DataBaseManager.getInstance();
    private static List<DataBaseObject> data;

    public static List<DataBaseObject> init(List<DataBaseObject> object, Class cls) {
        data = object;
        if (cls == Address.class)
            return object;

        if (cls == Admins.class)
            return initAdmin();

        if (cls == Brand.class)
            return object;

        if (cls == Cart.class)
            return initCart();

        if (cls == Categories.class)
            return object;

        if (cls == Guests.class)
            return initGuest();

        if (cls == ItemHits.class)
            return initItemHit();

        if (cls == Items.class)
            return initItem();

        if (cls == Log.class)
            return initLog();

        if (cls == OrderItem.class)
            return initOrderItem();

        if (cls == Orders.class)
            return initOrder();

        if (cls == Photos.class)
            return object;

        if (cls == Privileges.class)
            return object;

        if (cls == Specifications.class)
            return object;

        if (cls == Store.class)
            return initStore();

        if (cls == Users.class)
            return object;

        if (cls == WishList.class)
            return initWishList();

        return null;
    }

    private static List<DataBaseObject> initAdmin() {
        List<DataBaseObject> information = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Admins admin = (Admins) data.get(i);
            admin = (Admins) manager.initialize(admin, "privilege");
            information.add(admin);
        }
        return information;
    }

    private static List<DataBaseObject> initCart() {
        List<DataBaseObject> information = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Cart cart = (Cart) data.get(i);
            cart = (Cart) manager.initialize(cart, "item");
            cart = (Cart) manager.initialize(cart, "user");
            information.add(cart);
        }
        return information;
    }

    private static List<DataBaseObject> initGuest() {
        List<DataBaseObject> information = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Guests guest = (Guests) data.get(i);
            guest = (Guests) manager.initialize(guest, "itemsHits");
            information.add(guest);
        }
        return information;
    }

    private static List<DataBaseObject> initItemHit() {
        List<DataBaseObject> information = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            ItemHits itemHit = (ItemHits) data.get(i);
            itemHit = (ItemHits) manager.initialize(itemHit, "item");
            information.add(itemHit);
        }
        return information;
    }

    private static List<DataBaseObject> initItem() {
        List<DataBaseObject> information = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Items item = (Items) data.get(i);
            item = (Items) manager.initialize(item, "brand");
            item = (Items) manager.initialize(item, "photos");
            item = (Items) manager.initialize(item, "specifications");
            information.add(item);
        }
        return information;
    }

    private static List<DataBaseObject> initLog() {
        List<DataBaseObject> information = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Log log = (Log) data.get(i);
            log = (Log) manager.initialize(log, "order");
            information.add(log);
        }
        return information;
    }

    private static List<DataBaseObject> initOrderItem() {
        List<DataBaseObject> information = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            OrderItem orderItem = (OrderItem) data.get(i);
            orderItem = (OrderItem) manager.initialize(orderItem, "item");
            information.add(orderItem);
        }
        return information;
    }

    private static List<DataBaseObject> initOrder() {
        List<DataBaseObject> information = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Orders order = (Orders) data.get(i);
            order = (Orders) manager.initialize(order, "user");
            order = (Orders) manager.initialize(order, "orderItems");
        }
        return information;
    }

    private static List<DataBaseObject> initStore() {
        List<DataBaseObject> information = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Store store = (Store) data.get(i);
            store = (Store) manager.initialize(store, "addresses");
            information.add(store);
        }
        return information;
    }

    private static List<DataBaseObject> initWishList() {
        List<DataBaseObject> information = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            WishList wishList = (WishList) data.get(i);
            wishList = (WishList) manager.initialize(wishList, "user");
            wishList = (WishList) manager.initialize(wishList, "item");
            information.add(wishList);
        }
        return information;
    }
}
