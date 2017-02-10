package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Log;
import Plat.Hibernate.Entities.OrderItem;
import Plat.Hibernate.Entities.Orders;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.*;

/**
 * Created by MontaserQasem on 12/9/16.
 */
@Path("/logs")
public class LogAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public String getAllLogs() throws IOException {
        List<Log> result = (List<Log>) (List<?>) manager.find(null, Log.class);
        Collections.sort(result, new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return (int) (o2.getOrder().getId() - o1.getOrder().getId());
            }
        });
        List<DataBaseObject> target = (List<DataBaseObject>) (List<?>) result;
        return JsonParser.parse(target);
    }

    @GET
    @Path("{storeId}")
    public String getLogsByStoreId(@PathParam("storeId") int storeId) throws IOException {
        List<Log> result = (List<Log>) (List<?>) manager.find(null, Log.class);
        List<DataBaseObject> target = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            Log log = result.get(i);
            Orders order = log.getOrder();
            OrderItem orderItem = order.getOrderItems().get(0);
            if (ItemsService.checkItemInStore(orderItem.getItem(), storeId))
                target.add((DataBaseObject) log);
        }
        return JsonParser.parse(target);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addLogRecord(Log log) {
        manager.merge(log);
        return new ResponseMessage("Added").getResponseMessage();
    }
}
