package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Brand;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MontaserQasem on 11/22/16.
 */
@Path("/brands")
@Produces(MediaType.APPLICATION_JSON)
public class BrandsAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<Brand> getAllBrands() {
        List<DataBaseObject> objects = manager.find(null, Brand.class);
        List<Brand> brands = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Brand.class);
            for (int i = 0; i < objects.size(); i++) {
                Brand brand = (Brand) objects.get(i);
                brands.add(brand);
            }
        }
        return brands;
    }

    @GET
    @Path("/{brandId}")
    public Brand getBrandById(@PathParam("brandId") int brandId) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, brandId);
        List<DataBaseObject> objects = manager.find(ruleObject, Brand.class);
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Brand.class);
            Brand brand = (Brand) objects.get(0);
            return brand;
        }
        return null;
    }

    @POST
    @Path("/{brandName}")
    public List<Brand> getBrandsByName(@PathParam("brandName") String brandName) {
        RuleObject ruleObject = new RuleObject("name", HibernateUtil.LIKE, brandName);
        List<DataBaseObject> objects = manager.find(ruleObject, Brand.class);
        List<Brand> brands = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Brand.class);
            for (int i = 0; i < objects.size(); i++) {
                Brand node = (Brand) objects.get(i);
                brands.add(node);
            }
        }
        return brands;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addBrand(Brand brand) {
        manager.merge(brand);
        return "Brand (" + brand.getName() + ") has been added successfully";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateBrand(Brand brand) {
        manager.update(brand);
        return "Brand (" + brand.getName() + ") has been updated";
    }

    @DELETE
    @Path("/{brandId}")
    public String deleteBrand(@PathParam("brandId") int brandId) {
        RuleObject ruleObject = new RuleObject("id", HibernateUtil.EQUAL, brandId);
        List<DataBaseObject> objects = manager.find(ruleObject, Brand.class);
        if (objects == null || objects.size() == 0) return "There's a problem with the brand id";
        Brand brand = (Brand) objects.get(0);
        manager.delete(brand);
        return "Brand (" + brand.getName() + ") has been deleted";
    }

}