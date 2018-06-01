package com.studder.serviceimpl;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studder.exception.AuthenticationException;
import com.studder.model.User;
import com.studder.service.SecurityService;
import com.studder.service.UserService;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

	private static Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);
	
	private final AuthenticationManager authenticationManager;
	private final UserService userService;

	@Autowired
	public SecurityServiceImpl(AuthenticationManager authenticationManager,
			UserService userService) throws Exception {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	@Override
	public User login(User user) {
		LOGGER.info("Authenticating user " + user.getUsername());
		try {
			UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
	
			Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
					user.getPassword(), new ArrayList<>());
	
			authenticationManager.authenticate(authenticationToken);
	
			if (authenticationToken.isAuthenticated()) {
				LOGGER.info("User " + user.getUsername() + " successfully authenticated");
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				setOnlineStatus();
				User returnUser = userService.findUserByUsername(user.getUsername());
				return returnUser;
			}
		}catch (Exception e) {
			LOGGER.error("Error while executing user authentication. " + e.getMessage());
			throw new AuthenticationException("Error while executing user authentication. " + e.getMessage());
		}
		return null;
	}

	@Override
	public void logout() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null) {
			return;
		}
		
		LOGGER.info(authentication.getName() + " is logging out");
		setLastOnlineStatus();
		SecurityContextHolder.getContext().setAuthentication(null);
		LOGGER.info("User " + authentication.getName() + " logged out");
	}
	
	private void setOnlineStatus() {
		User user = userService.getLoggedUser();
		LOGGER.info("Updating online status for " + user.getUsername());
		user.setOnlineStatus(true);
		userService.updateUser(user);
		LOGGER.info("User's " + user.getUsername() + " online status is successfully updated");
	}
	
	private void setLastOnlineStatus() {
		User user = userService.getLoggedUser();
		if(user == null) {
			return;
		}
		
		LOGGER.info("Updating online status for " + user.getUsername());
		user.setOnlineStatus(false);
		user.setLastOnline(new Date());
		userService.updateUser(user);
		LOGGER.info("User's " + user.getUsername() + " online status is successfully updated");
	}

}
