package com.studder.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.studder.model.Media;
import com.studder.model.User;
import com.studder.service.MediaService;
import com.studder.service.UserService;

@RestController
@RequestMapping("/media")
public class MediaController {
	
	private final MediaService mediaService;
	private final UserService userService;
	
  @Autowired
	public MediaController(MediaService mediaService, UserService userService) {
		this.mediaService = mediaService;
		this.userService = userService;
	}

	@GetMapping("/{userId}")
	public List<Media> getMediaForUserId(@PathVariable("userId") Long userId){
		List<Media> media = mediaService.getMediasForUser(userId);
		
		media.stream().forEach(singleMedia -> {
			String base64Encoded = mediaService.convertImageToString(singleMedia.getId(), 200, 200);
			singleMedia.setEncodedImage(base64Encoded);
		});
		
		return media;
	}
	
	@GetMapping("/me")
	public List<Media> getMediaForMe(){		
	    
		if(userService.getLoggedUser() == null) {
				return null;
	    }
		
	    List<Media> media = mediaService.getMediasForUser();
		
		media.stream().forEach(singleMedia ->{
			String base64Encoded = mediaService.convertImageToString(singleMedia.getId(), 200, 200);
			singleMedia.setEncodedImage(base64Encoded);
		});
		
		return media;
	}
	
	
	@PostMapping("/setProfileImage/{mediaId}")
	public boolean setProfileImage(@PathVariable("mediaId") @NotNull @Valid Long mediaId){
		
		if(userService.getLoggedUser() == null) {
			return false;
		}
		
		User user = userService.getLoggedUser();
		user.setProfileImage(mediaId);
		
		return true;
	}
	
	@GetMapping("/getProfileImage/{userId}")
	public Media getProfileImage(@PathVariable("userId") Long userId){
		
		User user = userService.getLoggedUser();
		
		if(user == null) {	return null; }
		
		String base64Encoded;
		
		User requestedUser = userService.getUser(userId);
		Media media = mediaService.getMediaById(requestedUser.getProfileImage());
		base64Encoded = mediaService.convertImageToString(userId, 200, 200);
		media.setEncodedImage(base64Encoded);
		
		return media;
	}
	
	@GetMapping("/getProfileImage")
	public Media getProfileImage(){
		
		User user = userService.getLoggedUser();
		Media media = mediaService.getMediaById(user.getProfileImage());
		
		if(user == null || media == null) {	return null; }
		
		String base64Encoded;
		base64Encoded = mediaService.convertImageToString(user.getProfileImage(), 200, 200);
		media.setEncodedImage(base64Encoded);
		
		return media;
	}
	
	@PostMapping("/upload/{description}")
	public Media createMedia(@PathVariable("description") @Valid @NotEmpty String description,
			@RequestParam("file") MultipartFile file) throws IOException {
		
		if(userService.getLoggedUser() == null) {
			return null;
		}
		
		Media media = mediaService.createMedia(file.getOriginalFilename(), file.getSize(), file.getBytes(), file.getContentType(),
				description);
		
		String base64Encoded = mediaService.convertImageToString(media.getId(), 200, 200);
		
		media.setEncodedImage(base64Encoded);
		return media;
	}
	
	@GetMapping("/image/{mediaId}")
	public String getMedia(@PathVariable("mediaId") @Valid Long mediaId) throws IOException {
		return new String(mediaService.getMediaBytes(mediaId));
	}
	
	@DeleteMapping("/delete/{mediaId}")
	public void deleteMedia(@PathVariable("mediaId") @Valid @NonNull Long mediaId) {
		if(userService.getLoggedUser() == null) {
			return;
		}
		mediaService.deleteMedia(mediaId);
	}
	
}
