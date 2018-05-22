package com.studder.service;

import com.studder.model.Swipe;

public interface SwipeService {

	void createSwipe(Long likerId, Long likedId, Boolean isLiked);
	
	Swipe getExistingSwipeForUsers(Long participant1, Long participant2);
	
}
