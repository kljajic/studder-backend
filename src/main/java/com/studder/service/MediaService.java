package com.studder.service;

import java.util.List;

import com.studder.model.Media;

public interface MediaService {

	void createMedia(Media media);
	
	Media getMedia(Long mediaId);
	
	List<Media> getMediasForUser(Long userId);
	
	void deleteMedia(Long mediaId);
	
}
