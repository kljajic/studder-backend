package com.studder.service;

import java.util.List;

import com.studder.dto.MediaDto;
import com.studder.model.Media;

public interface MediaService {

	void createMedia(String fileName, Long size, byte[] bytes, String description, String contentType);
	
	MediaDto getMedia(Long mediaId);
	
	List<Media> getMediasForUser();
	
	void deleteMedia(Long mediaId);
	
}
