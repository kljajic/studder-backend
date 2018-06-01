package com.studder.serviceimpl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studder.exception.DataBaseManipulationException;
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

	private static Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);
	
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
		LOGGER.info("Creating new message for mathc " + matchId + ". Sender: " + sender.getUsername());
		message.setMatch(match);
		message.setStatus(MessageStatus.DELIVERED);
		message.setTimeRecived(new Date());
		message.setSender(sender);
		messageRepository.save(message);
		
		LOGGER.info("Match for message updating -> lastMessage, lastMessageSeen for matchId " + matchId);
		match.setLastMessage(message.getText());
		match.setLastMessageSeen(false);
		match.setLastMessageDate(message.getTimeRecived());;
		matchService.createMatch(match);
		LOGGER.info("Match for message updated -> lastMessage, lastMessageSeen for matchId " + matchId);
		
		LOGGER.info("Message for " + matchId + " is successfully created");
	}

	@Override
	public Message getMessage(Long messageId) {
		LOGGER.info("Fetching message with id " + messageId);
		Message message = messageRepository.getOne(messageId);
		LOGGER.info("Message with id" + messageId + " is successfully fetched");
		return message;
	}

	@Override
	public List<Message> getMessagesByMatchId(Long matchId) {
		LOGGER.info("Fetching messages for match with id " + matchId);
		List<Message> messages = messageRepository.getMessagesByMatchId(matchId);
		LOGGER.info("Messages for match with id " + matchId + " are successfully fetched");
		return messages;
	}

	@Override
	public void updateMessage(Message message) {
		if(message.getId() == null)
			return;
		Message updateMessage = messageRepository.getOne(message.getId());
		LOGGER.info("Updating message for mathc " + message.getMatch().getId() + " whose sender is "
				+ message.getSender().getId());
		
		User user = userService.getLoggedUser();
		if(!updateMessage.getSender().getId().equals(user.getId())) {
			LOGGER.info("User " + user.getUsername() + " is not allowed to edit this message");
			throw new DataBaseManipulationException(
					"User " + user.getUsername() + " is not allowed to edit this message");
		}
		
		updateMessage.setText(message.getText());
		updateMessage.setStatus(MessageStatus.DELIVERED);
		updateMessage.setTimeRecived(new Date());
		messageRepository.save(updateMessage);
		LOGGER.info("Message with id " + updateMessage.getId() + " is successfully updated");
	}

	@Override
	public void deleteMessage(Long messageId) {
		LOGGER.info("Deleting message with id " + messageId);
		messageRepository.deleteById(messageId);
		LOGGER.info("Message with id " + messageId + " is successfully deleted");
	}

}
