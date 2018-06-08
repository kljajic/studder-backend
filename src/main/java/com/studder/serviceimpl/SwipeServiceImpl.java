package com.studder.serviceimpl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.studder.exception.DataBaseManipulationException;
import com.studder.model.Swipe;
import com.studder.model.User;
import com.studder.model.UserMatch;
import com.studder.repository.SwipeRepository;
import com.studder.service.MatchService;
import com.studder.service.NotificationService;
import com.studder.service.SwipeService;
import com.studder.service.UserService;

@Service
@Transactional
public class SwipeServiceImpl implements SwipeService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(SwipeServiceImpl.class);
	
	private final SwipeRepository swipeRepository;
	private final UserService userService;
	private final MatchService matchService;
	private final NotificationService notificationService;
	@Autowired
	public SwipeServiceImpl(SwipeRepository swipeRepository, 
							UserService userService,
							MatchService matchService,
							RestTemplate restTemplate,
							NotificationService notificationService) {
		this.swipeRepository = swipeRepository;
		this.userService = userService;
		this.matchService = matchService;
		this.notificationService = notificationService;
	}
	
	@Override
	public void createSwipe(Long likedId, Boolean isLiked) {
		User liker = userService.getLoggedUser();
		User liked = userService.getUser(likedId);
		LOGGER.info("Creating swipe for users: " + liker.getUsername() + ", and " + liked.getUsername());
		
		if(getExistingSwipeForUsers(liker.getId(), liked.getId()) != null) {
			LOGGER.info(liker.getUsername() + "have already swiped " + liked.getUsername());
			throw new DataBaseManipulationException(liker.getUsername() + "have already swiped " + liked.getUsername());
		}
		
		Swipe swipe = new Swipe(isLiked, new Date(), liker, liked);
		swipeRepository.save(swipe);
		
		LOGGER.info("Swipe is successfully created");
		
		if(!isLiked) {
			return;
		}
			
		Swipe otherUserSwiped = this.getExistingSwipeForUsers(likedId, liker.getId());
		if(otherUserSwiped != null && otherUserSwiped.getIsLiked()) {
			LOGGER.info(liked.getUsername() + "have already liked " + liker.getUsername() + ". Creating new mathc");
			UserMatch match = new UserMatch(new Date(), liker, liked);
			UserMatch userMatch  = matchService.createMatch(match);
			notificationService.notifyMatch(userMatch);
			LOGGER.info("Mathc is successfully created");
		}
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public Swipe getExistingSwipeForUsers(Long participant1, Long participant2) {
		LOGGER.info("Fetching existing swipe for users with id: " + participant1 + ", and " + participant2);
		Swipe existingSwipe = swipeRepository.getSwipeByLikerIdAndLikedId(participant1, participant2);
		return existingSwipe;
	}

}
