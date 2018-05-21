package com.studder.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.studder.model.category.ItemPreferences;
import com.studder.model.media.Comment;
import com.studder.model.media.CommentLike;
import com.studder.model.media.Like;
import com.studder.model.media.Media;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = -5922614948975123925L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private String surname;
	private String description;
	@NotNull
	private Date birthday;
	private Boolean onlineStatus;
	private Date lastOnline;
	@NotNull
	private Short radius;
	private Double latitude;
	private Double longitude;
	private Date profileCreated;
	@NotNull
	private Boolean shareLocation;
	@NotNull
	private Boolean isPrivate;
	private Boolean isDeactivated;
	
	@OneToMany(mappedBy="item", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemPreferences> preferences;
	
	@OneToMany(mappedBy="liker", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Swipe> likers;
	
	@OneToMany(mappedBy="liked", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Swipe> lovedOnes;
	
	@OneToMany(mappedBy="participant1", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Match> participants1;
	
	@OneToMany(mappedBy="participant2", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Match> participants2;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Media> medias;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Like> likes;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CommentLike> likeComments;
	
	/*@ManyToOne
	private UserRole role;*/
	
	public User() {
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Boolean getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Boolean onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Date getLastOnline() {
		return lastOnline;
	}

	public void setLastOnline(Date lastOnline) {
		this.lastOnline = lastOnline;
	}

	public Short getRadius() {
		return radius;
	}

	public void setRadius(Short radius) {
		this.radius = radius;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Date getProfileCreated() {
		return profileCreated;
	}

	public void setProfileCreated(Date profileCreated) {
		this.profileCreated = profileCreated;
	}

	public Boolean getShareLocation() {
		return shareLocation;
	}

	public void setShareLocation(Boolean shareLocation) {
		this.shareLocation = shareLocation;
	}

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public Boolean getIsDeactivated() {
		return isDeactivated;
	}

	public void setIsDeactivated(Boolean isDeactivated) {
		this.isDeactivated = isDeactivated;
	}

	@JsonIgnore
	public List<ItemPreferences> getPreferences() {
		return preferences;
	}

	@JsonProperty
	public void setPreferences(List<ItemPreferences> preferences) {
		this.preferences = preferences;
	}
	
	@JsonIgnore
	public List<Swipe> getLikers() {
		return likers;
	}

	@JsonProperty
	public void setLikers(List<Swipe> likers) {
		this.likers = likers;
	}

	@JsonIgnore
	public List<Swipe> getLovedOnes() {
		return lovedOnes;
	}

	@JsonProperty
	public void setLovedOnes(List<Swipe> lovedOnes) {
		this.lovedOnes = lovedOnes;
	}
	
	@JsonIgnore
	public List<Match> getParticipants1() {
		return participants1;
	}

	@JsonProperty
	public void setParticipants1(List<Match> participants1) {
		this.participants1 = participants1;
	}

	@JsonIgnore
	public List<Match> getParticipants2() {
		return participants2;
	}

	@JsonProperty
	public void setParticipants2(List<Match> participants2) {
		this.participants2 = participants2;
	}

	@JsonIgnore
	public List<Media> getMedias() {
		return medias;
	}

	@JsonProperty
	public void setMedias(List<Media> medias) {
		this.medias = medias;
	}

	@JsonIgnore
	public List<Like> getLikes() {
		return likes;
	}

	@JsonProperty
	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	@JsonIgnore
	public List<Comment> getComments() {
		return comments;
	}

	@JsonProperty
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@JsonIgnore
	public List<CommentLike> getLikeComments() {
		return likeComments;
	}

	@JsonProperty
	public void setLikeComments(List<CommentLike> likeComments) {
		this.likeComments = likeComments;
	}

	/*public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}*/

}
