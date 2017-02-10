package Plat.Hibernate.Services;


import Plat.Hibernate.Entities.Privileges;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
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
    public String getAllPrivileges() throws IOException {
        List<DataBaseObject> objects = manager.find(null, Privileges.class);
        return JsonParser.parse(objects);
    }

    @GET
    @Path("/{privilegeId}")
    public String getPrivilegeById(@PathParam("privilegeId") int prvId) throws IOException {
        List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, prvId), Privileges.class);
        return JsonParser.parse(objects.get(0));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addPrivilege(Privileges privilege) {
        List<DataBaseObject> objects = manager.find(new RuleObject("name", HibernateUtil.EQUAL, privilege.getName()), Privileges.class);
        if (objects != null && objects.size() > 0)
            return new ResponseMessage("This name is already has a privilege").getResponseMessage();
        manager.save(privilege);

        return new ResponseMessage("Privilege has been added Successfully").getResponseMessage();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updatePrivilege(Privileges privilege) {
        manager.update(privilege);
        return new ResponseMessage("Privilage has been updated successfully").getResponseMessage();
    }

    @DELETE
    @Path("/{privilegeId}")
    public String DeletePrivilege(@PathParam("privilegeId") int privilegeId) {
        List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, privilegeId), Privileges.class);
        if (objects == null || objects.size() == 0)
            return new ResponseMessage("There was a problem with the Privilege id").getResponseMessage();
        Privileges privilege = (Privileges) objects.get(0);
        manager.delete(privilege);

        return new ResponseMessage("Privilege " + privilege.getName() + " deleted").getResponseMessage();
    }
}
