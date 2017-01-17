package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by MontaserQasem on 12/9/16.
 */
@Entity(name = "log")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"})
public class Log implements DataBaseObject {
    private int id;
    private long deliveryData;
    private String adminName;
    private Orders order;

    public Log() {
        deliveryData = Calendar.getInstance().getTimeInMillis();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "delivery_date", nullable = false)
    @Basic
    public long getDeliveryData() {
        return deliveryData;
    }

    public void setDeliveryData(long deliveryData) {
        this.deliveryData = deliveryData;
    }

    @Column(name = "admin_name", nullable = false)
    @Basic
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }


    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }


}
