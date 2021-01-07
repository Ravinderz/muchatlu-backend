package com.muchatlu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.muchatlu.model.UserModel;
import com.muchatlu.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping("register")
	public UserModel register(@RequestBody UserModel user){
		return userService.register(user);
	}
	
	@PostMapping("login")
	public UserModel login(@RequestBody UserModel user){
		return userService.login(user);
	}
	
	@GetMapping("getAllUsers/{userId}")
	public List<UserModel> getAllUsers(@PathVariable Long userId){
		return userService.getAllUsers(userId);
	}
}
