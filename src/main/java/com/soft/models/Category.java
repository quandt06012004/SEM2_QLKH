package com.soft.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "categoryName", columnDefinition = "nvarchar(255)")
	@NotEmpty(message = "Category name cannot be empty !")
	@Size(min = 2, max = 100, message = "Category names must be between 2 and 100 characters long !")
	private String categoryName;
	@Column(name = "categoryStatus")
	private Boolean categoryStatus;

	@OneToMany(mappedBy = "category")
	private Set<Product> products;

	public Category() {
		// TODO Auto-generated constructor stub
	}

	public Category(Integer id,
			@NotEmpty(message = "Category name cannot be empty !") @Size(min = 2, max = 100, message = "Category names must be between 2 and 100 characters long !") String categoryName,
			Boolean categoryStatus, Set<Product> products) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.categoryStatus = categoryStatus;
		this.products = products;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Boolean getCategoryStatus() {
		return categoryStatus;
	}

	public void setCategoryStatus(Boolean categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}
