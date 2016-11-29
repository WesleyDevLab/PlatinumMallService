package Plat.Hibernate.Services;


import Plat.Hibernate.Entities.Privileges;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MontaserQasem on 11/20/16.
 */
@Path("/privileges")
@Produces(MediaType.APPLICATION_JSON)
public class PrivilegesAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<Privileges> getAllPrivileges() {
        List<DataBaseObject> objects = manager.find(null, Privileges.class);
        objects = EntityCleaner.clean(objects, Privileges.class);
        List<Privileges> privileges = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            Privileges prv = (Privileges) objects.get(i);
            privileges.add(prv);
        }
        return privileges;
    }

    @GET
    @Path("/{privilegeId}")
    public Privileges getPrivilegeById(@PathParam("privilegeId") int prvId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, prvId);
        List<DataBaseObject> objects = manager.find(rule, Privileges.class);
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Privileges.class);
            Privileges privilege = (Privileges) objects.get(0);
            return privilege;
        }
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addPrivilege(Privileges privilege) {
        RuleObject rule = new RuleObject("name", HibernateUtil.EQUAL, privilege.getName());
        List<DataBaseObject> objects = manager.find(rule, Privileges.class);
        if (objects != null && objects.size() > 0) return "This name is already has a privilege";
        manager.save(privilege);
        return "Privilege has been added Successfully";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updatePrivilege(Privileges privilege) {
        manager.update(privilege);
        return "Privilage has been updated successfully";
    }

    @DELETE
    @Path("/{privilegeId}")
    public String updatePrivilege(@PathParam("privilegeId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(rule, Privileges.class);
        if (objects == null || objects.size() == 0) return "Privilege id is not exist";
        Privileges privilege = (Privileges) objects.get(0);
        manager.delete(privilege);
        return "Privilege " + privilege.getName() + " deleted";
    }
}
