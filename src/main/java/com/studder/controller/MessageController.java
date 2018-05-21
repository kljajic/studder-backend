package com.studder.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studder.model.Message;
import com.studder.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

	private final MessageService messageService;
	
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@PostMapping("/{matchId}/{senderId}")
	public void createMessage(@RequestBody @Valid Message message,
			@PathVariable("matchId") @NotNull @Valid Long matchId,
			@PathVariable("senderId") @NotNull @Valid Long senderId) {
		messageService.createMessage(message, matchId, senderId);
	}
	
	@GetMapping("/{messageId}")
	public Message getMessage(@PathVariable("messageId") @NotNull @Valid Long messageId) {
		return messageService.getMessage(messageId);
	}
	
	@GetMapping("/match/{matchId}")
	public List<Message> getMessages(@PathVariable("matchId") @NotNull @Valid Long matchId) {
		return messageService.getMessagesByMatchId(matchId);
	}
	
	@PutMapping
	public void updateMessage(@RequestBody @Valid Message message) {
		messageService.updateMessage(message);
	}
	
	@DeleteMapping("/{messageId}")
	public void deleteMessage(@PathVariable("messageId") @NotNull @Valid Long messageId) {
		messageService.deleteMessage(messageId);
	}
	
}
