package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Guests;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by MontaserQasem on 11/22/16.
 */
@Path("/guests")
@Produces(MediaType.APPLICATION_JSON)
public class GuestAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<Guests> getlAllGuests() {
        List<DataBaseObject> objects = manager.find(null, Guests.class);
        List<Guests> guests = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Guests.class);
            for (int i = 0; i < objects.size(); i++) {
                Guests node = (Guests) objects.get(i);
                guests.add(node);
            }
        }
        Collections.sort(guests, new Comparator<Guests>() {
            @Override
            public int compare(Guests o1, Guests o2) {
                return o2.getId() - o1.getId();
            }
        });
        return guests;
    }

    @GET
    @Path("/{getMax}")
    public String getlGuestById(@PathParam("getMax") String getMax) {
        if (getMax.equalsIgnoreCase("getmax")) {
            List<Guests> guests = getlAllGuests();
            return guests.get(0).getId() + "";
        }
        return -1 + "";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addGuest(Guests guest) {
        manager.save(guest);
        return "Guest added successfully";
    }
}
