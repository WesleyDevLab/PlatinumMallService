package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Cart;
import Plat.Hibernate.Entities.Users;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by MontaserQasem on 11/22/16.
 */
@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class CartAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<Cart> getAllCarts() {
        List<DataBaseObject> objects = manager.find(null, Cart.class);
        List<Cart> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Cart.class);
            for (int i = 0; i < objects.size(); i++) {
                Cart node = (Cart) objects.get(i);
                result.add(node);
            }
        }
        return result;
    }

    @GET
    @Path("/{userId}")
    public List<Cart> getCartsByUserId(@PathParam("userId") int userId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, userId);
        List<DataBaseObject> objects = manager.find(rule, Users.class);
        List<Cart> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Users.class);
            Users user = (Users) objects.get(0);
            Iterator it = user.getCart().iterator();
            while (it.hasNext()) {
                Cart cart = (Cart) it.next();
                result.add(cart);
            }
        }
        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addCartRecord(Cart cart) {
        manager.merge(cart);
        return "Cart recorde added successfully";
    }

    @DELETE
    @Path("/{cartId}")
    public String deleteCartRecord(@PathParam("cartId") int cartId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, cartId);
        List<DataBaseObject> object = manager.find(rule, Cart.class);
        if (object == null || object.size() == 0) return "There's a problem with the cart id";
        Cart cart = (Cart) object.get(0);
        manager.delete(cart);
        return "Cart record deleted";
    }
}
