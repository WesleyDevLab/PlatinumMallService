package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MontaserQasem on 11/14/16.
 */
@Entity(name = "guests")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"})
public class Guests implements DataBaseObject {
    private int id;
    private List<ItemHits> itemsHits;

    public Guests(){
        itemsHits = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "guest",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    public List<ItemHits> getItemsHits() {
        return itemsHits;
    }

    public void setItemsHits(List<ItemHits> itemsHits) {
        this.itemsHits = itemsHits;
    }
}
