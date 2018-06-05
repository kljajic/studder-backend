package com.notification;

import java.io.Serializable;



public class Notification implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 482239514493086802L;

	public static final String NOTIFICATION_TYPE = "MATCH";
	
	private String title;
	private String message;
	
	public Notification() {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
