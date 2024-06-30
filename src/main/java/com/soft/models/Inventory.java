package com.soft.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "Inventories")
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;

	@NotNull(message = "Quantity is required")
	@PositiveOrZero(message = "Quantity must be a positive number or zero")
	@Column(name = "Quantity")
	private Integer quantity;

	@NotNull(message = "Last updated date is required")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "LastUpdated")
	private Date lastUpdated;
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	@OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
//    , orphanRemoval = true
	private List<InventoryHistory> history = new ArrayList<>();

	public Inventory() {
		// Default constructor
	}

	public Inventory(Integer id,
			@NotNull(message = "Quantity is required") @PositiveOrZero(message = "Quantity must be a positive number or zero") Integer quantity,
			@NotNull(message = "Last updated date is required") Date lastUpdated, Product product,
			List<InventoryHistory> history) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.lastUpdated = lastUpdated;
		this.product = product;
		this.history = history;
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

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<InventoryHistory> getHistory() {
		return history;
	}

	public void setHistory(List<InventoryHistory> history) {
		this.history = history;
	}
}
