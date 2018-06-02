package com.studder.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Media implements Serializable {

	private static final long serialVersionUID = 6244895049508882158L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String contentType;
	private Long size;
	private String description;
	private Date timeAdded;
	private String path;
	private Boolean isPrivate = false;
	
	@ManyToOne
	private User user;
	
	public Media() {
	}
	
	public Media(String name, String contentType, Long size, String description, Date timeAdded, String path, User user) {
		this.name = name;
		this.contentType = contentType;
		this.size = size;
		this.description = description;
		this.timeAdded = timeAdded;
		this.path = path;
		this.user = user;
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
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimeAdded() {
		return timeAdded;
	}

	public void setTimeAdded(Date timeAdded) {
		this.timeAdded = timeAdded;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
}
