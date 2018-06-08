package com.studder.serviceimpl;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studder.model.User;
import com.studder.model.UserMatch;
import com.studder.repository.MatchRepository;
import com.studder.service.MatchService;
import com.studder.service.NotificationService;
import com.studder.service.UserService;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {
	
	@Value("${firebase.session}")
    public String sessionKey;
	
	@Value("${firebase.url}")
	public String url;
	
	private static Logger LOGGER = LoggerFactory.getLogger(MatchServiceImpl.class);
	
	private final MatchRepository matchRepository;
	private final UserService userService;
	private final NotificationService notificationService;
	
	@Autowired
	public MatchServiceImpl(MatchRepository matchRepository, UserService userService, NotificationService notificationService) {
		this.matchRepository = matchRepository;
		this.userService = userService;
		this.notificationService = notificationService;
	}
	
	@Override
	public void createMatch(UserMatch match) {
		LOGGER.info("Creating new mathc for users: " + match.getParticipant1().getUsername() + " and "
				+ match.getParticipant2().getUsername());
		match = matchRepository.save(match);
		notificationService.notifyMatch(match);
		LOGGER.info("Match is successfully created");
	}

	@Override
	@Transactional(readOnly = true)
	public UserMatch getMathc(Long matchId) {
		LOGGER.info("Fetching match with id " + matchId);
		UserMatch match = matchRepository.getOne(matchId);
		LOGGER.info("Match with id " + matchId + " is successfully fetched");
		return match;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserMatch> getMathces() {
		User user = userService.getLoggedUser();
		LOGGER.info("Fetching matches for user " + user.getUsername());
		List<UserMatch> mathces = matchRepository.getMatchesByParticipant1IdOrParticipant2Id(user.getId());
		LOGGER.info("Matches for user " + user.getUsername() + " are successfully fetched");
		return mathces;
	}

	@Override
	public void deleteMathc(Long mathcId) {
		LOGGER.info("Deleting match with id " + mathcId);
		matchRepository.deleteById(mathcId);
		LOGGER.info("Match with id " + mathcId + " is successfully deleted");
	}

	@Override
	public List<UserMatch> getMatches(Long userId) {
		LOGGER.info("Retrieving matches related with user id " + userId);
		List<UserMatch> userMatches =  matchRepository.getMatchesByParticipant1IdOrParticipant2Id(userId);
		LOGGER.info("Matches related with user id " + userId + " successfully retrieved");
		return userMatches;
	}

	public UserMatch getMatch(@NotNull @Valid Long matchId) {
		return matchRepository.getOne(matchId);
	}

}
