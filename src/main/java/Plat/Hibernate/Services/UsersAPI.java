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
    public List<Users> getAllUsers() {
        List<DataBaseObject> objects = manager.find(null, Users.class);
        List<Users> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Users.class);
            for (int i = 0; i < objects.size(); i++) {
                Users user = (Users) objects.get(i);
                result.add(user);
            }
        }
        return result;
    }

    @GET
    @Path("/{userId}")
    public Users getUserById(@PathParam("userId") int userId) {
        RuleObject rule = new RuleObject("id", HibernateUtil.EQUAL, userId);
        List<DataBaseObject> objects = manager.find(rule, Users.class);
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Users.class);
            Users user = (Users) objects.get(0);
            return user;
        }
        return null;
    }

    @GET
    @Path("/{email}/{userPassword}")
    public String loginUser(@PathParam("email") String email, @PathParam("userPassword") String password) {
        List<RuleObject> rules = new ArrayList<>();
        rules.add(new RuleObject("email", HibernateUtil.EQUAL, email));
        rules.add(new RuleObject("password", HibernateUtil.EQUAL, password));
        List<DataBaseObject> objects = manager.findAll(rules, Users.class);
        if (objects == null || objects.size() == 0) return "-1";
        Users user = (Users) objects.get(0);
        return user.getId() + "";
    }

    @POST
    @Path("/{email}")
    public List<Users> getUsersByName(@PathParam("email") String email) {
        RuleObject rule = new RuleObject("email", HibernateUtil.LIKE, email);
        List<DataBaseObject> objects = manager.find(rule, Users.class);
        List<Users> result = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            objects = EntityCleaner.clean(objects, Users.class);
            for (int i = 0; i < objects.size(); i++) {
                Users node = (Users) objects.get(i);
                result.add(node);
            }
        }
        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addUser(Users user) {
        RuleObject rule = new RuleObject("email", HibernateUtil.EQUAL, user.getEmail());
        List<DataBaseObject> object = manager.find(rule, Users.class);
        if (object != null && object.size() > 0) return "this e-mail is used before";
        manager.merge(user);
        return "User has been added successfully";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateUser(Users user) {
        manager.update(user);
        return "User has been updated ";
    }

    @DELETE
    @Path("{userId}")
    public String deleteUser(@PathParam("userId") int userId) {
        Users user = getUserById(userId);
        if (user == null) return "There's a problem with the user id";
        manager.delete(user);
        return "user (" + user.getFirstName() + ") has been deleted";
    }

}
