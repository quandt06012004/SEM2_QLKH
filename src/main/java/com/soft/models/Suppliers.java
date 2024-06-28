package com.soft.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
@Entity
@Table(name="suppliers")
public class Suppliers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id")
	private Integer id;
	
	@Column(name="SupplierName", columnDefinition = "nvarchar(255)")
	@NotBlank(message = "Name is mandatory")
	private String supplierName;
	
	@Column(name="Gender")
	private Boolean gender;
	
	@Column(name="Phone")
	@NotBlank(message = "Phone is mandatory")
	@Pattern(regexp = "^[0-9]{10,15}$", message = "Phone must be a numeric value between 10 and 15 digits")
	private String phone;
	
	@Column(name="Email")
	@Email(message = "Email should be valid")
	@NotBlank(message = "Email is mandatory")
	private String email;
	
	@Column(name="Address", columnDefinition = "nvarchar(255)")
	@NotBlank(message = "Address is mandatory")
	private String address;
	
	@OneToMany(mappedBy = "suppliers")
	private Set<Product> product;

	
	public Suppliers() {
		// TODO Auto-generated constructor stub
	}


	public Suppliers(Integer id, @NotBlank(message = "Name is mandatory") String supplierName, Boolean gender,
			@NotBlank(message = "Phone is mandatory") @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone must be a numeric value between 10 and 15 digits") String phone,
			@Email(message = "Email should be valid") @NotBlank(message = "Email is mandatory") String email,
			@NotBlank(message = "Address is mandatory") String address, Set<Product> product) {
		super();
		this.id = id;
		this.supplierName = supplierName;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.product = product;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getSupplierName() {
		return supplierName;
	}


	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}


	public Boolean getGender() {
		return gender;
	}


	public void setGender(Boolean gender) {
		this.gender = gender;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Set<Product> getProduct() {
		return product;
	}


	public void setProduct(Set<Product> product) {
		this.product = product;
	}
	
	
}
