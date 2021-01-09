package com.muchatlu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.muchatlu.model.UserModel;
import com.muchatlu.service.UserOnlineService;
import com.muchatlu.service.UserService;

import io.reactivex.Observable;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserOnlineService onlineService;

	@PostMapping("/register")
	public UserModel register(@RequestBody UserModel user){
		return userService.register(user);
	}
	
	@PostMapping("/login")
	public UserModel login(@RequestBody UserModel user){
		return userService.login(user);
	}
	
	@GetMapping("/getAllUsers/{userId}")
	public List<UserModel> getAllUsers(@PathVariable Long userId){
		return userService.getAllUsers(userId);
	}
	
//	@GetMapping("/getUsersStatus}")
//	public Observable<UserModel> getUserStatus(){
//		return onlineService.getUserOnlineStatus(user);
//	}
}
