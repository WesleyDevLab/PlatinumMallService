package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by MontaserQasem on 11/14/16.
 */
@Entity(name = "guests")
public class Guests implements DataBaseObject {
    private int id;
    private Set<ItemHits> itemsHits;
    public Guests(){
        itemsHits = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "guest",cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    public Set<ItemHits> getItemsHits() {
        return itemsHits;
    }

    public void setItemsHits(Set<ItemHits> itemsHits) {
        this.itemsHits = itemsHits;
    }
}
