package Plat.Hibernate;

import Plat.Hibernate.Entities.Store;
import Plat.Hibernate.Util.DataBaseManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("test")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        DataBaseManager manager = DataBaseManager.getInstance();
        manager.find(null,Store.class);
        return "Got it!";
    }
}
