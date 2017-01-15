package Plat.Hibernate.Entities;


import Plat.Hibernate.Util.DataBaseObject;


import javax.persistence.*;

/**
 * Created by MontaserQasem on 11/13/16.
 */
@Entity(name="wishlist")
public class WishList implements DataBaseObject {
    private int id;
    private Users user;
    private Items item;

    public WishList(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }
}
