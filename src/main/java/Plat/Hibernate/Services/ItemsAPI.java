package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.*;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by MontaserQasem on 11/22/16.
 */
@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
public class ItemsAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<Items> getAllItems() {
        List<DataBaseObject> objects = manager.find(null, Items.class);
        List<Items> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Items.class);
            for (int i = 0; i < objects.size(); i++) {
                Items node = (Items) objects.get(i);
                result.add(node);
            }
        }
        Collections.sort(result, new Comparator<Items>() {
            @Override
            public int compare(Items o1, Items o2) {
                return o2.getId() - o1.getId();
            }
        });
        return result;
    }

    @GET
    @Path("/{itemId}")
    public Items getItemById(@PathParam("itemId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(rule, Items.class);
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Items.class);
            Items item = (Items) objects.get(0);
            return item;
        }
        return null;
    }

    @GET
    @Path("/{storeId}/{itemName}")
    public List<Items> getItemsByNameAndStoreId(@PathParam("storeId") int storeId, @PathParam("itemName") String name) {
        List<Items> items = getItemsByName(name);
        List<Items> result = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, items.get(i).getCategory().getId());
            List<DataBaseObject> object = manager.find(rule, Categories.class);
            Categories category = (Categories) object.get(0);
            if (category.getStore().getId() == storeId)
                result.add(items.get(i));
        }
        return result;
    }

    @POST
    @Path("/{itemName}")
    public List<Items> getItemsByName(@PathParam("itemName") String name) {
        RuleObject rule = new RuleObject("name", HibernateUtil.LIKE, name);
        List<DataBaseObject> objects = manager.find(rule, Items.class);
        List<Items> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Items.class);
            for (int i = 0; i < objects.size(); i++) {
                Items node = (Items) objects.get(i);
                result.add(node);
            }
        }
        return result;
    }

    @POST
    @Path("/{operation}/{storeId}")
    public List<Items> getItemsByStoreId(@PathParam("operation") String operation, @PathParam("storeId") int storeId) {
        if (operation.equalsIgnoreCase("getitemsbystoreid")) {
            List<Items> result = new ArrayList<>();
            List<Items> objects = getAllItems();
            for (int i = 0; i < objects.size(); i++) {
                RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, objects.get(i).getCategory().getId());
                List<DataBaseObject> plainObject = manager.find(rule, Categories.class);
                plainObject = EntityCleaner.clean(plainObject, Categories.class);
                Categories category = (Categories) plainObject.get(0);
                if (category.getStore().getId() == storeId)
                    result.add(objects.get(i));
            }
            Collections.sort(result, new Comparator<Items>() {
                @Override
                public int compare(Items o1, Items o2) {
                    return (int) (o2.getId() - o1.getId());
                }
            });
            return result;
        }
        if (operation.equalsIgnoreCase("getmostwantedtenitemsbystoreid")) {
            List<Items> objects = getAllItems();
            Collections.sort(objects, new Comparator<Items>() {
                @Override
                public int compare(Items o1, Items o2) {
                    return o2.getItemHitses().size() - o1.getItemHitses().size();
                }
            });
            OrdersAPI ordersAPI = new OrdersAPI();
            List<Items> result = new ArrayList<>();
            for (int i = 0; i < (objects.size() < 10 ? objects.size() : 10); i++)
                if (ordersAPI.getItemsStoreId(objects.get(i).getId()) == storeId)
                    result.add(objects.get(i));
            return result;
        }
        if (operation.equalsIgnoreCase("getmostwantedtenitemsinwishlistbystoreid")) {
            List<Items> objects = getAllItems();
            Collections.sort(objects, new Comparator<Items>() {
                @Override
                public int compare(Items o1, Items o2) {
                    return o2.getWishLists().size() - o1.getWishLists().size();
                }
            });
            OrdersAPI ordersAPI = new OrdersAPI();
            List<Items> result = new ArrayList<>();
            for (int i = 0; i < (objects.size() < 10 ? objects.size() : 10); i++)
                if (ordersAPI.getItemsStoreId(objects.get(i).getId()) == storeId)
                    result.add(objects.get(i));
            return result;
        }
        return null;
    }


    @POST
    @Path("{storeId}/{sort}/{sortValue}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Items> getItemsByObjectView(ItemsView itemsView, @PathParam("storeId") int storeId, @PathParam("sort") boolean sort, @PathParam("sortValue") String sortValue) {
        List<Items> objects = getItemsByStoreId("getitemsbystoreid", storeId);
        List<Items> result = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            if (itemsView.getCategories() != null) {
                boolean categoryFlag = false;
                List<Categories> categories = itemsView.getCategories();
                for (int j = 0; j < categories.size(); j++)
                    if (categories.get(j).getId() == objects.get(i).getCategory().getId()) {
                        categoryFlag = true;
                        break;
                    }

                if (!categoryFlag) continue;
            }
            if (itemsView.getBrands() != null) {
                List<Brand> brands = itemsView.getBrands();
                boolean brandFlag = false;
                for (int k = 0; k < brands.size(); k++)
                    if (brands.get(k).getId() == objects.get(i).getBrand().getId()) {
                        brandFlag = true;
                        break;
                    }
                if (!brandFlag) continue;
            }
            if (itemsView.isInStock() && objects.get(i).getQuantity() == 0) continue;
            if (itemsView.isSpecialPrices() && objects.get(i).getDiscount() == 0) continue;
            if (itemsView.getMin() != -1 && itemsView.getMax() != -1)
                if (objects.get(i).getPrice() < itemsView.getMin() || objects.get(i).getPrice() > itemsView.getMax())
                    continue;

            result.add(objects.get(i));
        }
        if (sort) {
            if (sortValue.equalsIgnoreCase("a-z"))
                Collections.sort(result, new Comparator<Items>() {
                    @Override
                    public int compare(Items o1, Items o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });

            if (sortValue.equalsIgnoreCase("z-a"))
                Collections.sort(result, new Comparator<Items>() {
                    @Override
                    public int compare(Items o1, Items o2) {
                        return o2.getName().compareTo(o1.getName());
                    }
                });

            if (sortValue.equalsIgnoreCase("asc"))
                Collections.sort(result, new Comparator<Items>() {
                    @Override
                    public int compare(Items o1, Items o2) {
                        return (int) (o1.getPrice() - o2.getPrice());
                    }
                });

            if (sortValue.equalsIgnoreCase("desc"))
                Collections.sort(result, new Comparator<Items>() {
                    @Override
                    public int compare(Items o1, Items o2) {
                        return (int) (o1.getPrice() - o2.getPrice());
                    }
                });
        } else
            Collections.sort(result, new Comparator<Items>() {
                @Override
                public int compare(Items o1, Items o2) {
                    return o2.getId() - o1.getId();
                }
            });
        List<Items> cut = new ArrayList<>();
        for (int i = itemsView.getOffset(); i < ((result.size() - itemsView.getOffset()) > 10 ? 10 : result.size()); i++)
            cut.add(result.get(i));

        return cut;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addItem(Items item) {
        manager.merge(item);
        return "1";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateItem(Items item) {
        manager.update(item);
        return "item (" + item.getName() + ") has been updated";
    }

    @DELETE
    @Path("/{itemId}")
    public String deleteItem(@PathParam("itemId") int itemId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, itemId);
        List<DataBaseObject> object = manager.find(rule, Items.class);
        if (object == null || object.size() == 0) return "There's a problem with the item id";
        Items item = (Items) object.get(0);
        List<DataBaseObject> objects = manager.find(null, Specifications.class);
        List<DataBaseObject> target = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++)
            if (((Specifications) objects.get(i)).getItem().getId() == itemId)
                target.add(objects.get(i));
        manager.deleteList(target);
        objects = manager.find(null, Photos.class);
        List<DataBaseObject> photoTarget = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++)
            if (((Photos) objects.get(i)).getItem().getId() == itemId)
                photoTarget.add(objects.get(i));
        manager.deleteList(photoTarget);
        objects = manager.find(null, Cart.class);
        List<DataBaseObject> cartTarget = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++)
            if (((Cart) objects.get(i)).getItem().getId() == itemId)
                cartTarget.add(objects.get(i));
        objects = manager.find(null, WishList.class);
        List<DataBaseObject> wishlistTarget = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++)
            if (((WishList) objects.get(i)).getItem().getId() == itemId)
                wishlistTarget.add(objects.get(i));
        manager.deleteList(wishlistTarget);
        objects = manager.find(null, OrderItem.class);
        List<DataBaseObject> orderItemsTarget = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++)
            if (((OrderItem) objects.get(i)).getItem().getId() == itemId)
                orderItemsTarget.add(objects.get(i));
        manager.deleteList(orderItemsTarget);
        objects = manager.find(null, ItemHits.class);
        List<DataBaseObject> itemHitsTarget = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++)
            if (((ItemHits) objects.get(i)).getItem().getId() == itemId)
                itemHitsTarget.add(objects.get(i));
        manager.deleteList(itemHitsTarget);

        manager.delete(item);
        return "item (" + item.getName() + ") deleted";
    }
}
