package com.studder.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Message implements Serializable {

	private static final long serialVersionUID = -509005380964864843L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String text;
	private Date timeRecived;

	@Enumerated(EnumType.STRING)
	private MessageStatus status;

	@ManyToOne
	private UserMatch match;

	@ManyToOne
	private User sender;

	public Message() {
	}

	public Message(@NotBlank String text, Date timeRecived, @NotNull MessageStatus status, UserMatch match, User sender) {
		this.text = text;
		this.timeRecived = timeRecived;
		this.status = status;
		this.match = match;
		this.sender = sender;
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

	public Date getTimeRecived() {
		return timeRecived;
	}

	public void setTimeRecived(Date timeRecived) {
		this.timeRecived = timeRecived;
	}

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

	public UserMatch getMatch() {
		return match;
	}

	public void setMatch(UserMatch match) {
		this.match = match;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

}
