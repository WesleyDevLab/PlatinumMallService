package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.OrderItem;
import Plat.Hibernate.Entities.Orders;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by MontaserQasem on 11/23/16.
 */
@Path("/orderItems")
@Produces(MediaType.APPLICATION_JSON)
public class OrderItemAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<OrderItem> getAllOrderItems() {
        List<DataBaseObject> objects = manager.find(null, OrderItem.class);
        List<OrderItem> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, OrderItem.class);
            for (int i = 0; i < objects.size(); i++) {
                OrderItem node = (OrderItem) objects.get(i);
                result.add(node);
            }
        }
        return result;
    }

    @GET
    @Path("/{orderItemId}")
    public OrderItem getOrderItemById(@PathParam("orderItemId") int id) {
        List<OrderItem> objects = getAllOrderItems();
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getId() == id) return objects.get(i);
        }
        return null;
    }

    @POST
    @Path("/{orderId}")
    public List<OrderItem> getOrderItemsByOrderId(@PathParam("orderId") int id) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(ruleObject, Orders.class);
        List<OrderItem> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Orders.class);
            Orders order = (Orders) objects.get(0);
            Iterator it = order.getOrderItems().iterator();
            while (it.hasNext()) {
                OrderItem node = (OrderItem) it.next();
                result.add(node);
            }
        }
        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addOrderItemRecord(OrderItem orderItem) {
        manager.merge(orderItem);
        return "Added";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateOrderItemRecord(OrderItem orderItem) {
        manager.update(orderItem);
        return "Updated";
    }

    @DELETE
    @Path("/{orderItemId}")
    public String deleteOrderItemRecord(@PathParam("orderItemId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(rule, OrderItem.class);
        if (objects == null || objects.size() == 0) return "OrderItem id not found";
        OrderItem orderItem = (OrderItem) objects.get(0);
        manager.delete(orderItem);
        return "Deleted";
    }

}
