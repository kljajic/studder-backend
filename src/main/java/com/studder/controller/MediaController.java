package com.studder.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.studder.dto.MediaDto;
import com.studder.model.Media;
import com.studder.service.MediaService;

@RestController
@RequestMapping("/media")
public class MediaController {

	private final MediaService mediaService;
	
	@Autowired
	public MediaController(MediaService mediaService) {
		this.mediaService = mediaService;
	}
	
	@PostMapping("/{description:.+}")
	public void createMedia(@PathVariable("description") @Valid @NotEmpty String description,
			@RequestParam("file") MultipartFile file) throws IOException {
		mediaService.createMedia(file.getOriginalFilename(), file.getSize(), file.getBytes(), file.getContentType(),
				description);
	}
	
	@GetMapping("/{mediaId}")
	public ResponseEntity<?> getMedia(@PathVariable("mediaId") @Valid @NonNull Long mediaId) {
		MediaDto media = mediaService.getMedia(mediaId);
		
		InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(media.getBytes()));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment: filename=\"%s\"", media.getFilename()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		return ResponseEntity.ok().headers(headers).contentLength(media.getSize())
				.contentType(MediaType.parseMediaType(media.getContentType())).body(resource);
	}
	
	@GetMapping
	public List<Media> getMediaForUser() {
		return mediaService.getMediasForUser();
	}
	
	@DeleteMapping("/{mediaId}")
	public void deleteMedia(@PathVariable("mediaId") @Valid @NonNull Long mediaId) {
		mediaService.deleteMedia(mediaId);
	}
	
}
