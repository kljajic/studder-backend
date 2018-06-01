package com.studder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studder.model.User;
import com.studder.service.SecurityService;

@RestController
@RequestMapping("/auth")
public class SecurityController {

	private final SecurityService securityService;
	
	@Autowired
	public SecurityController(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	@PostMapping("/login")
	public User login(@RequestBody User user) {
		User u = securityService.login(user);
		return u;
	}
	
	@GetMapping("/logout")
	public void logout() {
		securityService.logout();
	}
	
}
