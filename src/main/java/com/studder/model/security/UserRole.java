package com.studder.model.security;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class UserRole implements Serializable {

	private static final long serialVersionUID = -3545262769123049937L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@OneToMany(mappedBy="role", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Permission> permissions;
	
	/*@OneToMany(mappedBy="role", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<User> users;*/
	
	public UserRole() {
	}
	
	public UserRole(String name) {
		this.name = name;
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
	
	@JsonIgnore
	public List<Permission> getPermissions() {
		return permissions;
	}

	@JsonProperty
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	/*@JsonIgnore
	public List<User> getUsers() {
		return users;
	}

	@JsonProperty
	public void setUsers(List<User> users) {
		this.users = users;
	}*/
	
}
