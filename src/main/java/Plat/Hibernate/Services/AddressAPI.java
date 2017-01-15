package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Address;
import Plat.Hibernate.Entities.Admins;
import Plat.Hibernate.Entities.Store;
import Plat.Hibernate.Util.*;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by MontaserQasem on 11/19/16.
 */
@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
public class AddressAPI {
    DataBaseManager manager = DataBaseManager.getInstance();
    ObjectMapper mapper = new ObjectMapper();


    @GET
    public String getAllAddresses() {
        List<DataBaseObject> objects = manager.find(null, Address.class);
        return JsonParser.parse(objects);
    }

    @GET
    @Path("/{storeId}")
    public List<Address> getAddressesByStoreId(@PathParam("storeId") int storeId) {
//        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, storeId);
//        List<DataBaseObject> objects = manager.find(rule, Store.class);
//        if (objects != null && objects.size() > 0) {
//            //objects = EntityCleaner.clean(objects, Store.class);
//            Store store = (Store) objects.get(0);
//            Set<Address> res = store.getAddresses();
//            List<Address> result = new ArrayList<>();
//            Iterator it = res.iterator();
//            while (it.hasNext())
//                result.add(((Address) it.next()));
//            return result;
//        }
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addAddressToStore(Address address) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, address.getStore().getId());
        List<DataBaseObject> objects = manager.find(ruleObject, Store.class);
        if (objects == null || objects.size() == 0) return "There's a problem with the store id";
        Store store = (Store) objects.get(0);
        manager.merge(address);
        return "Address has been added to store " + store.getName();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateAddress(Address address) {
        manager.update(address);
        return "Address has been updated successfully";
    }

    @DELETE
    @Path("/{addressId}")
    public String deleteAddress(@PathParam("addressId") int addressId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, addressId);
        List<DataBaseObject> objects = manager.find(rule, Address.class);
        if (objects == null || objects.size() == 0) return "Address id is not exist";
        Address address = (Address) objects.get(0);
        manager.delete(address);
        return "Address has been deleted Successfully";
    }
}
