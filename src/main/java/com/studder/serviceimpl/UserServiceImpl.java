package com.studder.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studder.exception.DataBaseManipulationException;
import com.studder.model.User;
import com.studder.repository.UserRepository;
import com.studder.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void createUser(User user) {
		LOGGER.info("Creating user with username " + user.getUsername() + " to studder system");
		User existingUser = userRepository.findUserByUsername(user.getUsername());
		if(existingUser != null) {
			LOGGER.error("User with given username already exists");
			throw new DataBaseManipulationException("User with given username already exists");
		}
		user.setProfileCreated(new Date());
		user.setIsDeactivated(false);
		user.setLastOnline(new Date());
		user.setOnlineStatus(false);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		System.out.println("PW JE: " + user.getPassword());
		userRepository.save(user);
		LOGGER.info("User " + user.getUsername() + " is successfully created");
	}

	@Override
	public User getUser(Long userId) {
		LOGGER.info("Fetching user based on id " + userId);
		User user = userRepository.getOne(userId);
		LOGGER.info("User  " + user.getUsername() + " is successfully fetched");
		return user;
	}

	@Override
	public String updateUser(User user) {
		//change return type to boolean, so user can know if he successfully changed his password...
		LOGGER.info("Updating user with username " + user.getUsername());
		User existingUser = userRepository.findUserByUsername(user.getUsername());
		if(existingUser == null) {
			LOGGER.error("Given username could not be found");
			throw new DataBaseManipulationException("User with " + user.getUsername() + "doesn't exist.");
		}
		//existingUser.setBirthday(user.getBirthday());
		if(user.getDescription() != null)
			existingUser.setDescription(user.getDescription());
		if(user.getIsPrivate() != null)
			existingUser.setIsPrivate(user.getIsPrivate());
		if(user.getName() != null)
			existingUser.setName(user.getName());
		if(user.getRadius() != null)
			existingUser.setRadius(user.getRadius());
		if(user.getShareLocation() != null)
			existingUser.setShareLocation(user.getShareLocation());
		if(user.getSurname() != null)
			existingUser.setSurname(user.getSurname());
		if(user.getUserGender() != null)
			existingUser.setUserGender(user.getUserGender());
		if(user.getSwipeThrow() != null)
			existingUser.setSwipeThrow(user.getSwipeThrow());
		if(user.getNewPw() != null && user.getPassword() != null){
			if(passwordEncoder.matches(user.getPassword(), existingUser.getPassword())){
				existingUser.setPassword(passwordEncoder.encode(user.getNewPw()));
			} else{
				return "-1";
			}
		}
		userRepository.save(existingUser);
		LOGGER.info("User with " + user.getUsername() + " username is successfully updated");
		return "1";
	}
	
	@Override
	public User findUserByUsername(String username) {
		LOGGER.info("Fetching user by username " + username);
		User user = userRepository.findUserByUsername(username);
		if(user == null) {
			LOGGER.info("User with username " + username + " could not be found");
		}else {
			LOGGER.info("User with username " + username + " is successfully fetched");
		}
		return user;
	}

	@Override
	public User getLoggedUser() {
		LOGGER.info("Fetching logged user");
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return findUserByUsername(username);
	}
	
	@Override
	public void deactivateAccount() {
		User user = getLoggedUser();
		LOGGER.info("Deactivating " + user.getUsername() + " account");
		user.setIsDeactivated(true);
		userRepository.save(user);
		LOGGER.info("Deactivation of " + user.getUsername() + "account is successfully finished");
	}

	@Override
	public List<User> getUsersForSwiping() {
		User user = getLoggedUser();
		LOGGER.info("Fetching users for swiping for user " + user.getUsername());
		
		List<User> usersForSwipping = userRepository.getUsersForSwiping(user.getId(), user.getSwipeThrow()).stream()
				.filter(userForSwipe -> calculateDestanceBetweenUsers(user.getId(), userForSwipe.getId()) < user
						.getRadius())
				.collect(Collectors.toList());
		
		LOGGER.info("Users are successfully fetched");
		return usersForSwipping;
	}
	
	@Override
	public void setLocationForUser(Double longitude, Double latitude) {
		User user = getLoggedUser();
		LOGGER.info("Updating user " + user.getUsername() + " coordinates");
		user.setLongitude(longitude);
		user.setLatitude(latitude);
		userRepository.save(user);
		LOGGER.info("User coordinates fo user " + user.getUsername() + " are successfully updated");
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

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findUserByUsername(username);

		if (user == null) {
			return null;
		}

		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), new ArrayList<>());
		return userDetails;
	}

}
