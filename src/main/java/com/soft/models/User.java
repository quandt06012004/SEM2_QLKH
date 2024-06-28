package com.soft.models;

import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "users")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "Username is mandatory ! ")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters ! ")
    private String userName;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password is mandatory ! ")
    @Size(min = 8, message = "Password must be at least 8 characters long ! ")
    private String passWord;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "fullname", nullable = false)
    @NotBlank(message = "Full name is mandatory ! ")
    private String fullName;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "address", nullable = false)
    @NotBlank(message = "Address is mandatory ! ")
    private String address;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is mandatory!")
    @Email(message = "Email should be valid!")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email must end with @gmail.com")
    private String email;

    @Column(name = "telephone")
    @Pattern(regexp = "^\\d{10}$", message = "Telephone should be a 10 digit number ! ")
    private String telephone;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<UserRole> userRoles;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(Long id,
			@NotBlank(message = "Username is mandatory ! ") @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters ! ") String userName,
			@NotBlank(message = "Password is mandatory ! ") @Size(min = 8, message = "Password must be at least 8 characters long ! ") String passWord,
			Boolean enabled, @NotBlank(message = "Full name is mandatory ! ") String fullName, Boolean gender,
			@NotBlank(message = "Address is mandatory ! ") String address,
			@NotBlank(message = "Email is mandatory!") @Email(message = "Email should be valid!") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email must end with @gmail.com") String email,
			@Pattern(regexp = "^\\d{10}$", message = "Telephone should be a 10 digit number ! ") String telephone,
			Set<UserRole> userRoles) {
		super();
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.enabled = enabled;
		this.fullName = fullName;
		this.gender = gender;
		this.address = address;
		this.email = email;
		this.telephone = telephone;
		this.userRoles = userRoles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	
}
