package com.studder.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = -5922614948975123925L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	//@NotBlank
	private String name;
	//@NotBlank
	private String surname;
	private String description;
	//@NotNull
	private Date birthday;
	private Boolean onlineStatus;
	private Date lastOnline;
	//@NotNull
	private Short radius;
	private Double latitude;
	private Double longitude;
	private Date profileCreated;
	//@NotNull
	private Boolean shareLocation;
	//@NotNull
	private Boolean isPrivate;
	private Boolean isDeactivated;
	private String profileImage;
	
	private String city;
	
	//@NotNull
	@Enumerated(EnumType.STRING)
	private Gender userGender;
	
	//@NotNull
	@Enumerated(EnumType.STRING)
	private Gender swipeThrow;
	
	@OneToMany(mappedBy="item", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemPreferences> preferences;
	
	@OneToMany(mappedBy="liker", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Swipe> likers;
	
	@OneToMany(mappedBy="liked", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Swipe> lovedOnes;
	
	@OneToMany(mappedBy="participant1", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserMatch> participants1;
	
	@OneToMany(mappedBy="participant2", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserMatch> participants2;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Media> medias;
	
	@OneToMany(mappedBy="sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Message> messages;
	
	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Gender getUserGender() {
		return userGender;
	}

	public void setUserGender(Gender userGender) {
		this.userGender = userGender;
	}

	public Gender getSwipeThrow() {
		return swipeThrow;
	}

	public void setSwipeThrow(Gender swipeThrow) {
		this.swipeThrow = swipeThrow;
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
	public List<UserMatch> getParticipants1() {
		return participants1;
	}

	@JsonProperty
	public void setParticipants1(List<UserMatch> participants1) {
		this.participants1 = participants1;
	}

	@JsonIgnore
	public List<UserMatch> getParticipants2() {
		return participants2;
	}

	@JsonProperty
	public void setParticipants2(List<UserMatch> participants2) {
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
	public List<Message> getMessages() {
		return messages;
	}

	@JsonProperty
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	
}
