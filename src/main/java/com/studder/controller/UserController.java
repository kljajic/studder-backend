package com.studder.controller;

import java.util.List;

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

	@PostMapping("/update")
	public String updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}

	@PutMapping("/deactivate")
	public void deactivateAccount(@PathVariable("userId") @Valid @NotNull Long userId) {
		userService.deactivateAccount();
	}

	@PutMapping("/location/{longitude:.+}/{latitude:.+}")
	public void setLocationForUser(@PathVariable("longitude") @Valid @NotNull Double longitude,
			@PathVariable("latitude") @Valid @NotNull Double latitude) {
		userService.setLocationForUser(longitude, latitude);
	}
	
	@GetMapping("/getForSwipping")
	public List<User> getUsersForSwiping() {
		return userService.getUsersForSwiping();
		/*ArrayList<User> users = new ArrayList<>();
		User user = new User();
		user.setUsername("Hehe");
		user.setPassword("hehe");
		user.setName("heh");
		user.setCity("NS");
		User user2 = new User();
		user2.setUsername("Hehe2");
		user2.setPassword("hehe2");
		user2.setName("heh2");
		user2.setCity("NS2");
		users.add(user);
		users.add(user2);
		return users;*/
	}

}
