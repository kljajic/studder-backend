package com.studder.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Null;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.notification.Message;
import com.notification.Notification;
import com.notification.OAuth2Google;
import com.studder.model.User;
import com.studder.model.UserDevice;
import com.studder.service.SecurityService;
import com.studder.service.UserDeviceService;
import com.studder.service.UserService;

@RestController
@RequestMapping("/auth")
public class SecurityController {
	
	@Value("${firebase.session}")
    public String sessionKey;
	
	@Value("${firebase.url}")
	public String url;
	
	@Value("${firebase.urlsomething}")
	public String urlsomething;
	
	private final SecurityService securityService;
	private final UserDeviceService userDeviceService;
	private final UserService userService;
	private final RestTemplate restTemplate;
	@Autowired
	public SecurityController(SecurityService securityService, UserDeviceService userDeviceService, UserService userService, RestTemplate restTemplate) {
		this.securityService = securityService;
		this.userDeviceService = userDeviceService;
		this.userService = userService;
		this.restTemplate = restTemplate;
	}
	
	@PostMapping("/login")
	public User login(@RequestBody User user) {
		User u = securityService.login(user);
		userDeviceService.login(u, user.getUserDeviceToken());
		
		User a = userService.findUserByUsername("username1");
		
		
		Notification matchNotification = new Notification();
		matchNotification.setTitle("New match with " + a.getName());
		matchNotification.setMessage("You have been matched, start your conversation with the matched person");
		
		List<UserDevice> devicesParticipant2 = userDeviceService.getUserDeviceByUserId(a.getId());
		
		devicesParticipant2.stream().forEach(dev -> {
			
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
			try {
				headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + OAuth2Google.getAccessToken());
				System.out.println(OAuth2Google.getAccessToken());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HttpEntity<Message> request = new HttpEntity<>(new Message(), headers);
			Message mes = new Message();;
			mes.setNotification(matchNotification);
			mes.setToken(dev.getDeviceToken());
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", a.getId().toString());
			mes.setData(map);
			
			
			try {
				JSONObject body = new JSONObject();
				JSONObject message = new JSONObject();
				
				JSONObject notification = new JSONObject();
				notification.put("title", "JSA Notification");
				notification.put("body", "Happy Message!");
				
				JSONObject data = new JSONObject();
				data.put("Key-1", "JSA Data 1");
				data.put("Key-2", "JSA Data 2");
				
				message.put("token", dev.getDeviceToken());
				message.put("notification", notification);
				message.put("data", data);
				body.put("message", message);
				
				HttpEntity<String> req = new HttpEntity<>(body.toString(), headers);
				System.out.println(body.toString());
				String resp = restTemplate.postForObject(urlsomething, req, String.class);
				
				
				System.out.println(resp);
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				System.out.println(e.getMostSpecificCause().toString());
				System.out.println();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			
		});
		
		return u;
	}
	
	@PostMapping("/logout")
	public void logout(@RequestBody User user) {
		User u = userService.getLoggedUser();
		if(u != null) {
			userDeviceService.logout(u, user.getUserDeviceToken());
		}
		securityService.logout();
	}
	
}
