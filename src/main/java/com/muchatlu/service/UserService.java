package com.muchatlu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muchatlu.model.User;
import com.muchatlu.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	
	public User register(User user) {
		userRepo.save(user);
		return user;
	}
	
	public User login(User user) {
		return userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
	}
	
	public User getUserBySessionId(String sessionId) {
		Optional<User> userModel =  userRepo.findBySessionId(sessionId);
		if(userModel.isPresent()) {
			return userModel.get();
		}else
			return null;
		
	}
	
	@Transactional
	public int updateSessionIdByUserId(String sessionId,Long userId) {
		return userRepo.updateSessionIdForAUser(sessionId, userId);
	}
	
	public Optional<User> getUserByUsername(String email) {
		return userRepo.findByEmail(email);
	}
	
	public List<User> getAllUsers(Long UserId){
		List<User> list = new ArrayList<>();
		userRepo.findAll().forEach(list::add);
		return list;
	}
	
	public List<User> saveAllUsers(List<User> users){
		return userRepo.saveAll(users);
	}
	
	public User getUserById(Long id) {
		Optional<User> userModel = userRepo.findById(id);
		if(userModel.isPresent()) {
			return userModel.get();
		}else
			return null;
	}
}
