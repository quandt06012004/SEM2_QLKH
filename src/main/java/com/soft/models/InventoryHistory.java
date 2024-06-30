package com.soft.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="InventoryHistory")
public class InventoryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Integer id;

    @Column(name="Quantity")
    private Integer quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="DateUpdated")
    private Date dateUpdated;

    @ManyToOne
    @JoinColumn(name="inventory_id", referencedColumnName = "id")
    private Inventory inventory;
    
    @ManyToOne
    @JoinColumn(name="inventoryOut_id", referencedColumnName = "id")
    private Inventory_out inventory_out;

    public InventoryHistory() {
        // Default constructor
    }

    public InventoryHistory(Integer quantity, Date dateUpdated, Inventory inventory) {
        this.quantity = quantity;
        this.dateUpdated = dateUpdated;
        this.inventory = inventory;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
