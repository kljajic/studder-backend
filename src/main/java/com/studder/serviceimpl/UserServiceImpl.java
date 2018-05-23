package com.studder.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studder.exception.DataBaseManipulationException;
import com.studder.model.User;
import com.studder.repository.UserRepository;
import com.studder.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void createUser(User user) {
		User existingUser = userRepository.findUserByUsername(user.getUsername());
		if(existingUser != null) {
			throw new DataBaseManipulationException("User with this username already exists");
		}
		userRepository.save(user);
	}

	@Override
	public User getUser(Long userId) {
		return userRepository.getOne(userId);
	}

	@Override
	public void updateUser(User user) {

	}
	
	@Override
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	@Override
	public User getLoggedUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return findUserByUsername(username);
	}
	
	@Override
	public void deactivateAccount() {
		// get logged user from context holder and deactivate its account
	}

	@Override
	public List<User> getUsersForSwiping(Long userId) {
		//get user from context holder
		User user = userRepository.getOne(userId);

		return userRepository.getUsersForSwiping(user.getId(), user.getSwipeThrow()).stream().filter(
				userForSwipe -> calculateDestanceBetweenUsers(user.getId(), userForSwipe.getId()) < user.getRadius())
				.collect(Collectors.toList());
	}
	
	@Override
	public void setLocationForUser(Double longitude, Double latitude) {
		User user = getLoggedUser();
		user.setLongitude(longitude);
		user.setLatitude(latitude);
		userRepository.save(user);
	}

	private double calculateDestanceBetweenUsers(Long user1Id, Long user2Id) {
		User user1 = userRepository.getOne(user1Id);
		User user2 = userRepository.getOne(user2Id);
		
		return distance(user1.getLatitude(), user1.getLongitude(), user2.getLatitude(), user2.getLongitude());
	}
	
	private double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		
		return dist * 1.609344;
	}

	/**
	 * This function converts decimal degrees to radians
	 * @param deg --> Number of decimal degrees
	 * @return
	 */
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	/**
	 * This function converts radians to decimal degrees
	 * 
	 * @param rad --> Number of radian
	 * @return
	 */
	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

}
