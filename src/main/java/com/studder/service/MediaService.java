package com.studder.service;

import java.io.IOException;
import java.util.List;

import com.studder.dto.MediaDto;
import com.studder.model.Media;

public interface MediaService {

	Media createMedia(String fileName, Long size, byte[] bytes, String description, String contentType);
	
	Media getMediaById(Long mediaId);
	
	MediaDto getMedia(Long mediaId);
	
	byte[] getMediaBytes(Long mediaId) throws IOException;
	
	List<Media> getMediasForUser();
	
	List<Media> getMediasForUser(Long userId);
	
	void deleteMedia(Long mediaId);
	
	String convertImageToString(Long profileImageId, int width, int height);
	
}
