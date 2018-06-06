package com.studder.serviceimpl;

import java.util.List;

import javax.validation.constraints.Null;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.notification.Message;
import com.notification.Notification;
import com.studder.model.User;
import com.studder.model.UserDevice;
import com.studder.model.UserMatch;
import com.studder.repository.MatchRepository;
import com.studder.service.MatchService;
import com.studder.service.UserDeviceService;
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
	private final UserDeviceService userDeviceService;
	private final RestTemplate restTemplate;
	
	@Autowired
	public MatchServiceImpl(MatchRepository matchRepository, UserService userService, UserDeviceService userDeviceService, RestTemplate restTemplate) {
		this.matchRepository = matchRepository;
		this.userService = userService;
		this.userDeviceService = userDeviceService;
		this.restTemplate = restTemplate;
	}
	
	@Override
	public void createMatch(UserMatch match) {
		LOGGER.info("Creating new mathc for users: " + match.getParticipant1().getUsername() + " and "
				+ match.getParticipant2().getUsername());
		matchRepository.save(match);
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

	@Override
	public void notifyMatched(UserMatch match) {
		LOGGER.info("Notifying users -> " + match.getParticipant1().getId()+ " and " + match.getParticipant2().getId());
		
		
		HttpEntity<Message> request = new HttpEntity<>(new Message());
		Notification matchNotification = new Notification();
		matchNotification.setTitle("New match with " + match.getParticipant1().getName());
		matchNotification.setMessage("You have been matched, start your conversation with the matched person");
		
		List<UserDevice> devicesParticipant2 = userDeviceService.getUserDeviceByUserId(match.getParticipant2().getId());
		
		devicesParticipant2.stream().forEach(dev -> {
		
			Message message = new Message();;
			message.setNotification(matchNotification);
			message.setToken(dev.getDeviceToken());
			request.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
			request.getHeaders().add(HttpHeaders.AUTHORIZATION, "key=" + sessionKey);
			
			restTemplate.postForObject(url, request, Null.class);
		});
		
		
		matchNotification.setTitle("New match with " + match.getParticipant2().getName());
		
		List<UserDevice> devicesParticipant1 = userDeviceService.getUserDeviceByUserId(match.getParticipant1().getId());
		
		devicesParticipant1.stream().forEach(dev -> {
			
			Message message = new Message();;
			message.setNotification(matchNotification);
			message.setToken(dev.getDeviceToken());
			request.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
			request.getHeaders().add(HttpHeaders.AUTHORIZATION, "key=" + sessionKey);
			
			restTemplate.postForObject(url, request, Null.class);
		});
		
		LOGGER.info("Firebase notified users -> " + match.getParticipant1().getId() + " and " + match.getParticipant2().getId());
		return;
  }

	public UserMatch getMatch(@NotNull @Valid Long matchId) {
		return matchRepository.getOne(matchId);
	}

}
