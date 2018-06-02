package com.studder.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studder.model.Media;
import com.studder.repository.MediaRepository;
import com.studder.service.MediaService;

@Service
@Transactional
public class MediaServiceImpl implements MediaService {

	private final MediaRepository mediaRepository;
	
	@Autowired
	public MediaServiceImpl(MediaRepository mediaRepository) {
		this.mediaRepository = mediaRepository;
	}
	
	@Override
	public void createMedia(Media media) {
		mediaRepository.save(media);
	}

	@Override
	public Media getMedia(Long mediaId) {
		Optional<Media> media = mediaRepository.findById(mediaId);
		return media.get();
	}

	@Override
	public List<Media> getMediasForUser(Long userId) {
		return mediaRepository.getMediaByUserId(userId);
	}

	@Override
	public void deleteMedia(Long mediaId) {
		mediaRepository.deleteById(mediaId);
	}

}
