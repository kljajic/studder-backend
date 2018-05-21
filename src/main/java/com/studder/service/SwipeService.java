package com.studder.service;

import java.util.List;

import com.studder.model.Swipe;
import com.studder.model.User;

public interface SwipeService {

	void createSwipe(Long likerId, Long likedId, Boolean isLiked);
	
	List<User> getUsersForSwipe();
	
	Swipe getExistingSwipeForUsers(Long participant1, Long participant2);
	
}
