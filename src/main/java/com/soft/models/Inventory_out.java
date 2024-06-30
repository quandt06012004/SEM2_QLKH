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

@Entity
@Table(name="inventory_out")
public class Inventory_out {
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="Id")
	    private Integer id;

	    @Column(name="Quantity_out")
	    private Integer quantity_out;

	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    @Column(name="LastUpdated")
	    private Date lastUpdated;

	    @ManyToOne
	    @JoinColumn(name="product_id", referencedColumnName = "id")
	    private Product product;

	    @OneToMany(mappedBy = "inventory_out", cascade = CascadeType.ALL)
//	    , orphanRemoval = true
	    private List<InventoryHistory> history = new ArrayList<>();
	  
	    
	    public Inventory_out() {
			// TODO Auto-generated constructor stub
		}


		public Inventory_out(Integer id, Integer quantity_out, Date lastUpdated, Product product,
				List<InventoryHistory> history) {
			super();
			this.id = id;
			this.quantity_out = quantity_out;
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


		public Integer getQuantity_out() {
			return quantity_out;
		}


		public void setQuantity_out(Integer quantity_out) {
			this.quantity_out = quantity_out;
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
