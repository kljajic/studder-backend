package com.studder.serviceimpl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studder.dto.MediaDto;
import com.studder.exception.DataBaseManipulationException;
import com.studder.exception.FileManipulationException;
import com.studder.model.Media;
import com.studder.model.User;
import com.studder.repository.MediaRepository;
import com.studder.service.FileService;
import com.studder.service.MediaService;
import com.studder.service.UserService;

@Service
@Transactional
public class MediaServiceImpl implements MediaService {

	private static final String FORMAT_JPG = "jpg";
	
	private static Logger LOGGER = LoggerFactory.getLogger(MediaServiceImpl.class);

	private final MediaRepository mediaRepository;
	private final UserService userService;
	private final FileService fileService;
	
	@Autowired
	public MediaServiceImpl(MediaRepository mediaRepository, UserService userService, FileService fileService) {
		this.mediaRepository = mediaRepository;
		this.userService = userService;
		this.fileService = fileService;
	}
	
	@Override
	public Media createMedia(String filename, Long size, byte[] bytes, String contentType, String description) {
		User user = userService.getLoggedUser();
		LOGGER.info("Saving media for user with username " + user.getUsername());
		
		int numberOfMedia = getMediasForUser(user.getId()).size();
		
		String mediaName = filename.split("\\.")[0] + "_" + numberOfMedia + "." + filename.split("\\.")[1];
		Media media = new Media(mediaName, contentType, size, description, new Date(), user.getUsername(), user);
		
		try {
			fileService.saveFile(user.getUsername(), mediaName, bytes);
			mediaRepository.save(media);
			LOGGER.info("Media " + filename + " is successfully saved");
			return media;
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("Media " + filename + " not saved to database " + e.getMessage());
			
			fileService.deleteFile(user.getUsername(), mediaName);
			mediaRepository.deleteById(mediaRepository.findMediaByName(mediaName).getId());
			
			throw new FileManipulationException("Media " + mediaName + " not saved to database " + e.getMessage());
		}
		
	}

	@Override
	public MediaDto getMedia(Long mediaId) {
		LOGGER.info("Fetching media with id " + mediaId);
		Media media = mediaRepository.getOne(mediaId);
		
		if(media == null) {
			LOGGER.error("Media with id " + mediaId + " doesn't exist");
			throw new DataBaseManipulationException("Media with id " + mediaId + " doesn't exist");
		}
		
		try {
			byte[] mediaBytes = fileService.readFile(media.getPath(), media.getName());
			LOGGER.info("Media with id " + mediaId + " is successfully fetched");
			return new MediaDto(media.getName(), media.getContentType(), mediaBytes.length, mediaBytes);
		} catch (IOException e) {
			LOGGER.error("Error while fetching file " + media.getName() + ". Reason: " + e.getMessage());
			throw new FileManipulationException(
					"Error while fetching file " + media.getName() + ". Reason: " + e.getMessage());
		}
	
	}

	@Override
	public List<Media> getMediasForUser() {
		User user = userService.getLoggedUser();
		LOGGER.info("Fetching media for user with username " + user.getUsername());
		List<Media> userMedias = mediaRepository.getMediaByUserId(user.getId());
		LOGGER.info("User medias are successfully fetched");
		return userMedias;
  }
	
  @Override
  public List<Media> getMediasForUser(Long userId) {
		User user = userService.getUser(userId);
		LOGGER.info("Fetching media for user with username " + user.getUsername());
		List<Media> userMedias = mediaRepository.getMediaByUserId(user.getId());
		LOGGER.info("User medias are successfully fetched");
		return userMedias;
	}

	@Override
	public void deleteMedia(Long mediaId) {
		LOGGER.info("Deleting media with id " + mediaId);
		Optional<Media> media = mediaRepository.findById(mediaId);
		
		if(!media.isPresent()) {
			LOGGER.error("Media with id " + mediaId + " doesn't exist");
			throw new DataBaseManipulationException("Media with id " + mediaId + " doesn't exist");
		}
		
		fileService.deleteFile(media.get().getPath(), media.get().getName());
		mediaRepository.deleteById(mediaId);
		LOGGER.info("Media with id " + mediaId + "is successfully deleted");
	}

	@Override
	public Media getMediaById(Long mediaId) {
		LOGGER.info("Fetching media with id " + mediaId);
		return mediaRepository.getOne(mediaId);
	}

	@Override
	public byte[] getMediaBytes(Long mediaId) throws IOException {
		Media media = this.getMediaById(mediaId);
		return fileService.readFile(media.getPath(), media.getName());
	}

	@Override
	public String convertImageToString(Long profileImageId, int width, int height) {
		LOGGER.info("Profile image converted to string with id " + profileImageId);
		String base64Encoded;
		MediaDto mediaDto = getMedia(profileImageId);
		try {
			BufferedImage originalImage;
			originalImage = ImageIO.read(new ByteArrayInputStream(mediaDto.getBytes()));
			originalImage = resize(originalImage, width, height);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, FORMAT_JPG, baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			base64Encoded = DatatypeConverter.printBase64Binary(imageInByte);
			return base64Encoded;
		} catch (Exception e) {
			LOGGER.info("Profile image failed to convert to string with id " + profileImageId + "; error: " + e.getMessage());
		}
		return null;
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {  
		
		if(newW == -1 || newH == -1) {
			return img;
		}
		BufferedImage newImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
		Graphics g = newImage.createGraphics();
		g.drawImage(img, 0, 0, newW, newH, null);
		g.dispose();
		return img;
  }

}
