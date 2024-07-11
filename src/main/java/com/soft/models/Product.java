package com.soft.models;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Product name cannot be empty")
    @Size(max = 255, message = "Product name cannot exceed 255 characters")
    @Column(name = "productName", columnDefinition = "nvarchar(255)")
    private String productName;

    @NotNull(message = "Price cannot be null")
    @Column(name = "Price")
    private Double price;

    @NotNull(message = "Sale Price cannot be null")
    @Column(name = "SalePrice")
    private Double salePrice;

    @Column(name = "image")
    private String image;

    @NotEmpty(message = "Description cannot be empty")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Column(name = "description", columnDefinition = "nvarchar(255)")
    private String description;

    @NotNull(message = "Created at date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "CreatedAt")
    private Date createdAt;

    @NotNull(message = "Gender for product cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "GenderForProduct")
    private GenderForProduct genderForProduct;

    // category
    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category category;

    // inventory
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Inventory> inventory;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Inventory_out> inventory_out;
    // suppliers
    @ManyToOne
    @JoinColumn(name = "supplierId", referencedColumnName = "id")
    private Suppliers suppliers;

    @NotNull(message = "quantity cannot be null")
    @Column(name="TotalQuantity")
    private Integer totalQuantity;

	 public Product() {
		// TODO Auto-generated constructor stub
	}
	public Product(Integer id,
			@NotEmpty(message = "Product name cannot be empty") @Size(max = 255, message = "Product name cannot exceed 255 characters") String productName,
			@NotNull(message = "Price cannot be null") Double price,
			@NotNull(message = "Sale Price cannot be null") Double salePrice, String image,
			@NotEmpty(message = "Description cannot be empty") @Size(max = 255, message = "Description cannot exceed 255 characters") String description,
			@NotNull(message = "Created at date cannot be null") Date createdAt,
			@NotNull(message = "Gender for product cannot be null") GenderForProduct genderForProduct,
			Category category, Set<Inventory> inventory, Set<Inventory_out> inventory_out, Suppliers suppliers,
			Integer totalQuantity) {
		super();
		this.id = id;
		this.productName = productName;
		this.price = price;
		this.salePrice = salePrice;
		this.image = image;
		this.description = description;
		this.createdAt = createdAt;
		this.genderForProduct = genderForProduct;
		this.category = category;
		this.inventory = inventory;
		this.inventory_out = inventory_out;
		this.suppliers = suppliers;
		this.totalQuantity = totalQuantity;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public Double getSalePrice() {
		return salePrice;
	}


	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public GenderForProduct getGenderForProduct() {
		return genderForProduct;
	}


	public void setGenderForProduct(GenderForProduct genderForProduct) {
		this.genderForProduct = genderForProduct;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public Set<Inventory> getInventory() {
		return inventory;
	}


	public void setInventory(Set<Inventory> inventory) {
		this.inventory = inventory;
	}


	public Set<Inventory_out> getInventory_out() {
		return inventory_out;
	}


	public void setInventory_out(Set<Inventory_out> inventory_out) {
		this.inventory_out = inventory_out;
	}


	public Suppliers getSuppliers() {
		return suppliers;
	}


	public void setSuppliers(Suppliers suppliers) {
		this.suppliers = suppliers;
	}


	public Integer getTotalQuantity() {
		return totalQuantity;
	}


	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

    
}
