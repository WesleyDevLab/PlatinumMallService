package Plat.Hibernate.Entities;

import Plat.Hibernate.Util.DataBaseObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by MontaserQasem on 11/12/16.
 */
@Entity(name = "orders")
public class Orders implements DataBaseObject {
    private int id;
    private String description;
    private int status;
    private int payment;
    private String additionalNote;
    private double total;
    private boolean deviceInput;
    private String deliveryDate;
    private Users user;
    private Set<OrderItem> orderItems = null;

    public Orders() {
        orderItems = new HashSet<OrderItem>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "description", length = 300, nullable = true)
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "status", nullable = false)
    @Basic
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "payment", nullable = false)
    @Basic
    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    @Column(name = "additionalNote", nullable = true, length = 45)
    @Basic
    public String getAditionalNote() {
        return additionalNote;
    }

    public void setAditionalNote(String aditionalNote) {
        this.additionalNote = aditionalNote;
    }

    @Column(name = "total", nullable = false)
    @Basic
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Column(name = "device_input", nullable = false)
    @Basic
    public boolean isDeviceInput() {
        return deviceInput;
    }

    public void setDeviceInput(boolean deviceInput) {
        this.deviceInput = deviceInput;
    }

    @Column(name = "delivery_date", nullable = false)
    @Basic
    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
