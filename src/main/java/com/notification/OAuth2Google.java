package com.notification;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.core.io.ClassPathResource;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;

public class OAuth2Google {
	
	public static String getAccessToken() throws IOException {
		
		String[] SCOPES = {"https://www.googleapis.com/auth/cloud-platform", "https://www.googleapis.com/auth/userinfo.email"};
		
		File file = new ClassPathResource("studder-4f254-firebase-adminsdk-7uotu-186dde3408.json").getFile();
		
		GoogleCredential googleCredential = GoogleCredential.fromStream(new FileInputStream(file)).createScoped(Arrays.asList(SCOPES));
		googleCredential.refreshToken();
		return googleCredential.getAccessToken();
	}
	
	public static GoogleCredentials googleCredentials() throws IOException {
		String[] SCOPES = {"https://www.googleapis.com/auth/cloud-platform", "https://www.googleapis.com/auth/userinfo.email"};
		
		File file = new ClassPathResource("studder-4f254-firebase-adminsdk-7uotu-186dde3408.json").getFile();
		
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new FileInputStream(file)).createScoped(Arrays.asList(SCOPES));
		googleCredentials.refreshAccessToken();
		
		return googleCredentials;
	}
}
