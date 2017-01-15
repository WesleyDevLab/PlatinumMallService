package Plat.Hibernate.Util;

import Plat.Hibernate.Entities.*;


/**
 * Created by MontaserQasem on 1/15/17.
 */
public class EntityInitializer {
    private static DataBaseManager manager = DataBaseManager.getInstance();

    public static DataBaseObject init(DataBaseObject object, Class cls) {
        if (cls == Address.class)
            return object;

        if (cls == Admins.class)
            return initAdmin((Admins) object);

        if (cls == Brand.class)
            return object;

        if (cls == Cart.class)
            return initCart((Cart) object);

        if (cls == Categories.class)
            return object;

        if (cls == Guests.class)
            return initGuest((Guests) object);

        if (cls == ItemHits.class)
            return initItemHit((ItemHits) object);

        if (cls == Items.class)
            return initItem((Items) object);

        if (cls == Log.class)
            return initLog((Log) object);

        if (cls == OrderItem.class)
            return initOrderItem((OrderItem) object);

        if (cls == Orders.class)
            return initOrder((Orders) object);

        if (cls == Photos.class)
            return object;

        if (cls == Privileges.class)
            return object;

        if (cls == Specifications.class)
            return object;

        if (cls == Store.class)
            return initStore((Store) object);

        if (cls == Users.class)
            return object;

        if (cls == WishList.class)
            return initWishList((WishList) object);

        return null;
    }

    public static Admins initAdmin(Admins admin) {
        admin = (Admins) manager.initialize(admin, "privilege");
        return admin;
    }

    public static Cart initCart(Cart cart) {
        cart = (Cart) manager.initialize(cart, "item");
        cart = (Cart) manager.initialize(cart, "user");
        return cart;
    }

    public static Guests initGuest(Guests guest) {
        guest = (Guests) manager.initialize(guest, "itemsHits");
        return guest;
    }

    public static ItemHits initItemHit(ItemHits itemHit) {
        itemHit = (ItemHits) manager.initialize(itemHit, "item");
        return itemHit;
    }

    public static Items initItem(Items item) {
        item = (Items) manager.initialize(item, "brand");
        item = (Items) manager.initialize(item, "photos");
        item = (Items) manager.initialize(item, "specifications");
        return item;
    }

    public static Log initLog(Log log) {
        log = (Log) manager.initialize(log, "order");
        return log;
    }

    public static OrderItem initOrderItem(OrderItem orderItem) {
        orderItem = (OrderItem) manager.initialize(orderItem, "item");
        return orderItem;
    }

    public static Orders initOrder(Orders order) {
        order = (Orders) manager.initialize(order, "user");
        order = (Orders) manager.initialize(order, "orderItems");
        return order;
    }

    public static Store initStore(Store store) {
        store = (Store) manager.initialize(store, "addresses");
        return store;
    }

    public static WishList initWishList(WishList wishList) {
        wishList = (WishList) manager.initialize(wishList, "user");
        wishList = (WishList) manager.initialize(wishList, "item");
        return wishList;
    }
}
