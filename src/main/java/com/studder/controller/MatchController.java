package com.studder.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.studder.dto.MediaDto;
import com.studder.model.Media;
import com.studder.model.User;
import com.studder.model.UserMatch;
import com.studder.service.MatchService;
import com.studder.service.MediaService;
import com.studder.service.UserService;

@RestController
@RequestMapping("/matches")
public class MatchController {

	private final MatchService matchService;
	private final UserService userService;
	private final MediaService mediaService;
	
	@Autowired
	public MatchController(MatchService matchService, UserService userService, MediaService mediaService) {
		this.matchService = matchService;
		this.userService = userService;
		this.mediaService = mediaService;
	}
	
	@GetMapping
	public List<UserMatch> getMatches() {
		return matchService.getMathces();
	}
	
	@DeleteMapping("/{matchId}")
	public void deleteMatch(@PathVariable("matchId") @NotNull @Valid Long matchId) {
		matchService.deleteMathc(matchId);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/getMatchesMe")
	public List<UserMatch> getMatchesMe(){
		User user = userService.getLoggedUser();
		if(user != null) {
			List<UserMatch> matches = matchService.getMatches(user.getId());
			
			for(int i = 0;i < matches.size();i++) {
				if(!matches.get(i).getParticipant1().getId().equals(user.getId())) {
					MediaDto mediaDto = mediaService.getMedia(matches.get(i).getParticipant1().getProfileImage());
					try {
						BufferedImage originalImage;
						originalImage = ImageIO.read(new ByteArrayInputStream(mediaDto.getBytes()));
						originalImage = MediaController.resize(originalImage, 100, 100);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write( originalImage, "jpg", baos );
						baos.flush();
						byte[] imageInByte = baos.toByteArray();
						baos.close();
						String base64Encoded = DatatypeConverter.printBase64Binary(imageInByte);
						matches.get(i).getParticipant1().setProfileImageEncoded(base64Encoded);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					MediaDto mediaDto = mediaService.getMedia(matches.get(i).getParticipant2().getProfileImage());
					try {
						BufferedImage originalImage;
						originalImage = ImageIO.read(new ByteArrayInputStream(mediaDto.getBytes()));
						originalImage = MediaController.resize(originalImage, 100, 100);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write( originalImage, "jpg", baos );
						baos.flush();
						byte[] imageInByte = baos.toByteArray();
						baos.close();
						String base64Encoded = DatatypeConverter.printBase64Binary(imageInByte);
						matches.get(i).getParticipant2().setProfileImageEncoded(base64Encoded);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			return matches;
		}
		return null;
	}
	
}
