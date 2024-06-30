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
    @JoinColumn(name = "inventory_out_id", referencedColumnName = "id")
    private Inventory_out inventoryOut;

    @ManyToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

   public InventoryHistory() {
	// TODO Auto-generated constructor stub
}

public InventoryHistory(Integer id, Integer quantity, Date dateUpdated, Inventory_out inventoryOut,
		Inventory inventory) {
	super();
	this.id = id;
	this.quantity = quantity;
	this.dateUpdated = dateUpdated;
	this.inventoryOut = inventoryOut;
	this.inventory = inventory;
}

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

public Inventory_out getInventoryOut() {
	return inventoryOut;
}

public void setInventoryOut(Inventory_out inventoryOut) {
	this.inventoryOut = inventoryOut;
}

public Inventory getInventory() {
	return inventory;
}

public void setInventory(Inventory inventory) {
	this.inventory = inventory;
}


   
}
