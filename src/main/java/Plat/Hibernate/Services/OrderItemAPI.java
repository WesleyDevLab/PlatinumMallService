package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.OrderItem;
import Plat.Hibernate.Entities.Orders;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
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
    public String getAllOrderItems() throws IOException {
        List<DataBaseObject> objects = manager.find(null, OrderItem.class);
        return JsonParser.parse(objects);
    }

    @GET
    @Path("/{orderItemId}")
    public String getOrderItemById(@PathParam("orderItemId") int id) throws IOException {
        List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, id), OrderItem.class);
        if (objects != null && objects.size() > 0)
            return JsonParser.parse(objects.get(0));
        return new ResponseMessage("There was a problem in the order item id").getResponseMessage();
    }

    @POST
    @Path("/{orderId}")
    public String getOrderItemsByOrderId(@PathParam("orderId") long id) throws IOException {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(ruleObject, Orders.class);
        if (objects != null && objects.size() > 0) {
            List<DataBaseObject> target = new ArrayList<>();
            Orders order = (Orders) objects.get(0);
            for (OrderItem orderItem : order.getOrderItems())
                target.add((DataBaseObject) orderItem);
            return JsonParser.parse(target);
        }
        return new ResponseMessage("There was a problem with the order id").getResponseMessage();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addOrderItemRecord(OrderItem orderItem) {
        manager.merge(orderItem);
        return new ResponseMessage("Added").getResponseMessage();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateOrderItemRecord(OrderItem orderItem) {
        manager.update(orderItem);
        return new ResponseMessage("Updated").getResponseMessage();
    }

    @DELETE
    @Path("/{orderItemId}")
    public String deleteOrderItemRecord(@PathParam("orderItemId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(rule, OrderItem.class);
        if (objects == null || objects.size() == 0)
            return new ResponseMessage("There was a problem with the OrderItem id").getResponseMessage();
        OrderItem orderItem = (OrderItem) objects.get(0);
        manager.delete(orderItem);
        return new ResponseMessage("Deleted").getResponseMessage();
    }

}
