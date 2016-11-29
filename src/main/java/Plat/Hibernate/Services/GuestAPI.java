package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Guests;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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
        return guests;
    }

    @GET
    @Path("/{guestId}")
    public Guests getlGuestById(@PathParam("guestId") int id) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> objects = manager.find(rule, Guests.class);
        if (objects != null && objects.size() != 0) {
            objects = EntityCleaner.clean(objects, Guests.class);
            Guests guest = (Guests) objects.get(0);
            return guest;
        }
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addGuest(Guests guest) {
        manager.save(guest);
        return "Guest added successfully";
    }
}
