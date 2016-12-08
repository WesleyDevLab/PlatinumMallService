package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Brand;
import Plat.Hibernate.Entities.Categories;
import Plat.Hibernate.Entities.Store;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

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
    @Path("/{operation}/{storeId}")
    public List<Brand> getBrandsByOperation(@PathParam("operation") String operation, @PathParam("storeId") int storeId) {
        if (operation.equalsIgnoreCase("getBrandsBystoreId")) {
            List<Brand> result = new ArrayList<>();
            Set<Brand> resultSet = new HashSet<>();
            CategoriesAPI categoriesAPI = new CategoriesAPI();
            Iterator catIt = categoriesAPI.getAllCategories().iterator();
            while (catIt.hasNext()) {
                Categories category = (Categories) catIt.next();
                if (category.getStore().getId() == storeId) {
                    if (category.getBrands() != null) {
                        Iterator brandIt = category.getBrands().iterator();
                        while (brandIt.hasNext())
                            resultSet.add(((Brand) brandIt.next()));
                    }
                }
            }
            result.addAll(resultSet);
            Collections.sort(result, new Comparator<Brand>() {
                @Override
                public int compare(Brand o1, Brand o2) {
                    return o1.getId() - o2.getId();
                }
            });
            return result;
        }

        return null;
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
