package Plat.Hibernate.Services;


import Plat.Hibernate.Entities.Items;
import Plat.Hibernate.Entities.Photos;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.*;

/**
 * Created by MontaserQasem on 11/23/16.
 */

@Path("/photos")
@Produces(MediaType.APPLICATION_JSON)
public class PhotosAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public String getAllPhotos() throws IOException {
        List<DataBaseObject> objects = manager.find(null, Photos.class);
        return JsonParser.parse(objects);
    }

    @GET
    @Path("/{itemId}")
    public String getPhotosByItemId(@PathParam("itemId") int itemId) throws IOException {
        List<DataBaseObject> object = manager.find(new RuleObject("id", HibernateUtil.EQUAL, itemId), Items.class);
        if (object != null && object.size() > 0) {
            Items item = (Items) object.get(0);
            List<DataBaseObject> target = new ArrayList<>();
            for (Photos photo : item.getPhotos())
                target.add((DataBaseObject) photo);
            return JsonParser.parse(target);
        }
        return new ResponseMessage("There was a problem with the item id").getResponseMessage();
    }

    @POST
    public String getPhotoNextValue() {
        List<Photos> photos = (List<Photos>) (List<?>) manager.find(null, Photos.class);
        int maxi = 0;
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
        maxi++;
        return ResponseMessage.createSimpleObject("nextValue", maxi + "");
    }

    @POST
    @Path("/{itemId}/{photoPath}")
    public String addNewItemPhoto(@PathParam("itemId") int itemId, @PathParam("photoPath") String photoPath) {
        Photos photo = new Photos();
        photo.setPath(photoPath);
        photo.setPrimary(false);
        List<DataBaseObject> object = manager.find(new RuleObject("id", HibernateUtil.EQUAL, itemId), Items.class);
        if (object == null || object.size() == 0)
            return new ResponseMessage("There's a problem with the item id").getResponseMessage();

        Items item = (Items) object.get(0);
        item.getPhotos().add(photo);
        photo.setItem(item);
        manager.merge(photo);
        return new ResponseMessage("1").getResponseMessage();
    }

    @PUT
    @Path("/{itemId}/{photoId}")
    public String makePhotoPrimary(@PathParam("itemId") int itemId, @PathParam("photoId") int photoId) {
        List<DataBaseObject> object = manager.find(new RuleObject("id", HibernateUtil.EQUAL, itemId), Items.class);
        if (object == null || object.size() == 0)
            return new ResponseMessage("There's a problem with the item id").getResponseMessage();

        Items item = (Items) object.get(0);
        List<DataBaseObject> newPhotos = new ArrayList<>();
        for (Photos photo : item.getPhotos()) {
            if (photo.getId() == photoId)
                photo.setPrimary(true);
            else photo.setPrimary(false);
            photo.setItem(item);
            newPhotos.add(photo);
        }
        manager.mergeList(newPhotos);

        return new ResponseMessage("photo primary updated").getResponseMessage();
    }

    @DELETE
    @Path("/{photoId}")
    public String deletePhoto(@PathParam("photoId") int photoId) {
        List<DataBaseObject> object = manager.find(new RuleObject("id", HibernateUtil.EQUAL, photoId), Photos.class);
        if (object == null || object.size() == 0)
            return new ResponseMessage("There was a problem with the photo id").getResponseMessage();
        Photos photo = (Photos) object.get(0);
        manager.delete(photo);
        return new ResponseMessage("Photo has been deleted").getResponseMessage();
    }

}