package com.studder.service;

import java.util.List;

import com.studder.model.User;
import com.studder.model.UserDevice;;

public interface UserDeviceService {
	
	List<UserDevice> getUserDeviceByUserId(Long userId);
	List<UserDevice> getUserDeviceByUserIdAndIsSigned(Long userId, Boolean isSigned);
	
	UserDevice saveUserDevice(UserDevice userDevice);
	UserDevice getUserDeviceByTokenAndUserId(String token, Long userId);
	
	void removeUserDeviceByToken(String token);
	void removeUserDeviceById(Long userDeviceId);
	void login(User user, String token);
	void logout(User user, String token);
	
}
