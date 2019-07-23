package com.project.planningapp.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {

	// Table Fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	
	@Column(name="user_email")
	@NotNull(message = "Email is required")
	@Size(min = 1, max = 254)
	private String email;
	
	@Column(name="user_firstName")
	@NotNull(message = "First name is required")
	@Size(min = 1, max = 254)
	private String firstName;
	
	@Column(name="user_lastName")
	@NotNull(message = "Last name is required")
	@Size(min = 1, max = 254)
	private String lastName;
	
	@Column(name="user_password")
	@NotNull(message = "Password is required")
	@Size(min = 5, max = 254, message = "Password should be at least 5 characters long")
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY,
				cascade = {
						CascadeType.PERSIST,
						CascadeType.MERGE,
				})
	@JoinTable(name = "users_groups",
			joinColumns = {@JoinColumn(name = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "group_id")})
	private Set<Group> groups;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE,
			})
	@JoinTable(name = "users_roles",
			joinColumns = {@JoinColumn(name = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<Role> roles;
	
	// Constructors
	
	public User() {
		
	}

	public User(String email, 
			String firstName, 
			String lastName, 
			String password,
			Set<Group> groups,
			Set<Role> roles) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.groups = groups;
		this.roles = roles;
	}

	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	// tostring
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", groups=" + groups + ", roles=" + roles + "]";
	}

	
}
