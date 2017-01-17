package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Categories;
import Plat.Hibernate.Entities.Store;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by MontaserQasem on 11/20/16.
 */
@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
public class CategoriesAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public String getAllCategories() {
        List<DataBaseObject> objects = manager.find(null, Categories.class);
        return JsonParser.parse(EntityInitializer.init(objects, Categories.class));
    }


    @GET
    @Path("/{storeId}")
    public String getCategoriesByStoreId(@PathParam("storeId") int storeId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, storeId);
        List<DataBaseObject> objects = manager.find(rule, Store.class);
        if (objects != null && objects.size() > 0) {
            Store store = (Store) objects.get(0);
            store = (Store) manager.initialize(store, "categories");
            List<DataBaseObject> target = new ArrayList<>();
            for (Categories category : store.getCategories())
                target.add((DataBaseObject) category);
            return JsonParser.parse(EntityInitializer.init(target, Categories.class));
        }
        return new ResponseMessage("There was an error with the store id").getResponseMessage();
    }

    @GET
    @Path("/{storeId}/{categoryId}")
    public String getCategoryByIdAndStoreId(@PathParam("storeId") int storeId, @PathParam("categoryId") int catId) {
        List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, catId), Categories.class);
        if (objects != null && objects.size() > 0) {
            Categories category = (Categories) objects.get(0);
            category = (Categories) manager.initialize(category, "store");
            if (category.getStore().getId() == storeId) {
                List<DataBaseObject> target = new ArrayList<>();
                target.add((DataBaseObject) category);
                return JsonParser.parse(target);
            }
            return new ResponseMessage("There was an error with the store id").getResponseMessage();
        }
        return new ResponseMessage("There was an error with the category id").getResponseMessage();
    }

    @POST
    @Path("/{catId}")
    public String getCategoryById(@PathParam("catId") int catId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, catId);
        List<DataBaseObject> objects = manager.find(rule, Categories.class);
        if (objects == null || objects.size() == 0)
            return new ResponseMessage("There was an error with the category id").getResponseMessage();
        return JsonParser.parse(objects);
    }

    @POST
    @Path("/{storeId}/{categoryName}")
    public String getCategoryByStoreIdAndName(@PathParam("storeId") int storeId, @PathParam("categoryName") String cateName) {
        List<DataBaseObject> objects = manager.find(new RuleObject("name", HibernateUtil.LIKE, cateName), Categories.class);
        return JsonParser.parse(objects);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addCategory(Categories category) {
        if (category.getStore() != null) {
            RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, category.getStore().getId());
            List<DataBaseObject> objects = manager.find(rule, Store.class);
            if (objects == null || objects.size() == 0)
                return new ResponseMessage("There's a problem with the store id").getResponseMessage();

            Store store = (Store) objects.get(0);
            store = (Store) manager.initialize(store, "categories");
            for (Categories node : store.getCategories())
                if (node.getName().equalsIgnoreCase(category.getName()))
                    return new ResponseMessage("This name is already exist please pick a different name to your category").getResponseMessage();

            manager.merge(category);
            return new ResponseMessage("Category has been added successfully").getResponseMessage();
        }
        return new ResponseMessage("Please provide a store object with the store id in your category object").getResponseMessage();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateCategory(Categories category) {
        List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, category.getId()), Categories.class);
        Categories node = (Categories) objects.get(0);
        node = (Categories) manager.initialize(node, "store");
        category.setStore(node.getStore());
        manager.update(category);
        return new ResponseMessage("Category has been updated successfully").getResponseMessage();
    }

    @DELETE
    @Path("/{categoryId}")
    public String deleteCategory(@PathParam("categoryId") int categoryId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, categoryId);
        List<DataBaseObject> objects = manager.find(rule, Categories.class);
        if (objects == null || objects.size() == 0)
            return new ResponseMessage("There's a problem with the category id").getResponseMessage();
        Categories category = (Categories) objects.get(0);
        manager.delete(category);
        return new ResponseMessage("Category " + category.getName() + " has been deleted").getResponseMessage();
    }
}
