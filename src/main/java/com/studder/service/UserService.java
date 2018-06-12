package com.studder.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.studder.model.User;

public interface UserService extends UserDetailsService {

	void createUser(User user);
	
	User getUser(Long userId);
	
	String updateUser(User user);
	
	User findUserByUsername(String username);
	
	User getLoggedUser();
	
	void deactivateAccount();
	
	List<User> getUsersForSwiping(); 
	
	void setLocationForUser(Double longitude, Double latitude);

	List<User> getUsersForMarking();
	
	void setProfileImage(Long id);
	
}
