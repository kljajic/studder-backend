package com.studder.controller;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studder.model.Media;
import com.studder.model.User;
import com.studder.service.MediaService;
import com.studder.service.UserService;

@RestController
@RequestMapping("/media")
public class MediaController {
	
	private final MediaService mediaService;
	private final UserService userService;
	
	public MediaController(MediaService mediaService, UserService userService) {
		this.mediaService = mediaService;
		this.userService = userService;
	}
	
	@GetMapping("/{userId}")
	public List<Media> getMediaForUserId(@NotNull @Valid @PathVariable("userId") Long userId){
		List<Media> media = mediaService.getMediasForUser(userId);
		for(Media singleMedia : media) {
			BufferedImage originalImage;
			Resource resource = new ClassPathResource(singleMedia.getPath());
			try {
				System.out.println(singleMedia.getPath());
				originalImage = ImageIO.read(resource.getFile());
				originalImage = resize(originalImage, 200, 200);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write( originalImage, "jpg", baos );
				baos.flush();
				byte[] imageInByte = baos.toByteArray();
				baos.close();
				String base64Encoded = DatatypeConverter.printBase64Binary(imageInByte);
				singleMedia.setPath(base64Encoded);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return media;
	}
	
	@PostMapping("/setProfileImage")
	public String setProfileImage(@RequestBody Media temp){
		
		User user = userService.getUser(temp.getUser().getId());
		Media media = mediaService.getMedia(temp.getId());
		BufferedImage originalImage;
		Resource resource = new ClassPathResource(media.getPath());
		String ret = "-1";
		
		try {
			System.out.println(media.getPath());
			originalImage = ImageIO.read(resource.getFile());
			originalImage = resize(originalImage, 200, 200);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( originalImage, "jpg", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			ret = DatatypeConverter.printBase64Binary(imageInByte);
			user.setProfileImage(ret);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	
	@GetMapping("/bitmap/{mediaId}")
	public byte[] getSingleMediaForUser(@NotNull @Valid @PathVariable("mediaId") Long mediaId){
		Media singleMedia = mediaService.getMedia(mediaId);
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
				// TODO Auto-generated catch block
				return null;
			}
			
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
	
}
