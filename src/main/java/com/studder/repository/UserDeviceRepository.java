package com.studder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studder.model.UserDevice;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

	List<UserDevice> getUserDeviceByIsSignedAndUserId(boolean isSigned, Long userId);
	List<UserDevice> getUserDeviceByUserId(Long userId);
	UserDevice getUserDeviceByDeviceToken(String token);
	UserDevice getUserDeviceByDeviceTokenAndUserId(String token, Long userId);

}