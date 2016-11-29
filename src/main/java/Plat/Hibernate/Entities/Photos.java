package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by MontaserQasem on 11/12/16.
 */
@Entity(name="photos")
public class Photos implements DataBaseObject {
    private int id;
    private String path;
    private boolean isprimary;
    private Items item;

    public Photos(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id ){
        this.id=id;
    }


    @Column(name="path",nullable = false,length = 300)
    @Basic
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name="is_primary",nullable = false)
    @Basic
    public boolean isPrimary() {
        return isprimary;
    }

    public void setPrimary(boolean primary) {
        this.isprimary = primary;
    }

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }
}
