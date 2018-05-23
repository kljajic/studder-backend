package com.studder.service;

import java.util.List;

import com.studder.model.User;

public interface UserService {

	void createUser(User user);
	
	User getUser(Long userId);
	
	void updateUser(User user);
	
	void deactivateAccount();
	
	List<User> getUsersForSwiping(Long userId); 
	
	void setLocationForUser(Long userId, double longitude, double latitude);
	
}
