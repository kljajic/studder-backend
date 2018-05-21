package com.studder.model.category;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.studder.model.User;

@Entity
public class ItemPreferences implements Serializable {

	private static final long serialVersionUID = 5115899153045600082L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private short score;
	
	@ManyToOne
	private CategoryItem item;
	
	@ManyToOne
	private User user;
	
	public ItemPreferences() {
	}
	
	public ItemPreferences(short score, CategoryItem item, User user) {
		this.score = score;
		this.item = item;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public short getScore() {
		return score;
	}

	public void setScore(short score) {
		this.score = score;
	}

	public CategoryItem getItem() {
		return item;
	}

	public void setItem(CategoryItem item) {
		this.item = item;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
