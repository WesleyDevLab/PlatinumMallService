package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.*;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by MontaserQasem on 11/23/16.
 */
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public String getAllOrders() {
        List<DataBaseObject> objects = manager.find(null, Orders.class);
        return JsonParser.parse(objects);
    }

    @GET
    @Path("/{orderId}")
    public String getOrderByOrderId(@PathParam("orderId") long id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(rule, Orders.class);
        if (objects != null && objects.size() > 0)
            return JsonParser.parse(objects);
        return new ResponseMessage("There was a problem with the order id").getResponseMessage();
    }

    @GET
    @Path("/{operation}/{id}")
    public String getOrdersByOperation(@PathParam("operation") String operation, @PathParam("id") int storeId) {
        if (operation.equalsIgnoreCase("getordersbystoreid")) {//gets new requests
            List<Orders> result = (List<Orders>) (List<?>) manager.find(new RuleObject("status", HibernateUtil.EQUAL, 1), Orders.class);
            List<Orders> target = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                Orders order = result.get(i);
                for (OrderItem orderItem : order.getOrderItems()) {
                    Items item = orderItem.getItem();
                    if (ItemsService.checkItemInStore(item, storeId))
                        target.add(order);
                }

            }
            Collections.sort(result, new Comparator<Orders>() {
                @Override
                public int compare(Orders o1, Orders o2) {
                    return (int) (o1.getId() - o2.getId());
                }
            });
            List<DataBaseObject> parsed = (List<DataBaseObject>) (List<?>) target;
            return JsonParser.parse(parsed);
        }

        return null;
    }


    @POST
    @Path("/{userId}")
    public String getOrdersByUserId(@PathParam("userId") int userId) {
        List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, userId), Users.class);
        if (objects != null && objects.size() > 0) {
            List<DataBaseObject> target = new ArrayList<>();
            Users user = (Users) objects.get(0);
            for (Orders order : user.getOrders())
                target.add((DataBaseObject) order);
            return JsonParser.parse(target);
        }
        return new ResponseMessage("There is a problem with the user id or the user does not have any orders in his inventory").getResponseMessage();
    }

    @POST
    @Path("/{email}/{password}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addOrder(Orders order, @PathParam("email") String email, @PathParam("password") String password) {
        UsersAPI usersAPI = new UsersAPI();
        if (usersAPI.doLoginUser(email, password) == -1)
            return new ResponseMessage("Password or Email is incorrect").getResponseMessage();

        manager.merge(order);
        for (OrderItem orderItem : order.getOrderItems())
            manager.merge(orderItem);

        return new ResponseMessage("Your Request has been sent").getResponseMessage();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateOrder(Orders order) {
        manager.update(order);
        return new ResponseMessage("updated").getResponseMessage();
    }

    @PUT
    @Path("{operation}/{orderId}/{adminId}")
    public String updateOrderState(@PathParam("operation") String operation, @PathParam("orderId") long id, @PathParam("adminId") int adminId) {

        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> object = manager.find(ruleObject, Orders.class);
        if (object == null || object.size() == 0)
            return new ResponseMessage("There's a problem with the orderId").getResponseMessage();
        Orders order = (Orders) object.get(0);

        if (operation.equalsIgnoreCase("accept")) {
            order.setStatus(3);
            order.setDeliveryDate(Calendar.getInstance().getTimeInMillis() + "");
            manager.update(order);
            migrateOrderToLog(order, adminId);
            return new ResponseMessage("Request Accepted").getResponseMessage();
        }
        if (operation.equalsIgnoreCase("reject")) {
            order.setStatus(2);
            manager.update(order);
            migrateOrderToLog(order, adminId);
            return new ResponseMessage("Request Rejected").getResponseMessage();
        }
        return null;
    }

    public void migrateOrderToLog(Orders order, int adminId) {
        Log log = new Log();
        log.setOrder(order);
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, adminId);
        List<DataBaseObject> object = manager.find(rule, Admins.class);
        Admins admin = (Admins) object.get(0);
        log.setAdminName(admin.getUsername());
        manager.merge(log);
    }

    @DELETE
    @Path("/{orderId}")
    public String deleteOrder(@PathParam("orderId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> object = manager.find(rule, Orders.class);
        if (object == null || object.size() == 0)
            return new ResponseMessage("there was a problem with ther orderId").getResponseMessage();
        Orders order = (Orders) object.get(0);
        manager.delete(order);
        return new ResponseMessage("Deleted").getResponseMessage();
    }

}
