package com.studder.service;

import com.studder.model.User;

public interface SecurityService {

	User login(User user);
	
	void logout();
	
}
