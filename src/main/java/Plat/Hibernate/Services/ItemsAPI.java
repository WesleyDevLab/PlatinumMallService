package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.*;
import Plat.Hibernate.RestricationObjects.ItemsView;
import Plat.Hibernate.Util.*;

import javax.json.Json;
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
    public String getAllItems() {
        List<DataBaseObject> objects = manager.find(null, Items.class);
        List<Items> result = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++)
            result.add((Items) objects.get(i));
        Collections.sort(result, new Comparator<Items>() {
            @Override
            public int compare(Items o1, Items o2) {
                return o2.getId() - o1.getId();
            }
        });
        List<DataBaseObject> target = (List<DataBaseObject>) (List<?>) result;
        return JsonParser.parse(EntityInitializer.init(target, Items.class));
    }

    @GET
    @Path("/{itemId}")
    public String getItemById(@PathParam("itemId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(rule, Items.class);
        if (objects != null && objects.size() > 0)
            return JsonParser.parse(EntityInitializer.init(objects, Items.class));
        return new ResponseMessage("There was an error with the item id").getResponseMessage();
    }

    @GET
    @Path("/{storeId}/{itemName}")
    public String getItemsByNameAndStoreId(@PathParam("storeId") int storeId, @PathParam("itemName") String name) {
        List<DataBaseObject> objects = manager.find(new RuleObject("name", HibernateUtil.LIKE, name), Items.class);
        List<DataBaseObject> targer = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            Items item = (Items) objects.get(i);
            item = (Items) manager.initialize(item, "category");
            Categories category = item.getCategory();
            if (category.getStore().getId() == storeId)
                targer.add((DataBaseObject) item);
        }
        return JsonParser.parse(targer);
    }

    @POST
    @Path("/{itemName}")
    public String getItemsByName(@PathParam("itemName") String name) {
        List<DataBaseObject> objects = manager.find(new RuleObject("name", HibernateUtil.LIKE, name), Items.class);
        return JsonParser.parse(objects);
    }

    @POST
    @Path("/{operation}/{storeId}")
    public String getItemsByOperationAndStoreId(@PathParam("operation") String operation, @PathParam("storeId") int storeId) {
        if (operation.equalsIgnoreCase("getitemsbystoreid")) {
            List<Items> items = ItemsService.getItemsByStoreId(storeId);
            if (items == null)
                return new ResponseMessage("There was a problem in the store id").getResponseMessage();

            List<DataBaseObject> target = (List<DataBaseObject>) (List<?>) items;
            return JsonParser.parse(target);
        }
        if (operation.equalsIgnoreCase("getmostwantedtenitemsbystoreid")) {
            List<Items> items = ItemsService.getItemsByStoreId(storeId);
            if (items == null)
                return new ResponseMessage("There was a problem in the store id").getResponseMessage();
            List<Items> orderedItems = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                Items item = items.get(i);
                item = (Items) manager.initialize(item, "itemHits");
                orderedItems.add(item);
            }
            Collections.sort(orderedItems, new Comparator<Items>() {
                @Override
                public int compare(Items o1, Items o2) {
                    return o2.getItemHitses().size() - o1.getItemHitses().size();
                }
            });

            List<DataBaseObject> target = (List<DataBaseObject>) (List<?>) orderedItems;
            return JsonParser.parse(target);
        }

        if (operation.equalsIgnoreCase("getmostwantedtenitemsinwishlistbystoreid")) {
            List<Items> items = ItemsService.getItemsByStoreId(storeId);
            if (items == null)
                return new ResponseMessage("There was a problem in the store id").getResponseMessage();

            Collections.sort(items, new Comparator<Items>() {
                @Override
                public int compare(Items o1, Items o2) {
                    return o2.getWishLists().size() - o1.getWishLists().size();
                }
            });

            List<DataBaseObject> target = (List<DataBaseObject>) (List<?>) items;
            return JsonParser.parse(target);
        }

        if (operation.equalsIgnoreCase("getpopularfromall")) {// returns most three popular items
            List<DataBaseObject> objects = manager.find(null, Items.class);
            if (objects == null || objects.size() == 0)
                return new ResponseMessage("There is no items").getResponseMessage();

            List<Items> items = (List<Items>) (List<?>) objects;
            Collections.sort(items, new Comparator<Items>() {
                @Override
                public int compare(Items o1, Items o2) {
                    return o2.getItemHitses().size() - o1.getItemHitses().size();
                }
            });

            List<DataBaseObject> target = new ArrayList<>();
            for (int i = 0; i < items.size() && i <= 3; i++)
                target.add((DataBaseObject) items.get(i));

            return JsonParser.parse(target);
        }
        if (operation.equalsIgnoreCase("getpopularfromstore")) {// returns most three popular items
            List<Items> items = ItemsService.getItemsByStoreId(storeId);
            if (items == null || items.size() == 0)
                return new ResponseMessage("There is no items").getResponseMessage();
            Collections.sort(items, new Comparator<Items>() {
                @Override
                public int compare(Items o1, Items o2) {
                    return o2.getItemHitses().size() - o1.getItemHitses().size();
                }
            });

            List<DataBaseObject> target = new ArrayList<>();
            for (int i = 0; i < items.size() && i <= 3; i++)
                target.add((DataBaseObject) items.get(i));
            return JsonParser.parse(target);
        }

        if (operation.equalsIgnoreCase("getdiscountfromall")) {
            List<Items> items = (List<Items>) (List<?>) manager.find(null, Items.class);
            Collections.sort(items, new Comparator<Items>() {
                @Override
                public int compare(Items o1, Items o2) {
                    if (o1.getDiscount() < o2.getDiscount()) return 1;
                    if (o1.getDiscount() < o2.getDiscount()) return -1;
                    return 0;
                }
            });

            List<DataBaseObject> target = new ArrayList<>();
            for (int i = 0; i < items.size(); i++)
                target.add((DataBaseObject) items.get(i));
            return JsonParser.parse(target);
        }

        if (operation.equalsIgnoreCase("getdiscountfromall")) {
            List<Items> items = ItemsService.getItemsByStoreId(storeId);
            Collections.sort(items, new Comparator<Items>() {
                @Override
                public int compare(Items o1, Items o2) {
                    if (o1.getDiscount() < o2.getDiscount()) return 1;
                    if (o1.getDiscount() < o2.getDiscount()) return -1;
                    return 0;
                }
            });

            List<DataBaseObject> target = new ArrayList<>();
            for (int i = 0; i < items.size(); i++)
                target.add((DataBaseObject) items.get(i));
            return JsonParser.parse(target);
        }

        return null;
    }


    @POST
    @Path("{storeId}/{sort}/{sortValue}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String getItemsByObjectView(ItemsView itemsView, @PathParam("storeId") int storeId, @PathParam("sort") boolean sort, @PathParam("sortValue") String sortValue) {
        List<Items> objects = ItemsService.getItemsByStoreId(storeId);
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

        List<DataBaseObject> target = (List<DataBaseObject>) (List<?>) cut;
        return JsonParser.parse(target);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addItem(Items item) {
        manager.merge(item);
        return new ResponseMessage("1").getResponseMessage();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateItem(Items item) {
        manager.update(item);
        return new ResponseMessage("item " + item.getName() + " has been updated").getResponseMessage();
    }

    @DELETE
    @Path("/{itemId}")
    public String deleteItem(@PathParam("itemId") int itemId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, itemId);
        List<DataBaseObject> object = manager.find(rule, Items.class);
        if (object == null || object.size() == 0)
            return new ResponseMessage("There was a problem with the item id").getResponseMessage();

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

        return new ResponseMessage("item " + item.getName() + " deleted").getResponseMessage();
    }
}
