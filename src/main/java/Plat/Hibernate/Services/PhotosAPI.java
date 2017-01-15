package Plat.Hibernate.Services;


import Plat.Hibernate.Entities.Items;
import Plat.Hibernate.Entities.Photos;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by MontaserQasem on 11/23/16.
 */

@Path("/photos")
@Produces(MediaType.APPLICATION_JSON)
public class PhotosAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public List<Photos> getAllPhotos() {
        List<DataBaseObject> objects = manager.find(null, Photos.class);
        List<Photos> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
           // objects = EntityCleaner.clean(objects, Photos.class);
            for (int i = 0; i < objects.size(); i++) {
                Photos photo = (Photos) objects.get(i);
                result.add(photo);
            }
        }
        return result;
    }

    @GET
    @Path("/{itemId}")
    public List<Photos> getPhotosByItemId(@PathParam("itemId") int itemId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, itemId);
        List<DataBaseObject> object = manager.find(rule, Items.class);
        List<Photos> result = new ArrayList<>();
        if (object != null && object.size() > 0) {
           // object = EntityCleaner.clean(object, Items.class);
            Items item = (Items) object.get(0);
            Iterator it = item.getPhotos().iterator();
            if (it != null) {
                while (it.hasNext()) {
                    Photos node = (Photos) it.next();
                    result.add(node);
                }
            }
        }
        return result;
    }

    @POST
    public String getPhotoNextValue() {
        List<Photos> photos = getAllPhotos();
        int maxi = -1;
        if (photos != null) {
            for (int i = 0; i < photos.size(); i++) {
                Photos photo = photos.get(i);
                String value = photo.getPath(), newValue = "";
                for (int j = 0; j < value.length(); j++) {
                    if (value.charAt(j) == '.') break;
                    else newValue += value.charAt(j);
                }
                int s = Integer.parseInt(newValue);
                maxi = s > maxi ? s : maxi;
            }
        }
        maxi++;
        return maxi + "";
    }

    @POST
    @Path("/{itemId}/{photoPath}")
    public String addNewItemPhoto(@PathParam("itemId") int itemId, @PathParam("photoPath") String photoPath) {
        Photos photo = new Photos();
        photo.setPath(photoPath);
        photo.setPrimary(false);
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, itemId);
        List<DataBaseObject> object = manager.find(rule, Items.class);
        if (object == null || object.size() == 0) return "There's a problem with the item id";
        //object = EntityCleaner.clean(object, Items.class);
        Items item = (Items) object.get(0);
        item.getPhotos().add(photo);
        photo.setItem(item);
        manager.merge(photo);
        return "1";
    }

    @PUT
    @Path("/{itemId}/{photoId}")
    public String makePhotoPrimary(@PathParam("itemId") int itemId, @PathParam("photoId") int photoId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, itemId);
        List<DataBaseObject> object = manager.find(rule, Items.class);
        if (object == null || object.size() == 0) return "There's a problem with the item id";
        Items item = (Items) object.get(0);
        Iterator it = item.getPhotos().iterator();
        Set<Photos> newPhoto = new HashSet<>();
        boolean flag = false;
        while (it.hasNext()) {
            Photos node = (Photos) it.next();
            if (node.getId() == photoId) {
                flag = true;
            }
            newPhoto.add(node);
        }
        if (!flag) return "Photo id not found";
        it = newPhoto.iterator();
        List<DataBaseObject> newListPhoto = new ArrayList<>();
        while (it.hasNext()) {
            Photos node = (Photos) it.next();
            if (node.getId() != photoId)
                node.setPrimary(false);
            else node.setPrimary(true);
            newListPhoto.add(node);
        }
        //item.setPhotos(newPhoto);
        manager.updateList(newListPhoto);
        manager.update(item);
        return "photo primary updated";
    }

    @DELETE
    @Path("/{photoId}")
    public String deletePhoto(@PathParam("photoId") int photoId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, photoId);
        List<DataBaseObject> object = manager.find(rule, Photos.class);
        if (object == null || object.size() == 0) return "photo id not found";
        Photos photo = (Photos) object.get(0);
        manager.delete(photo);
        return "Photo has been deleted";
    }

}