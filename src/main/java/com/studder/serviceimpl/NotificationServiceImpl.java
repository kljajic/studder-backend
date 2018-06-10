package com.studder.serviceimpl;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.notification.OAuth2Google;
import com.studder.model.Message;
import com.studder.model.User;
import com.studder.model.UserDevice;
import com.studder.model.UserMatch;
import com.studder.service.NotificationService;
import com.studder.service.UserDeviceService;
@Service
public class NotificationServiceImpl implements NotificationService{
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	
	public static final String NOTIFICATION_MATCH = "match";
	public static final String NOTIFICATION_MESSAGE = "message";
	
	
	@Value("${firebase.urlsomething}")
	public String url;
	
	private final UserDeviceService userDeviceService;
	private final RestTemplate restTemplate;
	
	public NotificationServiceImpl(UserDeviceService userDeviceService, RestTemplate restTemplate) {
		this.userDeviceService = userDeviceService;
		this.restTemplate = restTemplate;
	}
	
	@Override
	public void notifyMessage(Message mess) {
		
		LOGGER.info("Notifying a message for match id " + mess.getMatch().getId());
		
		User receiver;		
		
		if(mess.getMatch().getParticipant1().getId() == mess.getSender().getId()) {
			receiver = mess.getMatch().getParticipant2();
		} else {
			receiver = mess.getMatch().getParticipant1();
		}
		
		List<UserDevice> devices = userDeviceService.getUserDeviceByUserId(receiver.getId());
		
		devices.stream().forEach(dev -> {
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
			
			try {
				headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + OAuth2Google.getAccessToken());
				System.out.println(OAuth2Google.getAccessToken());
			} catch (IOException e1) {
				LOGGER.info("Exception notifying message for match id " + mess.getMatch().getId() + " failed to get OAuth2Google access token");
			}
			
			try {
				JSONObject body = new JSONObject();
				JSONObject message = new JSONObject();
				
				JSONObject notification = new JSONObject();
				
				notification.put("title", mess.getSender().getName());
				notification.put("body", mess.getText());
				
				JSONObject data = new JSONObject();
				data.put("userId", Long.toString(receiver.getId()));
				data.put("matchId", Long.toString(mess.getMatch().getId()));
				data.put("type", "message");
				//data.put("image", mediaService.convertImageToString(mess.getSender().getProfileImage(), 100, 1000));
				data.put("message", mess.getText());
				
				message.put("token", dev.getDeviceToken());
				message.put("data", data);
				message.put("notification", notification);
				body.put("message", message);
				
				HttpEntity<String> req = new HttpEntity<>(body.toString(), headers);
				System.out.println(body.toString());
				
				String resp = restTemplate.postForObject(url, req, String.class);
				
				System.out.println(resp);
				LOGGER.info("Response for message match id " + mess.getMatch().getId() + " and message id "+ mess.getId() + " --> response: " + resp);
				
			} catch (HttpClientErrorException e) {
				LOGGER.info("HttpClientError exception for message match id " + mess.getMatch().getId() + " and message id "+ mess.getId());
				e.printStackTrace();
			} catch (JSONException e) {
				LOGGER.info("JSON creating exception for message match id " + mess.getMatch().getId() + " and message id "+ mess.getId());
				e.printStackTrace();
			} 
			
			LOGGER.info("Message notifications sent " + mess.getMatch().getId() + " and message id "+ mess.getId());
		});
		
	}

	@Override
	public void notifyMatch(UserMatch match) {
		
		LOGGER.info("Notifying a message for match id " + match.getId());
		
		
		List<UserDevice> devicesParticipant1 = userDeviceService.getUserDeviceByUserId(match.getParticipant1().getId());
		List<UserDevice> devicesParticipant2 = userDeviceService.getUserDeviceByUserId(match.getParticipant2().getId());
		
		devicesParticipant1.stream().forEach(dev -> {
			
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
			
			try {
				headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + OAuth2Google.getAccessToken());
				System.out.println(OAuth2Google.getAccessToken());
			} catch (IOException e1) {
				LOGGER.info("Exception notifying for match id " + match.getId() + " failed to get OAuth2Google access token");
			}
			
			try {
				JSONObject body = new JSONObject();
				JSONObject message = new JSONObject();
				
				JSONObject notification = new JSONObject();
				
				notification.put("title", "New match with" + match.getParticipant2().getName());
				notification.put("body", "Write your first message"); 
				
				JSONObject data = new JSONObject();
				data.put("userId", Long.toString(match.getParticipant2().getId()));
				data.put("matchId", Long.toString(match.getId()));
				data.put("type", "match");
				//data.put("image", mediaService.convertImageToString(match.getParticipant2().getProfileImage(), 100, 100));
				
				message.put("token", dev.getDeviceToken());
				message.put("data", data);
				message.put("notification", notification);
				body.put("message", message);
				
				HttpEntity<String> req = new HttpEntity<>(body.toString(), headers);
				System.out.println(body.toString());
				
				String resp = restTemplate.postForObject(url, req, String.class);
				
				LOGGER.info("Response for match notification with id " + match.getId() + " --> response: " + resp);
				
			} catch (HttpClientErrorException e) {
				LOGGER.info("HttpClientError exception for message match id " + match.getId());
			} catch (JSONException e) {
				LOGGER.info("JSON creating exception for message match id " + match.getId());
			} 
			
			LOGGER.info("Message notifications sent " + match.getId() + " and message id "+ match.getId());
		});
		
		
		devicesParticipant2.stream().forEach(dev -> {
			
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
			
			try {
				headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + OAuth2Google.getAccessToken());
				System.out.println(OAuth2Google.getAccessToken());
			} catch (IOException e1) {
				LOGGER.info("Exception notifying for match id " + match.getId() + " failed to get OAuth2Google access token");
			}
			
			try {
				JSONObject body = new JSONObject();
				JSONObject message = new JSONObject();
				
				JSONObject notification = new JSONObject();
				
				notification.put("title", "New match with" + match.getParticipant1().getName());
				notification.put("body", "Write your first message"); 
				
				JSONObject data = new JSONObject();
				data.put("userId", Long.toString(match.getParticipant1().getId()));
				data.put("matchId", Long.toString(match.getId()));
				data.put("type", "match");
				//data.put("image", mediaService.convertImageToString(match.getParticipant1().getProfileImage(), 100, 1000));
				
				message.put("token", dev.getDeviceToken());
				message.put("data", data);
				message.put("notification", notification);
				body.put("message", message);
				
				HttpEntity<String> req = new HttpEntity<>(body.toString(), headers);
				System.out.println(body.toString());
				
				String resp = restTemplate.postForObject(url, req, String.class);
				
				LOGGER.info("Response for match notification with id " + match.getId() + " --> response: " + resp);
				
			} catch (HttpClientErrorException e) {
				LOGGER.info("HttpClientError exception for message match id " + match.getId());
			} catch (JSONException e) {
				LOGGER.info("JSON creating exception for message match id " + match.getId());
			} 
			
			LOGGER.info("Message notifications sent " + match.getId() + " and message id "+ match.getId());
		});
		
	}

}
