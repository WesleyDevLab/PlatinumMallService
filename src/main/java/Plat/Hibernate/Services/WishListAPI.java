package Plat.Hibernate.Services;


import Plat.Hibernate.Entities.Users;
import Plat.Hibernate.Entities.WishList;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by MontaserQasem on 11/22/16.
 */
@Path("/wishLists")
@Produces(MediaType.APPLICATION_JSON)
public class WishListAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<WishList> getAllWishLists() {
        List<DataBaseObject> objects = manager.find(null, WishList.class);
        List<WishList> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, WishList.class);
            for (int i = 0; i < objects.size(); i++) {
                WishList node = (WishList) objects.get(i);
                result.add(node);
            }
        }
        return result;
    }

    @GET
    @Path("/{userId}")
    public List<WishList> getWishListByUserId(@PathParam("userId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> object = manager.find(rule, Users.class);
        List<WishList> result = new ArrayList<>();
        if (object != null && object.size() > 0) {
            object = EntityCleaner.clean(object, Users.class);
            Iterator it = ((Users) object.get(0)).getWishLists().iterator();
            while (it.hasNext()) {
                WishList node = (WishList) it.next();
                result.add(node);
            }
        }
        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addWishListRecord(WishList wishList) {
        manager.merge(wishList);
        return "Added";
    }

    @DELETE
    @Path("/{wishListId}")
    public String deleteWishListById(@PathParam("wishListId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(rule, WishList.class);
        if (objects == null || objects.size()==0) return "There's a problem with the wishList id";
        WishList wishList = (WishList) objects.get(0);
        manager.delete(wishList);
        return "WishList record deleted";
    }
}
