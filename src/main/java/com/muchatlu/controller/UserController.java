package com.muchatlu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.muchatlu.model.MyUserDetails;
import com.muchatlu.model.UserModel;
import com.muchatlu.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public UserModel register(@RequestBody UserModel user){
		return userService.register(user);
	}
	
	@PostMapping("/login")
	public MyUserDetails login(@RequestBody UserModel user) throws Exception{
		Authentication principle = null;
		try {
			principle = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
			
			System.out.println("principle :: "+principle);
		}catch(Exception e) {
			throw new Exception("Incorrect username and password");
		}
		
		MyUserDetails details = (MyUserDetails) principle.getPrincipal();
		details.setIsOnline(true);
		details.setSessionId(null);
		details.setPassword(null);
		return details;
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
