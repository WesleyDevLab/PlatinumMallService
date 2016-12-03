package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Orders;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MontaserQasem on 11/23/16.
 */
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<Orders> getAllOrders() {
        List<DataBaseObject> objects = manager.find(null, Orders.class);
        List<Orders> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Orders.class);
            for (int i = 0; i < objects.size(); i++) {
                Orders order = (Orders) objects.get(i);
                result.add(order);
            }
        }
        return result;
    }

    @GET
    @Path("/{orderId}")
    public Orders getOrderByOrderId(@PathParam("orderId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(rule, Orders.class);
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Orders.class);
            Orders order = (Orders) objects.get(0);
            return order;
        }
        return null;
    }

    @POST
    @Path("/{userId}")
    public List<Orders> getOrdersByUserId(@PathParam("userId") int userId) {
        List<Orders> result = new ArrayList<>();
        List<Orders> objects = getAllOrders();
        for (int i = 0; i < objects.size(); i++) {
            Orders order = objects.get(i);
            if (order.getUser() != null) {
                if (order.getUser().getId() == userId)
                    result.add(order);
            }
        }
        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addOrder(Orders order) {
        manager.merge(order);
        return "Added";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateOrder(Orders order) {
        manager.update(order);
        return "updated";
    }

    @DELETE
    @Path("/{orderId}")
    public String deleteOrder(@PathParam("orderId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> object = manager.find(rule, Orders.class);
        if (object == null || object.size() == 0) return "orderId not found";
        Orders order = (Orders) object.get(0);
        manager.delete(order);
        return "Deleted";
    }

}
