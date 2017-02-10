package Plat.Hibernate.Services;


import Plat.Hibernate.Entities.Store;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
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
    public String getAllStores() throws IOException {
        List<DataBaseObject> objects = manager.find(null, Store.class);
        return JsonParser.parse(EntityInitializer.init(objects, Store.class));
    }

    @GET
    @Path("/{storeId}")
    public String getStoreById(@PathParam("storeId") int id) throws IOException {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(rule, Store.class);
        if (objects != null && objects.size() > 0) {
            objects = EntityInitializer.init(objects, Store.class);
            return JsonParser.parse(objects.get(0));
        }
        return new ResponseMessage("There was an error in the store id you provided").getResponseMessage();
    }
}