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
@Path("/wishlists")
@Produces(MediaType.APPLICATION_JSON)
public class WishListAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public String getAllWishLists() {
        List<DataBaseObject> objects = manager.find(null, WishList.class);
        return JsonParser.parse(objects);
    }

    @GET
    @Path("/{userId}")
    public String getWishListByUserId(@PathParam("userId") int userId) {
        List<DataBaseObject> object = manager.find(new RuleObject("id", HibernateUtil.EQUAL, userId), Users.class);
        if (object != null && object.size() > 0) {
            List<DataBaseObject> targer = (List<DataBaseObject>) (List<?>) ((Users) (object.get(0))).getWishLists();
            return JsonParser.parse(targer);
        }
        return new ResponseMessage("There was a problem with the user id").getResponseMessage();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addWishListRecord(WishList wishList) {
        manager.merge(wishList);
        return new ResponseMessage("Added").getResponseMessage();
    }

    @DELETE
    @Path("/{wishListId}")
    public String deleteWishListById(@PathParam("wishListId") int id) {
        List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, id), WishList.class);
        if (objects == null || objects.size() == 0)
            return new ResponseMessage("There's a problem with the wishList id").getResponseMessage();
        WishList wishList = (WishList) objects.get(0);
        manager.delete(wishList);

        return new ResponseMessage("item "+wishList.getItem().getName()+" has been deleted from your wishlist").getResponseMessage();
    }
}
