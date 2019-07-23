package com.project.planningapp.entity;

import java.util.HashSet;
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
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "groups")
public class Group {
	
	// Table fields
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "group_id")
	private Long id;
	
	@Column(name = "group_name")
	@NotEmpty(message = "* Must provide a group name")
	private String name;
	
	@Column(name = "group_description")
	private String description;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "users_groups",
	joinColumns = {@JoinColumn(name = "group_id")},
	inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private Set<User> users = new HashSet<>();
	
	// Constructors
	
	public Group() {
		
	}

	public Group(@NotEmpty(message = "* Must provide a group name") String name, 
			String description, 
			Set<User> users) {
		this.name = name;
		this.description = description;
		this.users = users;
	}

	// Getters and Setters

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	// tostring

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
}
