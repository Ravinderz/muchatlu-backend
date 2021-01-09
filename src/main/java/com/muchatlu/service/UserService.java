package com.muchatlu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
