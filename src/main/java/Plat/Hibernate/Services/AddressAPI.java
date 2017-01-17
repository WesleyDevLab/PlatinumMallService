package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Address;
import Plat.Hibernate.Entities.Store;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MontaserQasem on 11/19/16.
 */
@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
public class AddressAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public String getAllAddresses() {
        List<DataBaseObject> objects = manager.find(null, Address.class);
        return JsonParser.parse(objects);
    }

    @GET
    @Path("/{storeId}")
    public String getAddressesByStoreId(@PathParam("storeId") int storeId) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, storeId);
        List<DataBaseObject> objects = manager.find(ruleObject, Store.class);
        if (objects != null && objects.size() > 0) {
            Store store = (Store) (EntityInitializer.init(objects, Store.class)).get(0);
            List<DataBaseObject> target = new ArrayList<>();
            for (int i = 0; i < store.getAddresses().size(); i++)
                target.add(store.getAddresses().get(i));
            return JsonParser.parse(target);
        }
        return "[ ]";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addAddressToStore(Address address) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, address.getStore().getId());
        List<DataBaseObject> objects = manager.find(ruleObject, Store.class);
        if (objects == null || objects.size() == 0) return "There's a problem with the store id";
        Store store = (Store) objects.get(0);
        manager.merge(address);
        return new ResponseMessage("Address has been added to store " + store.getName()).getResponseMessage();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateAddress(Address address) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, address.getId());
        List<DataBaseObject> object = EntityInitializer.init(manager.find(ruleObject, Address.class), Address.class);
        Address address2 = (Address) object.get(0);
        address.setStore(address2.getStore());
        manager.update(address);
        return new ResponseMessage("Address has been updated successfully").getResponseMessage();
    }

    @DELETE
    @Path("/{addressId}")
    public String deleteAddress(@PathParam("addressId") int addressId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, addressId);
        List<DataBaseObject> objects = manager.find(rule, Address.class);
        if (objects == null || objects.size() == 0) return "Address id is not exist";
        Address address = (Address) objects.get(0);
        manager.delete(address);
        return new ResponseMessage("Address has been deleted Successfully").getResponseMessage();
    }
}
