package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Admins;
import Plat.Hibernate.Entities.Store;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by MontaserQasem on 11/19/16.
 */
@Path("/admins")
@Produces(MediaType.APPLICATION_JSON)
public class AdminsAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<Admins> getAllAdmins() {
        List<DataBaseObject> result = manager.find(null, Admins.class);
        result = EntityCleaner.clean(result, Admins.class);
        List<Admins> admins = new ArrayList<>();
        if (result != null && result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                Admins admin = (Admins) result.get(i);
                admins.add(admin);
            }
        }

        return admins;
    }

    @GET
    @Path("/{storeId}")
    public List<Admins> getAdminsByStoreId(@PathParam("storeId") int storeId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, storeId);
        List<DataBaseObject> objects = manager.find(rule, Store.class);
        objects = EntityCleaner.clean(objects, Store.class);
        List<Admins> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            Store store = (Store) objects.get(0);
            Set<Admins> setAdmins = store.getAdmins();
            Iterator it = setAdmins.iterator();
            while (it.hasNext())
                result.add(((Admins) it.next()));
        }
        return result;
    }

    @GET
    @Path("/{storeId}/{adminId}")
    public Admins getAdminByAdminIdAndStoreId(@PathParam("storeId") int storeId, @PathParam("adminId") int adminId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, storeId);
        List<DataBaseObject> storeObjects = manager.find(rule, Store.class);
        storeObjects = EntityCleaner.clean(storeObjects, Store.class);
        if (storeObjects != null && storeObjects.size() > 0) {
            Store store = (Store) storeObjects.get(0);
            Iterator it = store.getAdmins().iterator();
            while (it.hasNext()) {
                Admins adminNode = (Admins) it.next();
                if (adminNode.getId() == adminId) return adminNode;
            }
        }
        return null;
    }

    @POST
    @Path("/{storeId}/{userName}/{password}")
    public String checkAdminInfo(@PathParam("storeId") int storeId, @PathParam("userName") String userName, @PathParam("password") String password) {
        List<Admins> admins = getAdminsByStoreId(storeId);
        for (int i = 0; i < admins.size(); i++) {
            Admins admin = (Admins) admins.get(i);
            if (admin.getUsername().equals(userName) && admin.getPassword().equals(password))
                return "1&"+admin.getId();
        }
        return "0";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addAdmin(Admins admin) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, admin.getStore().getId());
        List<DataBaseObject> objects = manager.find(rule, Store.class);
        if (objects == null || objects.size() == 0) return "There's a problem with the store id";
        Store store = (Store) objects.get(0);
        Iterator it = store.getAdmins().iterator();
        while (it.hasNext()) {
            Admins node = (Admins) it.next();
            if (node.getUsername().equalsIgnoreCase(admin.getUsername()))
                return "Username (" + admin.getUsername() + ") already exist";
        }
        manager.merge(admin);
        return "Admin (" + admin.getFirstName() + ") has been added successfully";
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateAdmin(Admins admin) {
        System.out.println(admin.getPrivilege().getName());
        manager.update(admin);
        return "Admin information has been updated successfully";
    }

    @DELETE
    @Path("/{adminId}")
    public String deleteAdmin(@PathParam("adminId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> result = manager.find(rule, Admins.class);
        if (result == null || result.size() == 0)
            return "Admin record  is not exist to delete";

        manager.delete(result.get(0));
        return "admin record deleted";
    }
}
