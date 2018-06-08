package com.studder.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("/{matchId}")
	public UserMatch getMatch(@PathVariable("matchId") @NotNull @Valid Long matchId) {
		return matchService.getMatch(matchId);
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
					String base64Encoded = mediaService.convertImageToString(matches.get(i).getParticipant1().getProfileImage(), 200, 200);
					matches.get(i).getParticipant1().setProfileImageEncoded(base64Encoded); 
				} else {
					String base64Encoded = mediaService.convertImageToString(matches.get(i).getParticipant2().getProfileImage(), 200, 200);
					matches.get(i).getParticipant2().setProfileImageEncoded(base64Encoded);
				}
			}
			return matches;
		}
		return null;
	}
}
