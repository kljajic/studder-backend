package com.studder.service;

import com.studder.model.Message;
import com.studder.model.UserMatch;

public interface NotificationService {
	
	void notifyMessage(Message mess);
	void notifyMatch(UserMatch match);
	
}
