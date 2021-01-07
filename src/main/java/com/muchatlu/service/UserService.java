package com.muchatlu.service;

import java.util.ArrayList;
import java.util.List;

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
		return userRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	
	public List<UserModel> getAllUsers(Long UserId){
		List<UserModel> list = new ArrayList<>();
		userRepo.findAll().forEach(list::add);
		return list;
	}
}
