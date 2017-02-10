package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Brand;
import Plat.Hibernate.Entities.Categories;
import Plat.Hibernate.Entities.Store;
import Plat.Hibernate.Util.*;

import javax.json.Json;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by MontaserQasem on 11/22/16.
 */
@Path("/brands")
@Produces(MediaType.APPLICATION_JSON)
public class BrandsAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public String getAllBrands() throws IOException {
        List<DataBaseObject> objects = manager.find(null, Brand.class);
        return JsonParser.parse(EntityInitializer.init(objects, Brand.class));
    }

    @GET
    @Path("/{brandId}")
    public String getBrandById(@PathParam("brandId") int brandId) throws IOException {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, brandId);
        List<DataBaseObject> objects = manager.find(ruleObject, Brand.class);
        if (objects != null && objects.size() > 0) {
            objects = EntityInitializer.init(objects, Brand.class);
            return JsonParser.parse(objects.get(0));
        }
        return new ResponseMessage("There was an error with the id").getResponseMessage();
    }

    @POST
    @Path("/{brandName}")
    public String getBrandsByName(@PathParam("brandName") String brandName) throws IOException {
        RuleObject ruleObject = new RuleObject("name", HibernateUtil.LIKE, brandName);
        List<DataBaseObject> objects = manager.find(ruleObject, Brand.class);
        if (objects != null && objects.size() > 0) {
            objects = EntityInitializer.init(objects, Brand.class);
            return JsonParser.parse(objects);
        }
        return new ResponseMessage("No match found").getResponseMessage();
    }

    @POST
    @Path("/{operation}/{storeId}")
    public String getBrandsByOperation(@PathParam("operation") String operation, @PathParam("storeId") int storeId) throws IOException {
        if (operation.equalsIgnoreCase("getBrandsByStoreId")) {
            List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, storeId), Store.class);
            if (objects != null && objects.size() > 0) {
                List<Brand> brands = new ArrayList<>();
                Store store = (Store) objects.get(0);
                store = (Store) manager.initialize(store, "categories");
                for (Categories category : store.getCategories()) {
                    category = (Categories) manager.initialize(category, "brands");
                    for (Brand brand : category.getBrands()) {
                        boolean flag = false;
                        for (int i = 0; i < brands.size(); i++)
                            if (brands.get(i).getId() == brand.getId()) {
                                flag = true;
                                break;
                            }
                        if (!flag)
                            brands.add(brand);
                    }
                }
                List<DataBaseObject> target = new ArrayList<>();
                for (int i = 0; i < brands.size(); i++)
                    target.add((DataBaseObject) brands.get(i));

                return JsonParser.parse(EntityInitializer.init(target, Brand.class));
            }
            return new ResponseMessage("There was an error with the store id").getResponseMessage();
        }

        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addBrand(Brand brand) {
        manager.merge(brand);
        return new ResponseMessage("Brand " + brand.getName() + " has been added successfully").getResponseMessage();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateBrand(Brand brand) {
        manager.update(brand);
        return new ResponseMessage("Brand " + brand.getName() + " has been updated").getResponseMessage();
    }

    @DELETE
    @Path("/{brandId}")
    public String deleteBrand(@PathParam("brandId") int brandId) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, brandId);
        List<DataBaseObject> objects = manager.find(ruleObject, Brand.class);
        if (objects == null || objects.size() == 0)
            return new ResponseMessage("There's a problem with the brand id").getResponseMessage();
        Brand brand = (Brand) objects.get(0);
        manager.delete(brand);
        return new ResponseMessage("Brand " + brand.getName() + " has been deleted").getResponseMessage();
    }

}
