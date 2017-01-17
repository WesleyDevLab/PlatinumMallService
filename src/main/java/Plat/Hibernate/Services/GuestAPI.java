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
    public String getlAllGuests() {
        List<DataBaseObject> objects = manager.find(null, Guests.class);
        return JsonParser.parse(EntityInitializer.init(objects, Guests.class));
    }

    @GET
    @Path("/{getMax}")
    public String getGuestNextValue(@PathParam("getMax") String getMax) {
        if (getMax.equalsIgnoreCase("getMax")) {
            int max = 0;
            List<DataBaseObject> objects = manager.find(null, Guests.class);
            if (objects != null && objects.size() > 0) {
                for (int i = 0; i < objects.size(); i++) {
                    Guests guest = (Guests) objects.get(i);
                    max = (guest.getId() > max) ? guest.getId() : max;
                }
            }
            return ResponseMessage.createSimpleObject("nextValue", max + "");
        }
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addGuest(Guests guest) {
        manager.save(guest);
        return new ResponseMessage("Guest added successfully").getResponseMessage();
    }
}
