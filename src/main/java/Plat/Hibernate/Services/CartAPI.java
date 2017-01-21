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
    public String getAllCarts() {
        List<DataBaseObject> objects = manager.find(null, Cart.class);
        objects = EntityInitializer.init(objects, Cart.class);
        return JsonParser.parse(objects);
    }

    @GET
    @Path("/{userId}")
    public String getCartsByUserId(@PathParam("userId") int userId) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, userId);
        List<DataBaseObject> objects = manager.find(ruleObject, Users.class);
        if (objects != null && objects.size() > 0) {
            Users user = (Users) objects.get(0);
            user = (Users) manager.initialize(user, "cart");
            List<DataBaseObject> target = new ArrayList<>();
            for (Cart cart : user.getCart())
                target.add((DataBaseObject) cart);
            return JsonParser.parse(EntityInitializer.init(target, Cart.class));
        }
        return new ResponseMessage("There was an error with the user id").getResponseMessage();
    }

    @PUT
    @Path("/{cartId}/{quantity}")
    public String updateCartQuantity(@PathParam("cartId") int id, @PathParam("quantity") String quantity) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> object = manager.find(rule, Cart.class);
        Cart cart = (Cart) object.get(0);
        cart.setQuantity(Integer.parseInt(quantity));
        manager.update(cart);
        return new ResponseMessage("your cart has been updated").getResponseMessage();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addCartRecord(Cart cart) {
        List<RuleObject> rules = new ArrayList<>();
        rules.add(new RuleObject("user", HibernateUtil.EQUAL, cart.getUser()));
        rules.add(new RuleObject("item", HibernateUtil.EQUAL, cart.getItem()));
        List<DataBaseObject> objects = manager.findAll(rules, Cart.class);
        if (objects != null && objects.size() > 0)
            return new ResponseMessage("This item is already in your cart if you want to change the quantity go to your cart page").getResponseMessage();
        manager.merge(cart);
        return new ResponseMessage("This item has been added to your cart").getResponseMessage();
    }

    @DELETE
    @Path("/{userId}")
    public String deleteAllCartItems(@PathParam("userId") int userId) {
        List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, userId), Users.class);
        if (objects != null && objects.size() > 0) {
            Users user = (Users) objects.get(0);
            user = (Users) manager.initialize(user, "cart");
            List<DataBaseObject> targer = new ArrayList<>();
            for (Cart cart : user.getCart())
                targer.add((DataBaseObject) cart);
            manager.deleteList(targer);

            return new ResponseMessage("your cart has been cleared").getResponseMessage();
        }
        return new ResponseMessage("There was an error with the user id").getResponseMessage();
    }

    @DELETE
    @Path("/{userId}/{cartId}")
    public String deleteItemFromUserCart(@PathParam("userId") int userId, @PathParam("cartId") int cartId) {
        List<DataBaseObject> validUser = manager.find(new RuleObject("id", HibernateUtil.EQUAL, userId), Users.class);
        if (validUser != null && validUser.size() > 0) {
            List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, cartId), Cart.class);
            if (objects != null && objects.size() > 0) {
                manager.deleteList(objects);
                return new ResponseMessage("Item has been deleted from your cart").getResponseMessage();
            }
            return new ResponseMessage("There was an error with the cart id").getResponseMessage();
        }
        return new ResponseMessage("There was an error with the user id").getResponseMessage();
    }
}
