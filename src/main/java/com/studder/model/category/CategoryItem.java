package com.studder.model.category;

import java.io.Serializable;
import java.util.Collection;

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

@Entity
public class CategoryItem implements Serializable {

	private static final long serialVersionUID = -546468505029602085L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToOne
	private Category category;
	
	@OneToMany(mappedBy="item", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<ItemPreferences> preferences;
	
	public CategoryItem() {
	}
	
	public CategoryItem(String name, Category category) {
		this.name = name;
		this.category = category;
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
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@JsonIgnore
	public Collection<ItemPreferences> getPreferences() {
		return preferences;
	}

	@JsonProperty
	public void setPreferences(Collection<ItemPreferences> preferences) {
		this.preferences = preferences;
	}
	
}
