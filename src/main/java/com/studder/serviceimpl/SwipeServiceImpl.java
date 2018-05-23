package com.studder.serviceimpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studder.model.UserMatch;
import com.studder.model.Swipe;
import com.studder.model.User;
import com.studder.repository.SwipeRepository;
import com.studder.service.MatchService;
import com.studder.service.SwipeService;
import com.studder.service.UserService;

@Service
@Transactional
public class SwipeServiceImpl implements SwipeService {

	private final SwipeRepository swipeRepository;
	private final UserService userService;
	private final MatchService matchService;
	
	@Autowired
	public SwipeServiceImpl(SwipeRepository swipeRepository, 
							UserService userService,
							MatchService matchService) {
		this.swipeRepository = swipeRepository;
		this.userService = userService;
		this.matchService = matchService;
	}
	
	@Override
	public void createSwipe(Long likerId, Long likedId, Boolean isLiked) {
		//liker should be used from Context holder
		User liker = userService.getUser(likerId);
		User liked = userService.getUser(likedId);
		
		Swipe swipe = new Swipe(isLiked, new Date(), liker, liked);
		swipeRepository.save(swipe);
		
		if(!isLiked) {
			return;
		}
			
		Swipe otherUserSwiped = this.getExistingSwipeForUsers(likedId, likerId);
		if(otherUserSwiped != null && otherUserSwiped.getIsLiked()) {
			UserMatch match = new UserMatch(new Date(), liker, liked);
			matchService.createMatch(match);
		}
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public Swipe getExistingSwipeForUsers(Long participant1, Long participant2) {
		return swipeRepository.getSwipeByLikerIdAndLikedId(participant1, participant2);
	}

}
