package Plat.Hibernate.Services;


import Plat.Hibernate.Entities.Store;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MontaserQasem on 11/19/16.
 */
@Path("/stores")
@Produces(MediaType.APPLICATION_JSON)
public class StoreAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<Store> getAllItems() {
        List<Store> result = new ArrayList<>();
        List<DataBaseObject> stores = manager.find(null, Store.class);
        if (stores != null && stores.size() > 0) {
            stores = EntityCleaner.clean(stores, Store.class);
            for (int i = 0; i < stores.size(); i++) {
                Store store = (Store) stores.get(i);
                result.add(store);
            }
        }
        return result;
    }

    @GET
    @Path("/{storeId}")
    public Store getItemById(@PathParam("storeId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(rule, Store.class);
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Store.class);
            Store store = (Store) objects.get(0);
            return store;
        }
        return null;
    }

    @POST
    @Path("/{storeName}")
    public List<Store> getStoresByName(@PathParam("storeName") String name) {
        RuleObject rule = new RuleObject("name", HibernateUtil.LIKE, name);
        List<Store> result = new ArrayList<>();
        List<DataBaseObject> stores = manager.find(rule, Store.class);
        stores = EntityCleaner.clean(stores, Store.class);
        if (stores != null && stores.size() > 0) {
            for (int i = 0; i < stores.size(); i++) {
                Store store = (Store) stores.get(i);
                result.add(store);
            }
        }

        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addStore(Store store) {
        manager.save(store);
        return "Store Added Successfully";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateStore(Store store) {
        manager.update(store);
        return "Store updated Successfully";
    }

    @DELETE
    @Path("/{storeId}")
    public String deleteStore(@PathParam("storeId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> result = manager.find(rule, Store.class);
        if (result == null || result.size() == 0)
            return "Store is not exist to delete";

        manager.delete(result.get(0));
        return "Store deleted";
    }


}