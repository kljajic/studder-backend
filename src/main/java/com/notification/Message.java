package com.notification;

import java.io.Serializable;
import java.util.Map;

public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2362445221171901145L;
	private String token;
	private Notification notification;
	private Map<String, String> data;
	
	public Message() {	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
}
