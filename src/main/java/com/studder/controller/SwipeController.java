package com.studder.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studder.model.Swipe;
import com.studder.service.SwipeService;

@RestController
@RequestMapping("/swipes")
public class SwipeController {

	private final SwipeService swipeService;

	@Autowired
	public SwipeController(SwipeService swipeService) {
		this.swipeService = swipeService;
	}

	@PostMapping("/{likedId}")
	public void createSwipe(@PathVariable("likedId") @NotNull @Valid Long likedId,
			@RequestBody @NotNull @Valid Swipe swipe) {
		swipeService.createSwipe(likedId, swipe.getIsLiked());
	}
	
	@GetMapping("/count/{likerId}")
	public Integer getSwipeCountForUser(@PathVariable("likerId") @NotNull @Valid Long likerId) {
		return swipeService.getSwipesCountForUser(likerId);
	}
	
}
