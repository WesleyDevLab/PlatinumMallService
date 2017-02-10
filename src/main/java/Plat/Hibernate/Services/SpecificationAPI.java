package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Items;
import Plat.Hibernate.Entities.Specifications;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by MontaserQasem on 11/22/16.
 */
@Path("/specifications")
@Produces(MediaType.APPLICATION_JSON)
public class SpecificationAPI {

    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public String getAllSpecifications() throws IOException {
        List<DataBaseObject> objects = manager.find(null, Specifications.class);
        return JsonParser.parse(objects);
    }

    @GET
    @Path("/{itemId}")
    public String getSpecificationsByItemId(@PathParam("itemId") int itemId) throws IOException {
        List<DataBaseObject> object = manager.find(new RuleObject("id", HibernateUtil.EQUAL, itemId), Items.class);
        if (object != null && object.size() > 0) {
            Items item = (Items) object.get(0);
            List<DataBaseObject> target = new ArrayList<>();
            for (Specifications specification : item.getSpecifications())
                target.add((DataBaseObject) specification);
            return JsonParser.parse(target);
        }
        return new ResponseMessage("There was a problem with the item id").getResponseMessage();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addSpecification(Specifications specification) {
        List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, specification.getItem().getId()), Items.class);
        if (objects == null || objects.size() == 0)
            return new ResponseMessage("There's a problem with the item id").getResponseMessage();
        Items item = (Items) objects.get(0);
        for (Specifications node : item.getSpecifications())
            if (node.getSpecificationKey().equalsIgnoreCase(specification.getSpecificationKey()))
                return new ResponseMessage("This item already has this specification key").getResponseMessage();

        manager.merge(specification);
        return new ResponseMessage("Specification has been added").getResponseMessage();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateSpecification(Specifications specification) {
        manager.update(specification);
        return new ResponseMessage("Specification has been updated").getResponseMessage();
    }

    @DELETE
    @Path("/{specificationId}")
    public String deleteSpecificationById(@PathParam("specificationId") int id) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> object = manager.find(ruleObject, Specifications.class);
        if (object == null || object.size() == 0)
            return new ResponseMessage("There's a problem with the specification id").getResponseMessage();
        Specifications specification = (Specifications) object.get(0);
        manager.delete(specification);

        return new ResponseMessage("Specification has been deleted").getResponseMessage();
    }

}
