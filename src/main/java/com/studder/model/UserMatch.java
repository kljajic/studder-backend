package com.studder.model;

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

@Entity
public class UserMatch implements Serializable {

	private static final long serialVersionUID = 8440017360250516589L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date matchTime;
	
	@ManyToOne
	private User participant1;
	
	@ManyToOne
	private User participant2;
	
	@OneToMany(mappedBy="match", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Message> messages;

	public UserMatch() {
	}
	
	public UserMatch(Date matchTime, User participant1, User participant2) {
		this.matchTime = matchTime;
		this.participant1 = participant1;
		this.participant2 = participant2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	public User getParticipant1() {
		return participant1;
	}

	public void setParticipant1(User participant1) {
		this.participant1 = participant1;
	}

	public User getParticipant2() {
		return participant2;
	}

	public void setParticipant2(User participant2) {
		this.participant2 = participant2;
	}

	@JsonIgnore
	public Collection<Message> getMessages() {
		return messages;
	}

	@JsonProperty
	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}
	
}
