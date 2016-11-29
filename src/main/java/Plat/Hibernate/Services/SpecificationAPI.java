package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Items;
import Plat.Hibernate.Entities.Specifications;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    public List<Specifications> getAllSpecifications() {
        List<DataBaseObject> objects = manager.find(null, Specifications.class);
        List<Specifications> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Specifications.class);
            for (int i = 0; i < objects.size(); i++) {
                Specifications node = (Specifications) objects.get(i);
                result.add(node);
            }
        }
        return result;
    }

    @GET
    @Path("/{itemId}")
    public List<Specifications> getSpecificationsByItemId(@PathParam("itemId") int itemId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, itemId);
        List<DataBaseObject> object = manager.find(rule, Items.class);
        List<Specifications> result = new ArrayList<>();
        if (object != null && object.size() > 0) {
            object = EntityCleaner.clean(object, Items.class);
            Items item = (Items) object.get(0);
            Iterator it = item.getSpecifications().iterator();
            while (it.hasNext()) {
                Specifications node = (Specifications) it.next();
                result.add(node);
            }
        }

        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addSpecification(Specifications specification) {
        List<DataBaseObject> objects = null;
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, specification.getItem().getId());
        objects = manager.find(rule, Items.class);
        if (objects == null || objects.size() == 0) return "There's a problem with the item id";
        Iterator it = ((Items) objects.get(0)).getSpecifications().iterator();
        while (it.hasNext()) {
            Specifications node = (Specifications) it.next();
            if (node.getSpecificationKey().equalsIgnoreCase(specification.getSpecificationKey()))
                return "This specificationKey already exist in this item";
        }
        manager.merge(specification);
        return "Specification has been added";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateSpecification(Specifications specification) {
        manager.update(specification);
        return "Specification has been updated";
    }

    @DELETE
    @Path("/{specificationId}")
    public String deleteSpecificationById(@PathParam("specificationId") int id) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, id);
        List<DataBaseObject> object = manager.find(ruleObject, Specifications.class);
        if (object == null || object.size() == 0) return "There's a problem with the specification id";
        Specifications specification = (Specifications) object.get(0);
        manager.delete(specification);
        return "Specification has been deleted";
    }

}
