package com.studder.service;

import java.util.List;

import com.studder.model.Message;

public interface MessageService {

	void createMessage(Message message, Long matchId, Long senderId);
	
	Message getMessage(Long messageId);
	
	List<Message> getMessagesByMatchId(Long matchId);
	
	void updateMessage(Message message);
	
	void deleteMessage(Long messageId);
	
}
