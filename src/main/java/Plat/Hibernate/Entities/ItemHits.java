package Plat.Hibernate.Entities;


import Plat.Hibernate.Util.DataBaseObject;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


import javax.persistence.*;

/**
 * Created by MontaserQasem on 11/12/16.
 */
@Entity(name = "item_hits")
@JsonIgnoreProperties(value = {"guest","handler", "hibernateLazyInitializer"})
public class ItemHits implements DataBaseObject {
    private int id;
    private String date;
    private Items item;
    private Guests guest;

    public ItemHits() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "date", nullable = false)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id")
    public Guests getGuest() {
        return guest;
    }

    public void setGuest(Guests guest) {
        this.guest = guest;
    }
}
