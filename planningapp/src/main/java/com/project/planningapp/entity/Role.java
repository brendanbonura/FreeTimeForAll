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

@Entity
@Table(name="roles")
public class Role {

	// Table Fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private Long id;
	
	@Column(name="role_name")
	@NotNull
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE,
			})
	@JoinTable(name = "users_roles",
			joinColumns = {@JoinColumn(name = "role_id")},
			inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private Set<User> users;

	public Role() {
	}
	
	public Role(Long id, String name, Set<User> users) {
		this.id = id;
		this.name = name;
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", users=" + users + "]";
	}

}
