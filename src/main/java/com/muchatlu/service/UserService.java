package com.muchatlu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muchatlu.model.UserModel;
import com.muchatlu.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	
	public UserModel register(UserModel user) {
		userRepo.save(user);
		return user;
	}
	
	public UserModel login(UserModel user) {
		return userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
	}
	
	public UserModel getUserBySessionId(String sessionId) {
		Optional<UserModel> userModel =  userRepo.findBySessionId(sessionId);
		if(userModel.isPresent()) {
			return userModel.get();
		}else
			return null;
		
	}
	
	@Transactional
	public int updateSessionIdByUserId(String sessionId,Long userId) {
		return userRepo.updateSessionIdForAUser(sessionId, userId);
	}
	
	public Optional<UserModel> getUserByUsername(String email) {
		return userRepo.findByEmail(email);
	}
	
	public List<UserModel> getAllUsers(Long UserId){
		List<UserModel> list = new ArrayList<>();
		userRepo.findAll().forEach(list::add);
		return list;
	}
	
	public UserModel getUserById(Long id) {
		Optional<UserModel> userModel = userRepo.findById(id);
		if(userModel.isPresent()) {
			return userModel.get();
		}else
			return null;
	}
}
