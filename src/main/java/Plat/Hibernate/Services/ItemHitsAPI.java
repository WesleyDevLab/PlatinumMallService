package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.*;
import Plat.Hibernate.Util.*;

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
    public String getAllItemHits() {
        List<DataBaseObject> objects = manager.find(null, ItemHits.class);
        return JsonParser.parse(EntityInitializer.init(objects, ItemHits.class));
    }

    @GET
    @Path("/{storeId}")
    public String getItemHitsByStoreId(@PathParam("storeId") int storeId) {
        List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, storeId), Store.class);
        if (objects != null && objects.size() > 0) {
            List<DataBaseObject> target = new ArrayList<>();
            Store store = (Store) objects.get(0);
            store = (Store) manager.initialize(store, "categories");
            for (Categories category : store.getCategories()) {
                category = (Categories) manager.initialize(category, "items");
                for (Items item : category.getItems()) {
                    item = (Items) manager.initialize(item, "itemHits");
                    for (ItemHits itemHit : item.getItemHitses())
                        target.add((DataBaseObject) itemHit);

                }
            }
            return JsonParser.parse(EntityInitializer.init(target, ItemHits.class));
        }

        return new ResponseMessage("There was a problem with the store id").getResponseMessage();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addItemHit(ItemHits itemHit) {
        manager.merge(itemHit);
        return new ResponseMessage("ItemHit has been recorded successfully").getResponseMessage();
    }
}
