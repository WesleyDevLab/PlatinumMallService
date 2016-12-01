package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Categories;
import Plat.Hibernate.Entities.Store;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by MontaserQasem on 11/20/16.
 */
@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
public class CategoriesAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<Categories> getAllCategories() {
        List<DataBaseObject> objects = manager.find(null, Categories.class);
        objects = EntityCleaner.clean(objects, Categories.class);
        List<Categories> categories = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            Categories category = (Categories) objects.get(i);
            categories.add(category);
        }
        return categories;
    }


    @GET
    @Path("/{storeId}")
    public List<Categories> getCategoriesByStoreId(@PathParam("storeId") int storeId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, storeId);
        List<DataBaseObject> objects = manager.find(rule, Store.class);
        List<Categories> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Store.class);
            Store store = (Store) objects.get(0);
            Iterator it = store.getCategories().iterator();
            while (it.hasNext())
                result.add(((Categories) it.next()));
        }
        return result;
    }

    @GET
    @Path("/{storeId}/{categoryId}")
    public Categories getCategoryByIdAndStoreId(@PathParam("storeId") int storeId, @PathParam("categoryId") int catId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, storeId);
        List<DataBaseObject> objects = manager.find(rule, Store.class);
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Store.class);
            Store store = (Store) objects.get(0);
            Iterator it = store.getCategories().iterator();
            while (it.hasNext()) {
                Categories node = (Categories) it.next();
                if (node.getId() == catId)
                    return node;
            }

        }
        return null;
    }

    @POST
    @Path("/{storeId}/{categoryName}")
    public Categories getCategoryByStoreIdAndName(@PathParam("storeId") int storeId, @PathParam("categoryName") String cateName) {
        List<Categories> categories = getCategoriesByStoreId(storeId);
        for (int i = 0; i < categories.size(); i++) {
            Categories category = (Categories) categories.get(i);
            if (category.getName().equalsIgnoreCase(cateName))
                return category;
        }
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addCategory(Categories category) {
        if (category.getStore() != null) {
            RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, category.getStore().getId());
            List<DataBaseObject> objects = manager.find(rule, Store.class);
            if (objects == null || objects.size() == 0) return "There's a problem with the store id";
            Store store = (Store) objects.get(0);
            Iterator it = store.getCategories().iterator();
            while (it.hasNext()) {
                Categories node = (Categories) it.next();
                if (node.getName().equalsIgnoreCase(category.getName())) return "This name is already taken";
            }
            manager.merge(category);
            return "Category has been added successfully";
        }
        manager.save(category);
        return "Category has been added successfully";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateCategory(Categories category) {
        manager.update(category);
        return "Category has been updated successfully";
    }

    @DELETE
    @Path("/{categoryId}")
    public String deleteCategory(@PathParam("categoryId") int categoryId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, categoryId);
        List<DataBaseObject> objects = manager.find(rule, Categories.class);
        if (objects == null || objects.size() == 0) return "There's a problem with the category id";
        Categories category = (Categories) objects.get(0);
        manager.delete(category);
        return "Category (" + category.getName() + ") has been deleted";
    }
}