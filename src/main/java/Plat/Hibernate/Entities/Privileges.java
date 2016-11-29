package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by MontaserQasem on 11/12/16.
 */
@Entity(name="privileges")

public class Privileges implements DataBaseObject {
    private int id;
    private String name;
    private Set<Admins> admins = null;

    public Privileges(){
        admins = new HashSet<Admins>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "privilege" , cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    public Set<Admins> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<Admins> admins) {
        this.admins = admins;
    }
}
