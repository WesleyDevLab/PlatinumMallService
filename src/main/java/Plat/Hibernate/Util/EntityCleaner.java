package Plat.Hibernate.Util;


import Plat.Hibernate.Entities.*;

import java.util.*;

/**
 * Created by MontaserQasem on 11/19/16.
 */
public class EntityCleaner {

    public EntityCleaner() {
    }

    public static List<DataBaseObject> clean(List<DataBaseObject> objects, Class cls) {
        List<DataBaseObject> result = new ArrayList<>();
        if (cls == Address.class) {
            for (int i = 0; i < objects.size(); i++) {
                Address address = (Address) objects.get(i);
                result.add(cleanAddress(address));
            }
        }
        if (cls == Admins.class) {
            for (int i = 0; i < objects.size(); i++) {
                Admins admin = (Admins) objects.get(i);
                result.add(cleanAdmin(admin));
            }
        }
        if (cls == Brand.class) {
            for (int i = 0; i < objects.size(); i++) {
                Brand brand = (Brand) objects.get(i);
                result.add(cleanBrand(brand));
            }
        }

        if (cls == Cart.class) {
            for (int i = 0; i < objects.size(); i++) {
                Cart cart = (Cart) objects.get(i);
                result.add(cleanCart(cart));
            }
        }
        if (cls == Categories.class) {
            for (int i = 0; i < objects.size(); i++) {
                Categories category = (Categories) objects.get(i);
                result.add(cleanCategory(category));
            }
        }
        if (cls == Guests.class) {
            for (int i = 0; i < objects.size(); i++) {
                Guests guest = (Guests) objects.get(i);
                result.add(cleanGuest(guest));
            }
        }
        if (cls == ItemHits.class) {
            for (int i = 0; i < objects.size(); i++) {
                ItemHits itemHit = (ItemHits) objects.get(i);
                result.add(cleanItemHits(itemHit));
            }
        }
        if (cls == Items.class) {
            for (int i = 0; i < objects.size(); i++) {
                Items item = (Items) objects.get(i);
                result.add(cleanItem(item));
            }
        }
        if (cls == OrderItem.class) {
            for (int i = 0; i < objects.size(); i++) {
                OrderItem orderItem = (OrderItem) objects.get(i);
                result.add(cleanOrderItem(orderItem));
            }
        }
        if (cls == Orders.class) {
            for (int i = 0; i < objects.size(); i++) {
                Orders order = (Orders) objects.get(i);
                result.add(cleanOrder(order));
            }
        }
        if (cls == Photos.class) {
            for (int i = 0; i < objects.size(); i++) {
                Photos photo = (Photos) objects.get(i);
                result.add(cleanPhoto(photo));
            }
        }
        if (cls == Privileges.class) {
            for (int i = 0; i < objects.size(); i++) {
                Privileges privilege = (Privileges) objects.get(i);
                result.add(cleanPrivilege(privilege));
            }
        }
        if (cls == Specifications.class) {
            for (int i = 0; i < objects.size(); i++) {
                Specifications specification = (Specifications) objects.get(i);
                result.add(cleanSpecification(specification));
            }
        }
        if (cls == Store.class) {
            for (int i = 0; i < objects.size(); i++) {
                Store store = (Store) objects.get(i);
                result.add(cleanStore(store));
            }
            return result;
        }

        if (cls == Users.class) {
            for (int i = 0; i < objects.size(); i++) {
                Users user = (Users) objects.get(i);
                result.add(cleanUser(user));
            }
        }

        if (cls == WishList.class) {
            for (int i = 0; i < objects.size(); i++) {
                WishList wishList = (WishList) objects.get(i);
                result.add(cleanWishList(wishList));
            }
        }

        return result;
    }

    //-Entities Clean Methods Section

    private static Store cleanStore(Store store) {
        Iterator it = store.getAddresses().iterator();
        if (it != null) {
            Set<Address> newAdd = new HashSet<>();
            while (it.hasNext()) {
                Address node = (Address) it.next();
                node.setStore(null);
                newAdd.add(node);
            }
            store.setAddresses(newAdd);
        }
        it = store.getCategories().iterator();
        if (it != null) {
            Set<Categories> newCat = new HashSet<>();
            while (it.hasNext()) {
                Categories category = (Categories) it.next();
                category.setStore(null);
                category.setBrands(null);
                Iterator itemIt = category.getItems().iterator();
                Set<Items> newItems = new HashSet<>();
                while (itemIt.hasNext()) {
                    Items item = (Items) itemIt.next();
                    item.setCategory(null);
                    item.setPhotos(null);
                    item.setOrderedItems(null);
                    item.setBrand(null);
                    item.setSpecifications(null);
                    item.setCart(null);
                    item.setWishLists(null);
                    item.setItemHitses(null);
                    newItems.add(item);
                }
                category.setItems(newItems);
                newCat.add(category);
            }
            store.setCategories(newCat);
        }
        it = store.getAdmins().iterator();
        if (it != null) {
            Set<Admins> newAdmins = new HashSet<>();
            while (it.hasNext()) {
                Admins node = (Admins) it.next();
                node.setPrivilege(null);
                node.setStore(null);
                newAdmins.add(node);
            }
            store.setAdmins(newAdmins);
        }
        return store;
    }

    private static Address cleanAddress(Address address) {
        Store store = (Store) address.getStore();
        if (store != null) {
            store.setAdmins(null);
            store.setCategories(null);
            store.setAddresses(null);
            address.setStore(store);
        }
        return address;
    }

    private static Admins cleanAdmin(Admins admin) {
        Privileges prv = (Privileges) admin.getPrivilege();
        if (prv != null) {
            prv.setAdmins(null);
            admin.setPrivilege(prv);
        }
        Store store = (Store) admin.getStore();
        if (store != null) {
            store.setAddresses(null);
            store.setAdmins(null);
            store.setCategories(null);
            admin.setStore(store);
        }
        return admin;
    }

    private static Brand cleanBrand(Brand brand) {
        Iterator it =null;
        if(brand.getCategories()!=null) {
             it = brand.getCategories().iterator();
            if (it != null) {
                Set<Categories> newCat = new HashSet<>();
                while (it.hasNext()) {
                    Categories category = (Categories) it.next();
                    category.setStore(null);
                    category.setBrands(null);
                    category.setItems(null);
                    newCat.add(category);
                }
                brand.setCategories(newCat);
            }
        }
        if(brand.getItems()!=null) {
            it = brand.getItems().iterator();
            if (it != null) {
                Set<Items> newItems = new HashSet<>();
                while (it.hasNext()) {
                    Items node = (Items) it.next();
                    node.setBrand(null);
                    node.setPhotos(null);
                    node.setOrderedItems(null);
                    node.setSpecifications(null);
                    node.setItemHitses(null);
                    node.setWishLists(null);
                    node.setCart(null);
                    node.setCategory(null);
                    newItems.add(node);
                }
                brand.setItems(newItems);
            }
        }
        return brand;
    }

    private static Categories cleanCategory(Categories category) {
        Store store = (Store) category.getStore();
        if (store != null) {
            store.setAddresses(null);
            store.setAdmins(null);
            store.setCategories(null);
            category.setStore(store);
        }
        Iterator it = category.getBrands().iterator();
        if (it != null) {
            Set<Brand> newBrands = new HashSet<>();
            while (it.hasNext()) {
                Brand node = (Brand) it.next();
                node.setItems(null);
                node.setCategories(null);
                newBrands.add(node);
            }
            category.setBrands(newBrands);
        }
        it = category.getItems().iterator();
        if (it != null) {
            Set<Items> newItems = new HashSet<>();
            while (it.hasNext()) {
                Items node = (Items) it.next();
                node.setBrand(null);
                node.setPhotos(null);
                node.setOrderedItems(null);
                node.setSpecifications(null);
                node.setItemHitses(null);
                node.setWishLists(null);
                node.setCart(null);
                node.setCategory(null);
                newItems.add(node);
            }
            category.setItems(newItems);
        }
        return category;
    }

    private static Privileges cleanPrivilege(Privileges privilege) {
        Iterator it = privilege.getAdmins().iterator();
        if (it != null) {
            Set<Admins> newadmins = new HashSet<>();
            while (it.hasNext()) {
                Admins node = (Admins) it.next();
                node.setPrivilege(null);
                node.setStore(null);
                newadmins.add(node);
            }
            privilege.setAdmins(newadmins);
        }
        return privilege;
    }

    private static Cart cleanCart(Cart cart) {
        Items item = cart.getItem();
        if (item != null) {
            item.setBrand(null);
            item.setCart(null);
            item.setWishLists(null);
            item.setItemHitses(null);
            item.setSpecifications(null);
            item.setOrderedItems(null);
            item.setPhotos(null);
            item.setCategory(null);
            cart.setItem(item);
        }
        Users user = cart.getUser();
        if (user != null) {
            user.setOrders(null);
            user.setWishLists(null);
            user.setCart(null);
            cart.setUser(user);
        }
        return cart;
    }

    private static Guests cleanGuest(Guests guest) {
        Iterator it = guest.getItemsHits().iterator();
        if (it != null) {
            Set<ItemHits> newItemHits = new HashSet<>();
            while (it.hasNext()) {
                ItemHits node = (ItemHits) it.next();
                node.setGuest(null);
                node.setItem(null);
                newItemHits.add(node);
            }
            guest.setItemsHits(newItemHits);
        }
        return guest;
    }

    private static ItemHits cleanItemHits(ItemHits itemHit) {
        Items node = itemHit.getItem();
        if (node != null) {
            node.setBrand(null);
            node.setPhotos(null);
            node.setOrderedItems(null);
            node.setSpecifications(null);
            node.setItemHitses(null);
            node.setWishLists(null);
            node.setCart(null);
            node.setCategory(null);
            itemHit.setItem(node);
        }
        Guests guest = itemHit.getGuest();
        if (guest != null) {
            guest.setItemsHits(null);
            itemHit.setGuest(guest);
        }
        return itemHit;
    }

    private static Items cleanItem(Items item) {
        Brand brandNode = item.getBrand();
        if (brandNode != null) {
            brandNode.setCategories(null);
            brandNode.setItems(null);
            item.setBrand(brandNode);
        }
        Iterator it = item.getCart().iterator();
        if (it != null) {
            Set<Cart> newCart = new HashSet<>();
            while (it.hasNext()) {
                Cart node = (Cart) it.next();
                node.setUser(null);
                node.setItem(null);
                newCart.add(node);
            }
            item.setCart(newCart);
        }
        it = item.getItemHitses().iterator();
        if (it != null) {
            Set<ItemHits> newItemHits = new HashSet<>();
            while (it.hasNext()) {
                ItemHits node = (ItemHits) it.next();
                node.setGuest(null);
                node.setItem(null);
                newItemHits.add(node);
            }
            item.setItemHitses(newItemHits);
        }
        it = item.getOrderedItems().iterator();
        if (it != null) {
            Set<OrderItem> newOrderItem = new HashSet<>();
            while (it.hasNext()) {
                OrderItem node = (OrderItem) it.next();
                node.setItem(null);
                node.setOrder(null);
                newOrderItem.add(node);
            }
            item.setOrderedItems(newOrderItem);
        }
        it = item.getPhotos().iterator();
        if (it != null) {
            Set<Photos> newPhotos = new HashSet<>();
            while (it.hasNext()) {
                Photos node = (Photos) it.next();
                node.setItem(null);
                newPhotos.add(node);
            }
            item.setPhotos(newPhotos);
        }
        it = item.getSpecifications().iterator();
        if (it != null) {
            Set<Specifications> newSpec = new HashSet<>();
            while (it.hasNext()) {
                Specifications node = (Specifications) it.next();
                node.setItem(null);
                newSpec.add(node);
            }
            item.setSpecifications(newSpec);
        }
        it = item.getWishLists().iterator();
        if (it != null) {
            Set<WishList> newWishList = new HashSet<>();
            while (it.hasNext()) {
                WishList node = (WishList) it.next();
                node.setItem(null);
                node.setUser(null);
                newWishList.add(node);
            }
            item.setWishLists(newWishList);
        }

        Categories category = item.getCategory();
        if (category != null) {
            category.setBrands(null);
            category.setItems(null);
            category.setStore(null);
            item.setCategory(category);
        }
        return item;
    }

    private static OrderItem cleanOrderItem(OrderItem orderItem) {
        Items node = (Items) orderItem.getItem();
        if (node != null) {
            node.setBrand(null);
            node.setPhotos(null);
            node.setOrderedItems(null);
            node.setSpecifications(null);
            node.setItemHitses(null);
            node.setWishLists(null);
            node.setCart(null);
            node.setCategory(null);
            orderItem.setItem(node);
        }
        Orders order = orderItem.getOrder();
        if (order != null) {
            order.setOrderItems(null);
            order.setUser(null);
            orderItem.setOrder(order);
        }
        return orderItem;
    }

    private static Orders cleanOrder(Orders order) {
        Iterator it = order.getOrderItems().iterator();
        if (it != null) {
            Set<OrderItem> newOrderItem = new HashSet<>();
            while (it.hasNext()) {
                OrderItem node = (OrderItem) it.next();
                Items item =node.getItem();
                item.setBrand(null);
                item.setCart(null);
                item.setCategory(null);
                item.setDescription(null);
                item.setDiscount(0);
                item.setItemHitses(null);
                item.setOrderedItems(null);
                item.setPhotos(null);
                item.setPrice(0);
                item.setSpecifications(null);
                item.setWishLists(null);
                item.setQuantity(0);

                node.setItem(item);
                node.setOrder(null);
                newOrderItem.add(node);
            }
            order.setOrderItems(newOrderItem);
        }
        Users user = order.getUser();
        if (user != null) {
            user.setCart(null);
            user.setWishLists(null);
            user.setOrders(null);
        }
        order.setUser(user);
        return order;
    }

    private static Photos cleanPhoto(Photos photo) {
        Items node = photo.getItem();
        if (node != null) {
            node.setBrand(null);
            node.setPhotos(null);
            node.setOrderedItems(null);
            node.setSpecifications(null);
            node.setItemHitses(null);
            node.setWishLists(null);
            node.setCart(null);
            node.setCategory(null);
            photo.setItem(node);
        }

        return photo;
    }

    private static Specifications cleanSpecification(Specifications specification) {
        Items node = specification.getItem();
        node.setBrand(null);
        node.setPhotos(null);
        node.setOrderedItems(null);
        node.setSpecifications(null);
        node.setItemHitses(null);
        node.setWishLists(null);
        node.setCart(null);
        node.setCategory(null);
        specification.setItem(node);
        return specification;
    }

    private static Users cleanUser(Users user) {
        Iterator it = user.getWishLists().iterator();
        if (it != null) {
            Set<WishList> newWishList = new HashSet<>();
            while (it.hasNext()) {
                WishList node = (WishList) it.next();
                node.setItem(null);
                node.setUser(null);
                newWishList.add(node);
            }
            user.setWishLists(newWishList);
        }

        it = user.getCart().iterator();
        if (it != null) {
            Set<Cart> newCarts = new HashSet<>();
            while (it.hasNext()) {
                Cart node = (Cart) it.next();
                node.setItem(null);
                node.setUser(null);
                newCarts.add(node);
            }
            user.setCart(newCarts);
        }

        it = user.getOrders().iterator();
        if (it != null) {
            Set<Orders> newOrder = new HashSet<>();
            while (it.hasNext()) {
                Orders node = (Orders) it.next();
                node.setOrderItems(null);
                node.setUser(null);
                newOrder.add(node);
            }
            user.setOrders(newOrder);
        }
        return user;
    }

    private static WishList cleanWishList(WishList wishList) {
        Items node = wishList.getItem();
        node.setBrand(null);
        node.setPhotos(null);
        node.setOrderedItems(null);
        node.setSpecifications(null);
        node.setItemHitses(null);
        node.setWishLists(null);
        node.setCart(null);
        node.setCategory(null);
        wishList.setItem(node);

        Users user = wishList.getUser();
        user.setOrders(null);
        user.setWishLists(null);
        user.setCart(null);
        wishList.setUser(user);

        return wishList;
    }
}
