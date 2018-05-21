package com.studder.model.media;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class Comment implements Serializable {

	private static final long serialVersionUID = -7790869152080002035L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String text;
	private Date time;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Media media;
	
	@OneToMany(mappedBy="comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CommentLike> likes;
	
	public Comment() {
	}
	
	public Comment(String text, Date time, User user, Media media) {
		this.text = text;
		this.time = time;
		this.user = user;
		this.media = media;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	@JsonIgnore
	public List<CommentLike> getLikes() {
		return likes;
	}

	@JsonProperty
	public void setLikes(List<CommentLike> likes) {
		this.likes = likes;
	}
	
}
