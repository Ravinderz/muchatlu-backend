package com.muchatlu.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
	public int updateSessionIdByUserId(String sessionId,boolean isOnline,Long userId) {
		return userRepo.updateSessionIdForAUser(sessionId, isOnline, userId);
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
		User user = new User();
		Set<User> set = new HashSet<>();
		if(userModel.isPresent()) {
			user = userModel.get();
			set.addAll(user.getFriends().stream().filter(p -> p instanceof User).collect(Collectors.toList()));
			set.addAll(user.getFriendsTo().stream().filter(p -> p instanceof User).collect(Collectors.toList()));
			user.getFriends().addAll(set);
			return user;
		}else
			return null;
	}
}
