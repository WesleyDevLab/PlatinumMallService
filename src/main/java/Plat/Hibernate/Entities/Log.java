package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by MontaserQasem on 12/9/16.
 */
@Entity(name = "log")
public class Log implements DataBaseObject{
    private int id;
    private long deliveryData;
    private Orders order;
    private Admins admin;

    public Log(){
        deliveryData =  Calendar.getInstance().getTimeInMillis();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="delivery_date",nullable = false)
    @Basic
    public long getDeliveryData() {
        return deliveryData;
    }

    public void setDeliveryData(long deliveryData) {
        this.deliveryData = deliveryData;
    }


    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")
    public Admins getAdmin() {
        return admin;
    }

    public void setAdmin(Admins admin) {
        this.admin = admin;
    }
}
