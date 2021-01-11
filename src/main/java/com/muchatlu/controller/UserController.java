package com.muchatlu.controller;

import java.util.List;

import javax.swing.event.TableColumnModelListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.muchatlu.model.FriendRequest;
import com.muchatlu.model.FriendRequestModel;
import com.muchatlu.model.MyUserDetails;
import com.muchatlu.model.User;
import com.muchatlu.service.FriendRequestService;
import com.muchatlu.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	UserService userService;
	
	@Autowired
	FriendRequestService friendRequestService;
	
	@Autowired
	private SimpMessagingTemplate simpMessageTemplate;
	
	
	@PostMapping("/register")
	public User register(@RequestBody User user){
		return userService.register(user);
	}
	
	@PostMapping("/login")
	public MyUserDetails login(@RequestBody User user) throws Exception{
		Authentication principle = null;
		try {
			principle = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
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
	public List<User> getAllUsers(@PathVariable Long userId){
		return userService.getAllUsers(userId);
	}
	
	@GetMapping("/getAllFriends/{userId}")
	public User getFriendsOfUser(@PathVariable Long userId){
		return userService.getUserById(userId);
	}
	
	@PostMapping("/sendFriendRequest")
	public FriendRequest sendFriendRequest(@RequestBody FriendRequest request) {
		request = friendRequestService.saveFriendRequest(request);
		System.out.println(request);
		simpMessageTemplate.convertAndSend("/topic/"+request.getRequestToUserId()+".friendRequest",request);
		return request;
	}
	
	@PostMapping("/updateFriendRequest")
	public FriendRequestModel updateFriendRequest(@RequestBody FriendRequest request) {
		FriendRequestModel model = friendRequestService.updateFriendRequest(request);
		simpMessageTemplate.convertAndSend("/topic/"+request.getRequestToUserId()+".friendRequest",model);
		simpMessageTemplate.convertAndSend("/topic/"+request.getRequestFromUserId()+".friendRequest",model);
		return model;
	}
	
}
