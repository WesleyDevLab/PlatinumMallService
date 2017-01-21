package Plat.Hibernate.Services;

import Plat.Hibernate.Entities.Users;
import Plat.Hibernate.Util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MontaserQasem on 11/23/16.
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersAPI {
    DataBaseManager manager = DataBaseManager.getInstance();

    @GET
    public String getAllUsers() {
        List<DataBaseObject> objects = manager.find(null, Users.class);
        return JsonParser.parse(objects);
    }

    @GET
    @Path("/{userId}")
    public String getUserById(@PathParam("userId") int userId) {
        List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, userId), Users.class);
        if (objects != null && objects.size() > 0)
            return JsonParser.parse(objects);
        return new ResponseMessage("There was a problem with the user id").getResponseMessage();
    }

    @POST
    @Path("/{email}/{userPassword}")
    public String loginUser(@PathParam("email") String email, @PathParam("userPassword") String password) {
        return ResponseMessage.createSimpleObject("userid", "" + doLoginUser(email, password));
    }

    public int doLoginUser(String email, String password) {
        List<RuleObject> rules = new ArrayList<>();
        rules.add(new RuleObject("email", HibernateUtil.EQUAL, email));
        rules.add(new RuleObject("password", HibernateUtil.EQUAL, password));
        List<DataBaseObject> objects = manager.findAll(rules, Users.class);
        if (objects == null || objects.size() == 0)
            return -1;

        Users user = (Users) objects.get(0);
        return user.getId();
    }

    @POST
    @Path("/{email}")
    public String getUserByEmail(@PathParam("email") String email) {
        List<DataBaseObject> objects = manager.find(new RuleObject("email", HibernateUtil.LIKE, email), Users.class);
        return JsonParser.parse(objects);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addUser(Users user) {
        RuleObject rule = new RuleObject("email", HibernateUtil.EQUAL, user.getEmail());
        List<DataBaseObject> object = manager.find(rule, Users.class);
        if (object != null && object.size() > 0)
            return new ResponseMessage("this e-mail is already registered").getResponseMessage();
        manager.merge(user);
        return new ResponseMessage("Your Account has been registered successfully").getResponseMessage();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateUser(Users user) {
        manager.update(user);
        return new ResponseMessage("Your information has been updated").getResponseMessage();
    }

    @DELETE
    @Path("/{userId}")
    public String deleteUser(@PathParam("userId") int userId) {
        List<DataBaseObject> objects = manager.find(new RuleObject("id", HibernateUtil.EQUAL, userId), Users.class);
        if (objects != null && objects.size() > 0) {
            Users user = (Users) objects.get(0);
            manager.delete(user);
            return new ResponseMessage("User deleted").getResponseMessage();
        }
        return new ResponseMessage("There was a problem with the user id").getResponseMessage();
    }

}
