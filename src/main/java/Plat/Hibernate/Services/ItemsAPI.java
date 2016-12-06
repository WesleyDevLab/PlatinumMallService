package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Categories;
import Plat.Hibernate.Entities.Items;
import Plat.Hibernate.Entities.Specifications;
import Plat.Hibernate.Entities.Store;
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
            for(int i = 0;i<objects.size();i++){
                RuleObject rule = new RuleObject("id",HibernateUtil.EQUAL,objects.get(i).getCategory().getId());
                List<DataBaseObject> plainObject = manager.find(rule,Categories.class);
                plainObject = EntityCleaner.clean(plainObject,Categories.class);
                Categories category = (Categories) plainObject.get(0);
                if(category.getStore().getId()==storeId)
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
        return null;
    }

    @POST
    @Path("/{operation}/{action}/{storeId}")
    public List<Items> getItemsByOperationAndAction(@PathParam("operation") String operation, @PathParam("action") String action, @PathParam("storeId") int storeId) {
        List<Items> items = getItemsByStoreId("getitemsbystoreid", storeId);
        if (operation.equalsIgnoreCase("sort") && action.equalsIgnoreCase("alpha")) {
            Collections.sort(items, new Comparator<Items>() {
                @Override
                public int compare(Items o1, Items o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            return items;
        }
        if (operation.equalsIgnoreCase("sort") && action.equalsIgnoreCase("priceasc")) {
            Collections.sort(items, new Comparator<Items>() {
                @Override
                public int compare(Items o1, Items o2) {
                    return (int) (o1.getPrice() - o2.getPrice());
                }
            });
            return items;
        }
        if (operation.equalsIgnoreCase("sort") && action.equalsIgnoreCase("pricedes")) {
            Collections.sort(items, new Comparator<Items>() {
                @Override
                public int compare(Items o1, Items o2) {
                    return (int) (o2.getPrice() - o1.getPrice());
                }
            });
            return items;
        }
        if (operation.equalsIgnoreCase("offset")) {
            int offSetValue = Integer.parseInt(action);
            List<Items> newItems = new ArrayList<>();
            for (int i = offSetValue; i < items.size(); i++)
                newItems.add(items.get(i));
            return newItems;
        }

        return null;
    }


    @POST
    @Path("/{operation}/{action}/{storeId}/{level}/{levelValue}")
    public List<Items> getItemsByOperationAndActionAndLevel(@PathParam("operation") String operation, @PathParam("action") String action, @PathParam("storeId") int storeId, @PathParam("level") String level,
                                                            @PathParam("levelValue") int levelValue) {


        if (level.equalsIgnoreCase("category")) {
            if (operation.equalsIgnoreCase("sort") && action.equalsIgnoreCase("alpha")) {
                List<Items> result = getAllItems();
                for (int i = 0; i < result.size(); i++)
                    if (result.get(i).getCategory() != null && result.get(i).getCategory().getId() == levelValue)
                        result.add(result.get(i));

                Collections.sort(result, new Comparator<Items>() {
                    @Override
                    public int compare(Items o1, Items o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });

                return result;
            }

            if (operation.equalsIgnoreCase("sort") && action.equalsIgnoreCase("priceasc")) {
                List<Items> result = getAllItems();
                for (int i = 0; i < result.size(); i++)
                    if (result.get(i).getCategory() != null && result.get(i).getCategory().getId() == levelValue)
                        result.add(result.get(i));

                Collections.sort(result, new Comparator<Items>() {
                    @Override
                    public int compare(Items o1, Items o2) {
                        return (int) (o1.getPrice() - o2.getPrice());
                    }
                });
                return result;
            }
            if (operation.equalsIgnoreCase("sort") && action.equalsIgnoreCase("pricedes")) {
                List<Items> result = getAllItems();
                for (int i = 0; i < result.size(); i++)
                    if (result.get(i).getCategory() != null && result.get(i).getCategory().getId() == levelValue)
                        result.add(result.get(i));

                Collections.sort(result, new Comparator<Items>() {
                    @Override
                    public int compare(Items o1, Items o2) {
                        return (int) (o2.getPrice() - o1.getPrice());
                    }
                });
                return result;
            }
        }
//-- end of cate
        if (level.equalsIgnoreCase("brand")) {
            if (operation.equalsIgnoreCase("sort") && action.equalsIgnoreCase("alpha")) {
                List<Items> result = getAllItems();
                for (int i = 0; i < result.size(); i++)
                    if (result.get(i).getBrand() != null && result.get(i).getBrand().getId() == levelValue)
                        result.add(result.get(i));

                Collections.sort(result, new Comparator<Items>() {
                    @Override
                    public int compare(Items o1, Items o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                return result;
            }

            if (operation.equalsIgnoreCase("sort") && action.equalsIgnoreCase("priceasc")) {
                List<Items> result = getAllItems();
                for (int i = 0; i < result.size(); i++)
                    if (result.get(i).getBrand() != null && result.get(i).getBrand().getId() == levelValue)
                        result.add(result.get(i));

                Collections.sort(result, new Comparator<Items>() {
                    @Override
                    public int compare(Items o1, Items o2) {
                        return (int) (o1.getPrice() - o2.getPrice());
                    }
                });
                return result;
            }

            if (operation.equalsIgnoreCase("sort") && action.equalsIgnoreCase("pricedes")) {
                List<Items> result = getAllItems();
                for (int i = 0; i < result.size(); i++)
                    if (result.get(i).getBrand() != null && result.get(i).getBrand().getId() == levelValue)
                        result.add(result.get(i));

                Collections.sort(result, new Comparator<Items>() {
                    @Override
                    public int compare(Items o1, Items o2) {
                        return (int) (o2.getPrice() - o1.getPrice());
                    }
                });
                return result;
            }

            return null;
        }

        return null;
    }

    @POST
    @Path("{findByRange}/{storeId}/{minValue}/{maxValue}/{filter}/{filterValue}")
    public List<Items> findItemsByRange(@PathParam("findByRange") String operation, @PathParam("storeId") int storeId,
                                        @PathParam("minValue") int min, @PathParam("maxValue") int max, @PathParam("filter") String filter,
                                        @PathParam("filterValue") String filterValue) {

        if (!operation.equalsIgnoreCase("findbyrange")) return null;


        if (filter.equalsIgnoreCase("all")) {
            List<DataBaseObject> objects = manager.find(null, Items.class);
            objects = EntityCleaner.clean(objects, Items.class);
            List<Items> result = new ArrayList<>();
            for (int i = 0; i < objects.size(); i++) {
                Items item = (Items) objects.get(i);
                if (item.getPrice() >= min && item.getPrice() <= max)
                    result.add(item);
            }
            return result;
        }
        if (filter.equalsIgnoreCase("category")) {
            RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, storeId);
            List<DataBaseObject> objects = manager.find(rule, Store.class);
            List<Items> result = new ArrayList<>();
            if (objects == null || objects.size() == 0) return null;
            objects = EntityCleaner.clean(objects, Store.class);
            Categories category = null;
            Store store = (Store) objects.get(0);
            if (store.getCategories() != null) {
                Iterator it = store.getCategories().iterator();
                if (it != null) {
                    while (it.hasNext()) {
                        Categories cat = (Categories) it.next();
                        if (cat.getName().equalsIgnoreCase(filterValue)) {
                            category = cat;
                            break;
                        }
                    }
                }
            }
            if (category.getItems() != null) {
                Iterator itemIt = category.getItems().iterator();
                if (itemIt != null) {
                    while (itemIt.hasNext()) {
                        Items item = (Items) itemIt.next();
                        if (item.getPrice() >= min && item.getPrice() <= max)
                            result.add(item);
                    }
                }
            }
            return result;
        }

        if (filter.equalsIgnoreCase("brand")) {
            RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, storeId);
            List<DataBaseObject> objects = manager.find(rule, Store.class);
            List<Items> result = new ArrayList<>();
            if (objects == null || objects.size() == 0) return null;
            objects = EntityCleaner.clean(objects, Store.class);
            Store store = (Store) objects.get(0);
            if (store.getCategories() != null) {
                Iterator cateId = store.getCategories().iterator();
                if (cateId != null) {
                    while (cateId.hasNext()) {
                        Categories category = (Categories) cateId.next();
                        if (category.getItems() != null) {
                            Iterator itemIt = category.getItems().iterator();
                            if (itemIt != null) {
                                while (itemIt.hasNext()) {
                                    Items item = (Items) itemIt.next();
                                    item = getItemById(item.getId());
                                    if (item.getBrand().getName().equalsIgnoreCase(filterValue) && item.getPrice() >= min && item.getPrice() <= max)
                                        result.add(item);
                                }
                            }
                        }
                    }
                }
            }
            return result;
        }

        return null;
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
        manager.delete(item);
        return "item (" + item.getName() + ") deleted";
    }
}
