package com.studder.service;

import com.studder.model.User;

public interface UserService {

	void createUser(User user);
	
	User getUser(Long userId);
	
	void updateUser(User user);
	
	void deactivateAccount();
	
}
