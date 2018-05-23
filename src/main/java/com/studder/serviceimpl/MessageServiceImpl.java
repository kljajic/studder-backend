package com.studder.serviceimpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studder.model.Message;
import com.studder.model.MessageStatus;
import com.studder.model.User;
import com.studder.model.UserMatch;
import com.studder.repository.MessageRepository;
import com.studder.service.MatchService;
import com.studder.service.MessageService;
import com.studder.service.UserService;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

	private final MessageRepository messageRepository;
	private final MatchService matchService;
	private final UserService userService;
	
	@Autowired
	public MessageServiceImpl(MessageRepository messageRepository, 
							  MatchService matchService,
							  UserService userService) {
		this.messageRepository = messageRepository;
		this.matchService = matchService;
		this.userService = userService;
	}
	
	@Override
	public void createMessage(Message message, Long matchId) {
		UserMatch match = matchService.getMathc(matchId);
		
		User sender = userService.getLoggedUser();
		message.setMatch(match);
		message.setStatus(MessageStatus.DELIVERED);
		message.setTimeRecived(new Date());
		message.setSender(sender);
		messageRepository.save(message);
	}

	@Override
	public Message getMessage(Long messageId) {
		return messageRepository.getOne(messageId);
	}

	@Override
	public List<Message> getMessagesByMatchId(Long matchId) {
		return messageRepository.getMessagesByMatchId(matchId);
	}

	@Override
	public void updateMessage(Message message) {
		if(message.getId() == null)
			return;
		Message updateMessage = messageRepository.getOne(message.getId());
		updateMessage.setText(message.getText());
		updateMessage.setStatus(MessageStatus.DELIVERED);
		updateMessage.setTimeRecived(new Date());
		messageRepository.save(updateMessage);
	}

	@Override
	public void deleteMessage(Long messageId) {
		messageRepository.deleteById(messageId);
	}

}
