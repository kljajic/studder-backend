package com.studder.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public void deactivateAccount() {
		// get logged user from context holder and deactivate its account
	}

	@Override
	public List<User> getUsersForSwiping(Long userId) {
		//get user from context holder
		User user = userRepository.getOne(userId);

		List<User> nonSwipedUsers = new ArrayList<>();
		System.out.println("USER RADIUS: " + user.getRadius());
		userRepository.getUsersForSwiping(userId, user.getSwipeThrow()).forEach(nonSwipedUser -> {
			System.out.println("USER ID:" + nonSwipedUser.getId());
			System.out.println("DISTANCE: " + calculateDestanceBetweenUsers(userId, nonSwipedUser.getId()));
			if(calculateDestanceBetweenUsers(userId, nonSwipedUser.getId()) < user.getRadius()) {
				System.out.println("UDALJENOST ZADOVOLJAVAJUCA");
				nonSwipedUsers.add(nonSwipedUser);
			}
		});
		return nonSwipedUsers;
	}
	
	@Override
	public void setLocationForUser(Long userId, double longitude, double latitude) {
		// get logged user
		User user = userRepository.getOne(userId);
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
