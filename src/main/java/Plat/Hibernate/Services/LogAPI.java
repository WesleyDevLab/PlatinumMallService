package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Log;
import Plat.Hibernate.Util.DataBaseManager;
import Plat.Hibernate.Util.DataBaseObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by MontaserQasem on 12/9/16.
 */
@Path("/logs")
public class LogAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<Log> getAllLogs() {
        List<DataBaseObject> objects = manager.find(null, Log.class);
      //  objects = EntityCleaner.clean(objects, Log.class);
        List<Log> result = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++)
            result.add((Log) objects.get(i));
        Set<Log> hs = new HashSet<>();
        hs.addAll(result);
        result.clear();
        result.addAll(hs);
        Collections.sort(result, new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return (int) (o2.getOrder().getId() - o1.getOrder().getId());
            }
        });
        return result;
    }

    @GET
    @Path("{storeId}")
    public List<Log> getLogsByStoreId(@PathParam("storeId") int storeId) {
        List<Log> objects = getAllLogs();
        List<Log> result = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++)
           // if (objects.get(i).getAdmin().getStore().getId() == storeId)
                result.add(objects.get(i));
        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addLogRecord(Log log) {
        manager.merge(log);
        return "Added";
    }
}
