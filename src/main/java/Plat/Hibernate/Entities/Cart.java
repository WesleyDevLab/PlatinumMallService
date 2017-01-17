package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;

/**
 * Created by MontaserQasem on 11/12/16.
 */
@Entity(name = "cart")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"})
public class Cart implements DataBaseObject {
    private int id;
    private Integer quantity;
    private Users user;
    private Items item;


    public Cart() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    public int getId() {
        return id;
    }

    @Column(name = "quantity")
    @Basic
    @JsonProperty
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonProperty
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @JsonProperty
    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }
}
