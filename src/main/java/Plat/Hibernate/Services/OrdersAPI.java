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
    public List<Orders> getAllOrders() {
        List<DataBaseObject> objects = manager.find(null, Orders.class);
        List<Orders> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
           // objects = EntityCleaner.clean(objects, Orders.class);
            for (int i = 0; i < objects.size(); i++) {
                Orders order = (Orders) objects.get(i);
                result.add(order);
            }
        }
        return result;
    }

    @GET
    @Path("/{orderId}")
    public Orders getOrderByOrderId(@PathParam("orderId") long id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(rule, Orders.class);
        if (objects != null && objects.size() > 0) {
          //  objects = EntityCleaner.clean(objects, Orders.class);
            Orders order = (Orders) objects.get(0);
            return order;
        }
        return null;
    }

    @GET
    @Path("/{operation}/{id}")
    public List<Orders> getOrdersByOperation(@PathParam("operation") String operation, @PathParam("id") int id) {
//        if (operation.equalsIgnoreCase("getordersbystoreid")) {//gets new requests
//            List<Orders> orders = getAllOrders();
//            List<Orders> result = new ArrayList<>();
//            for (int i = 0; i < orders.size(); i++) {
//            //    Set<OrderItem> orderItems = orders.get(i).getOrderItems();
//                //Iterator it = orderItems.iterator();
//                boolean flag = false;
//                while (it.hasNext()) {
//                    OrderItem orderitem = (OrderItem) it.next();
//                    int itemId = orderitem.getItem().getId();
//                    if (getItemsStoreId(itemId) == id) {// id here is equal to storeId
//                        flag = true;
//                        break;
//                    }
//                }
//                if (flag && orders.get(i).getStatus() == 1)
//                    result.add(orders.get(i));
//            }
//            Collections.sort(result, new Comparator<Orders>() {
//                @Override
//                public int compare(Orders o1, Orders o2) {
//                    return (int) (o1.getId() - o2.getId());
//                }
//            });
//            return result;
//        }

        return null;
    }

    public int getItemsStoreId(int itemId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, itemId);
        List<DataBaseObject> object = manager.find(rule, Items.class);
        if (object == null || object.size() == 0) return -1;
        Items item = (Items) object.get(0);
        int catId = item.getCategory().getId();
        RuleObject rule2 = new RuleObject("id", HibernateUtil.EQUAL, catId);
        List<DataBaseObject> object2 = manager.find(rule2, Categories.class);
        if (object2 == null || object2.size() == 0) return -1;
        Categories category = (Categories) object2.get(0);
        return category.getStore().getId();
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
    @Path("/{email}/{password}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addOrder(Orders order, @PathParam("email") String email, @PathParam("password") String password) {
        UsersAPI usersAPI = new UsersAPI();
        if (usersAPI.loginUser(email, password) == "-1")
            return "Passoword or Email is incorrect";
        manager.merge(order);
        Iterator it = order.getOrderItems().iterator();
        while (it.hasNext()) {
            OrderItem orderItem = (OrderItem) it.next();
            orderItem.setOrder(order);
            manager.merge(orderItem);
        }
        return "Your Request has been sent";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateOrder(Orders order) {
        manager.update(order);
        return "updated";
    }

    @PUT
    @Path("{operation}/{orderId}/{adminId}")
    public String updateOrderState(@PathParam("operation") String operation, @PathParam("orderId") long id, @PathParam("adminId") int adminId) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> object = manager.find(ruleObject, Orders.class);
        if (object == null || object.size() == 0) return "There's a problem with the orderId";
        Orders order = (Orders) object.get(0);
        if (operation.equalsIgnoreCase("accept")) {
            order.setStatus(3);
            order.setDeliveryDate(Calendar.getInstance().getTimeInMillis() + "");
            manager.update(order);
            migrateOrderToLog(order, adminId);
            return "Request Accepted";
        }
        if (operation.equalsIgnoreCase("reject")) {
            order.setStatus(2);
            manager.update(order);
            migrateOrderToLog(order, adminId);
            return "Request Rejected";
        }
        return null;
    }

    public void migrateOrderToLog(Orders order, int adminId) {
        Log log = new Log();
        log.setOrder(order);
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, adminId);
        List<DataBaseObject> object = manager.find(rule, Admins.class);
        Admins admin = (Admins) object.get(0);
        //log.setAdmin(admin);
        manager.merge(log);
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
