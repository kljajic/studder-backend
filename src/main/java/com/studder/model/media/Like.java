package com.studder.model.media;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.studder.model.User;

@Entity
public class Like implements Serializable {

	private static final long serialVersionUID = -389098491926197727L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private LikeType type;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Media media;
	
	public Like() {
	}
	
	public Like(LikeType type, User user, Media media) {
		this.type = type;
		this.user = user;
		this.media = media;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LikeType getType() {
		return type;
	}

	public void setType(LikeType type) {
		this.type = type;
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
	
}
