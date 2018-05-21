package com.studder.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studder.model.User;
import com.studder.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public void createUser(@RequestBody @Valid User user) {
		userService.createUser(user);
	}
	
	@GetMapping("/{userId}")
	public User getUser(@PathVariable("userId") @Valid @NotNull Long userId) {
		return userService.getUser(userId);
	}
	
	@PutMapping
	public void updateUser(@RequestBody @Valid User user) {
		userService.updateUser(user);
	}

	@PutMapping("/deactivate")
	public void deactivateAccount(@PathVariable("userId") @Valid @NotNull Long userId) {
		userService.deactivateAccount();
	}
	
}
