package com.studder.serviceimpl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studder.model.User;
import com.studder.service.SecurityService;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

	private static Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
	
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;

	@Autowired
	public SecurityServiceImpl(AuthenticationManager authenticationManager,
			UserDetailsService userDetailsService) throws Exception {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
	}

	@Override
	@Transactional(readOnly = true)
	public void login(User user) {
		try {
			UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
	
			Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
					user.getPassword(), new ArrayList<>());
	
			authenticationManager.authenticate(authenticationToken);
	
			if (authenticationToken.isAuthenticated()) {
				logger.info("User " + user.getUsername() + " successfully authenticated.");
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}catch (Exception e) {
			logger.error("Error while executing user authentication. " + e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

}
