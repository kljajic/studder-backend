package com.studder.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Swipe implements Serializable {

	private static final long serialVersionUID = 5535302499221613526L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Boolean isLiked;
	
	private Date timeSwiped;
	
	@ManyToOne
	private User liker;
	
	@ManyToOne
	private User liked;
	
	public Swipe() {
	}
	
	public Swipe(@NotNull Boolean isLiked, Date timeSwiped, User liker, User liked) {
		this.isLiked = isLiked;
		this.timeSwiped = timeSwiped;
		this.liker = liker;
		this.liked = liked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(Boolean isLiked) {
		this.isLiked = isLiked;
	}
	
	public Date getTimeSwiped() {
		return timeSwiped;
	}

	public void setTimeSwiped(Date timeSwiped) {
		this.timeSwiped = timeSwiped;
	}

	public User getLiker() {
		return liker;
	}

	public void setLiker(User liker) {
		this.liker = liker;
	}

	public User getLiked() {
		return liked;
	}

	public void setLiked(User liked) {
		this.liked = liked;
	}

}
