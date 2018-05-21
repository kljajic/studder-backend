package com.studder.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studder.model.User;
import com.studder.repository.UserRepository;
import com.studder.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public void createUser(User user) {
		userRepository.save(user);
	}

	@Override
	public User getUser(Long userId) {
		return userRepository.getOne(userId);
	}

	@Override
	public void updateUser(User user) {
		
	}

	@Override
	public void deactivateAccount() {
		//get logged user from context holder and deactivate its account
	}

}
