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
        List<Cart> result = new ArrayList<>();
        List<Cart> objects = getAllCarts();
        for (int i = 0; i < objects.size(); i++)
            if (objects.get(i).getUser().getId() == userId)
                result.add(objects.get(i));

        return result;
    }

    @POST
    @Path("/{cartId}/{quantity}")
    public String updateCartQuantity(@PathParam("cartId") int id, @PathParam("quantity") String quantity) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> object = manager.find(rule, Cart.class);
        Cart cart = (Cart) object.get(0);
        cart.setQuantity(Integer.parseInt(quantity));
        manager.update(cart);
        return "Cart updated";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addCartRecord(Cart cart) {
        manager.merge(cart);
        return "Cart recorde added successfully";
    }

    @DELETE
    @Path("/{userId}")
    public String deleteCartRecord(@PathParam("userId") int userId) {
        List<Cart> carts = getAllCarts();
        List<DataBaseObject> cut = new ArrayList<>();
        for (int i = 0; i < carts.size(); i++)
            if (carts.get(i).getUser().getId() == userId)
                cut.add(carts.get(i));
        manager.deleteList(cut);
        return "ok";
    }
}
