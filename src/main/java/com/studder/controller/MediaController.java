package com.studder.controller;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		for(Media singleMedia : media) {
					BufferedImage originalImage;
					MediaDto mediaDto = mediaService.getMedia(singleMedia.getId());
					try {
						originalImage = ImageIO.read(new ByteArrayInputStream(mediaDto.getBytes()));
						originalImage = resize(originalImage, 100, 100);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write( originalImage, "jpg", baos );
						baos.flush();
						byte[] imageInByte = baos.toByteArray();
						baos.close();
						String base64Encoded = DatatypeConverter.printBase64Binary(imageInByte);
						singleMedia.setEncodedImage(base64Encoded);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			return media;
		}
	
  @GetMapping("/me")
	public List<Media> getMediaForMe(){		
    if(userService.getLoggedUser() == null) {
			return null;
    }
		User user = userService.getLoggedUser();
		if(user != null) {
			List<Media> media = mediaService.getMediasForUser();
				for(Media singleMedia : media) {
					BufferedImage originalImage;
					MediaDto mediaDto = mediaService.getMedia(singleMedia.getId());
					try {
						originalImage = ImageIO.read(new ByteArrayInputStream(mediaDto.getBytes()));
						originalImage = resize(originalImage, 100, 100);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write( originalImage, "jpg", baos );
						baos.flush();
						byte[] imageInByte = baos.toByteArray();
						baos.close();
						String base64Encoded = DatatypeConverter.printBase64Binary(imageInByte);
						singleMedia.setEncodedImage(base64Encoded);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			return media;
			}
		return null;
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
	
	@GetMapping("/getProfileImage")
	public Media getProfileImage(){
		User user = userService.getLoggedUser();
		Media media = new Media();
		if(user != null) {
			BufferedImage originalImage;
			MediaDto mediaDto = mediaService.getMedia(user.getProfileImage());
			try {
				originalImage = ImageIO.read(new ByteArrayInputStream(mediaDto.getBytes()));
				originalImage = resize(originalImage, 100, 100);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write( originalImage, "jpg", baos );
				baos.flush();
				byte[] imageInByte = baos.toByteArray();
				baos.close();
				String base64Encoded = DatatypeConverter.printBase64Binary(imageInByte);
				media.setEncodedImage(base64Encoded);
				return media;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	@GetMapping("/bitmap/{mediaId}")
	public byte[] getSingleMediaForUser(@NotNull @Valid @PathVariable("mediaId") Long mediaId){
		if(userService.getLoggedUser() != null) {
			Media singleMedia = mediaService.getMediaById(mediaId);
			if(singleMedia != null) {
				BufferedImage originalImage;
				try {
					Resource resource = new ClassPathResource(singleMedia.getPath());
					System.out.println(resource.getFile().exists());
					originalImage = ImageIO.read(resource.getFile());
					originalImage = resize(originalImage, 0, 0);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write( originalImage, "jpg", baos );
					baos.flush();
					byte[] imageInByte = baos.toByteArray();
					baos.close();
					return imageInByte;
				} catch (IOException e) {
					return null;
				}
			}
			return null;
		}
		return null;
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {  
		BufferedImage newImage = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);
		Graphics g = newImage.createGraphics();
		g.drawImage(img, 0, 0, 80, 80, null);
		g.dispose();
		return img;
  }
	
	@PostMapping("/upload/{description}")
	public Media createMedia(@PathVariable("description") @Valid @NotEmpty String description,
			@RequestParam("file") MultipartFile file) throws IOException {
		
		if(userService.getLoggedUser() == null) {
			return null;
		}
		
		Media media = mediaService.createMedia(file.getOriginalFilename(), file.getSize(), file.getBytes(), file.getContentType(),
				description);
		BufferedImage originalImage;
		originalImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
		originalImage = resize(originalImage, 100, 100);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( originalImage, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		String base64Encoded = DatatypeConverter.printBase64Binary(imageInByte);
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
