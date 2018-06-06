package com.studder.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.studder.model.User;
import com.studder.model.UserDevice;
import com.studder.repository.UserDeviceRepository;
import com.studder.service.UserDeviceService;

@Service
@Transactional
public class UserDeviceServiceImpl implements UserDeviceService{
	
	private static Logger LOGGER = LoggerFactory.getLogger(UserDeviceServiceImpl.class);
	
	private final UserDeviceRepository userDeviceRepository;
	
	public UserDeviceServiceImpl(UserDeviceRepository userDeviceRepository) {
		this.userDeviceRepository = userDeviceRepository;
	}

	@Override
	public List<UserDevice> getUserDeviceByUserId(Long userId) {
		LOGGER.info("Retrieving user devices for user id " + userId);
		List<UserDevice> userDevices = userDeviceRepository.getUserDeviceByUserId(userId);
		LOGGER.info("Successfully retrieved user devices for user id " + userId);
		return userDevices;
	}

	@Override
	public List<UserDevice> getUserDeviceByUserIdAndIsSigned(Long userId, Boolean isSigned) {
		LOGGER.info("Retrieving user devices for user id " + userId + " signed in " + isSigned);
		List<UserDevice> userDevices = userDeviceRepository.getUserDeviceByIsSignedAndUserId(isSigned, userId);
		LOGGER.info("Successfully retrieved user devices for user id " + userId + " signed in " + isSigned);
		return userDevices;
	}

	@Override
	public UserDevice saveUserDevice(UserDevice userDevice) {
		LOGGER.info("Saving user device");
		UserDevice u = userDeviceRepository.save(userDevice);
		LOGGER.info("Successfully saved user devices for user id " + u.getId());
		return u;
	}

	@Override
	public UserDevice getUserDeviceByTokenAndUserId(String token, Long userId) {
		LOGGER.info("Retrieving user device for token " + token);
		UserDevice u = userDeviceRepository.getUserDeviceByDeviceTokenAndUserId(token, userId);
		LOGGER.info("Successfully retrieved user device for token " + token);
		return u;
	}

	@Override
	public void removeUserDeviceByToken(String token) {
		LOGGER.info("Deleting user device for token " + token);
		UserDevice u = userDeviceRepository.getUserDeviceByDeviceToken(token);
		if(u == null) {
			LOGGER.info("Unable to delete user device for token - no user device found for token" + token);
			return;
		}
		userDeviceRepository.delete(u);
		LOGGER.info("Successfully deleted user device for token " + token);
		
	}

	@Override
	public void removeUserDeviceById(Long userDeviceId) {
		LOGGER.info("Deleting user device for token " + userDeviceId);
		UserDevice u = userDeviceRepository.getOne(userDeviceId);
		if(u == null) {
			LOGGER.info("Unable to delete user device for token - no user device found for user device id" + userDeviceId);
			return;
		}
		userDeviceRepository.delete(u);
		LOGGER.info("Successfully deleted user device for user device id " + userDeviceId);
		
	}

	@Override
	public void login(User user, String token) {
		LOGGER.info("Tokenization => ");
		if(user != null && token != null) {
			LOGGER.info("Tokenization for user id" + user.getId());
			UserDevice device = getUserDeviceByTokenAndUserId(token, user.getId());
			if(device != null) {
				device.setSignedIn(true);
				saveUserDevice(device);
			} else {
				UserDevice dev = new UserDevice();
				dev.setDeviceToken(token);
				dev.setSignedIn(true);
				dev.setUser(user);
				userDeviceRepository.save(dev);
			}
			LOGGER.info("Tokenization success for user id" + user.getId());
		}
	}

	@Override
	public void logout(User user, String token) {
		LOGGER.info("Sign out of device by token => " + token);
		UserDevice device = getUserDeviceByTokenAndUserId(token, user.getId());
		if(device != null) {
			device.setSignedIn(false);
			saveUserDevice(device);
		} 
		LOGGER.info("Sign out of device by token => " + token);
	}
}
