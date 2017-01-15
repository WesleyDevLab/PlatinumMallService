package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MontaserQasem on 11/12/16.
 */
@Entity(name = "privileges")
@JsonIgnoreProperties(value = {"admins"})
public class Privileges implements DataBaseObject {
    private int id;
    private String name;
    private List<Admins> admins = null;

    public Privileges() {
        admins = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", length = 50)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "privilege", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    public List<Admins> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admins> admins) {
        this.admins = admins;
    }
}
