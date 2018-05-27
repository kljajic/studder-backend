package com.studder.service;

import com.studder.model.User;

public interface SecurityService {

	void login(User user);
	
	void logout();
	
}
