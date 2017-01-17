package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Admins;
import Plat.Hibernate.Entities.Store;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by MontaserQasem on 11/19/16.
 */
@Path("/admins")
@Produces(MediaType.APPLICATION_JSON)
public class AdminsAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public String getAllAdmins() {
        List<DataBaseObject> objects = manager.find(null, Admins.class);
        if (objects != null && objects.size() > 0) {
            objects = EntityInitializer.init(objects, Admins.class);
            return JsonParser.parse(objects);
        }
        return "[]";
    }

    @GET
    @Path("/{storeId}")
    public String getAdminsByStoreId(@PathParam("storeId") int storeId) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, storeId);
        List<DataBaseObject> objects = manager.find(ruleObject, Admins.class);
        if (objects != null && objects.size() > 0) {
            objects = EntityInitializer.init(objects, Admins.class);
            return JsonParser.parse(objects);
        }
        return new ResponseMessage("There was an error with the store id").getResponseMessage();
    }

    @GET
    @Path("/{storeId}/{adminId}")
    public String getAdminByAdminIdAndStoreId(@PathParam("storeId") int storeId, @PathParam("adminId") int adminId) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, storeId);
        List<DataBaseObject> storeObject = manager.find(ruleObject, Store.class);
        if (storeObject != null && storeObject.size() > 0) {
            Store store = (Store) storeObject.get(0);
            store = (Store) manager.initialize(store, "admins");
            for (int i = 0; i < store.getAdmins().size(); i++) {
                Admins admin = store.getAdmins().get(i);
                if (admin.getId() == adminId) {
                    List<DataBaseObject> object = new ArrayList<>();
                    object.add(((DataBaseObject) admin));
                    object = EntityInitializer.init(object, Admins.class);
                    return JsonParser.parse(object);
                }
            }
        }
        return new ResponseMessage("There was an error with id").getResponseMessage();
    }

    @POST
    @Path("/{storeId}/{userName}/{password}")
    public String doLoginAdmin(@PathParam("storeId") int storeId, @PathParam("userName") String userName, @PathParam("password") String password) {
        List<DataBaseObject> storeObject = manager.find(new RuleObject("id", HibernateUtil.EQUAL, storeId), Store.class);
        if (storeObject != null && storeObject.size() > 0) {
            Store store = (Store) storeObject.get(0);
            store = (Store) manager.initialize(store, "admins");
            for (Admins admin : store.getAdmins())
                if (admin.getUsername().equals(userName) && admin.getPassword().equals(password))
                    return new ResponseMessage("Accepted").getResponseMessage();

            return new ResponseMessage("Wrong password or username").getResponseMessage();
        }
        return new ResponseMessage("There was an error with the store id").getResponseMessage();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addAdmin(Admins admin) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, admin.getStore().getId());
        List<DataBaseObject> objects = manager.find(rule, Store.class);
        if (objects == null || objects.size() == 0)
            return new ResponseMessage("There was a problem with the store id").getResponseMessage();

        Store store = (Store) objects.get(0);
        store = (Store) manager.initialize(store, "admins");
        for (Admins node : store.getAdmins())
            if (node.getUsername().equalsIgnoreCase(admin.getUsername()))
                return new ResponseMessage("username " + admin.getUsername() + " is already exist , please pick a different username").getResponseMessage();

        manager.merge(admin);
        return new ResponseMessage("Admin " + admin.getFirstName() + " has been added successfully").getResponseMessage();
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateAdmin(Admins admin) {
        List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, admin.getId()), Admins.class);
        if (objects != null && objects.size() > 0) {
            Admins admins = (Admins) objects.get(0);
            admin = (Admins) manager.initialize(admin, "store");
            admin.setStore(admin.getStore());
            manager.update(admin);
            return new ResponseMessage("Admin information has been updated successfully").getResponseMessage();
        }
        return new ResponseMessage("There was an error with the id").getResponseMessage();
    }

    @DELETE
    @Path("/{adminId}")
    public String deleteAdmin(@PathParam("adminId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> result = manager.find(rule, Admins.class);
        if (result == null || result.size() == 0)
            return new ResponseMessage("There was an error with id").getResponseMessage();
        Admins admin = (Admins) result.get(0);
        manager.delete(admin);
        return new ResponseMessage("admin " + admin.getUsername() + " has been deleted").getResponseMessage();
    }
}
