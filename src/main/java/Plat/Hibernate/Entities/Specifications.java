package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by MontaserQasem on 11/15/16.
 */

@Entity(name ="specifications")
public class Specifications implements DataBaseObject {
    private int id;
    private String specificationKey;
    private String specificationValue;
    private Items item;

    public Specifications(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Basic
    @Column(name = "specification_key",nullable = false,length = 50)
    public String getSpecificationKey() {
        return specificationKey;
    }

    public void setSpecificationKey(String specificationKey) {
        this.specificationKey = specificationKey;
    }

    @Basic
    @Column(name = "specification_value",nullable = false,length = 50)
    public String getSpecificationValue() {
        return specificationValue;
    }

    public void setSpecificationValue(String specificationValue) {
        this.specificationValue = specificationValue;
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
