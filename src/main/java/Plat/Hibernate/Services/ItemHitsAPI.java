package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.ItemHits;
import Plat.Hibernate.Util.DataBaseManager;
import Plat.Hibernate.Util.DataBaseObject;
import Plat.Hibernate.Util.EntityCleaner;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MontaserQasem on 11/22/16.
 */
@Path("/itemHits")
@Produces(MediaType.APPLICATION_JSON)
public class ItemHitsAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<ItemHits> getAllItemHits() {
        List<DataBaseObject> objects = manager.find(null, ItemHits.class);
        List<ItemHits> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, ItemHits.class);
            for (int i = 0; i < objects.size(); i++) {
                ItemHits node = (ItemHits) objects.get(i);
                result.add(node);
            }
        }
        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addItemHit(ItemHits itemHit) {
        manager.merge(itemHit);
        return "ItemHit has been recorded successfully";
    }
}
