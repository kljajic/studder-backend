package com.studder.model.media;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.studder.model.User;

@Entity
public class Media implements Serializable {

	private static final long serialVersionUID = 6244895049508882158L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String path;
	private String description;
	private Date timeAdded;
	private Boolean isPrivate;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy="media", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Like> likes;
	
	@OneToMany(mappedBy="media", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Comment> comments;
	
	public Media() {
	}
	
	public Media(String path, String description, Date timeAdded, Boolean isPrivate, User user) {
		this.path = path;
		this.description = description;
		this.timeAdded = timeAdded;
		this.isPrivate = isPrivate;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	public Collection<Like> getLikes() {
		return likes;
	}

	@JsonProperty
	public void setLikes(Collection<Like> likes) {
		this.likes = likes;
	}

	@JsonIgnore
	public Collection<Comment> getComments() {
		return comments;
	}

	@JsonProperty
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
}
