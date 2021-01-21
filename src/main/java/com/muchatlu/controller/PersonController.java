package com.muchatlu.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.muchatlu.dto.ConversationDto;
import com.muchatlu.model.*;
import com.muchatlu.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.muchatlu.service.FriendRequestService;
import com.muchatlu.service.PersonService;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PersonController {
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	PersonService personService;

	@Autowired
	ConversationService conversationService;
	
	@Autowired
	FriendRequestService friendRequestService;
	
	@Autowired
	private SimpMessagingTemplate simpMessageTemplate;
	
	
	@PostMapping("/register")
	public Person register(@RequestBody Person user) throws Exception {
		return personService.register(user);
	}
	
	@PostMapping("/login")
	public Person login(@RequestBody Person user) throws Exception{

		Optional<Person> opt = personService.getUserByUsername(user.getEmail());
		Person person = null;
		if(opt.isPresent()){
			person = opt.get();
			person.setSessionId(null);
			person.setPassword(null);
		}
		return person;
	}
	
	@GetMapping("/getAllUsers/{userId}")
	public List<Person> getAllUsers(@PathVariable Long userId){
		return personService.getAllUsers(userId);
	}
	
	@GetMapping("/getAllFriends/{userId}")
	public Person getFriendsOfUser(@PathVariable Long userId, @AuthenticationPrincipal UserDetails userDetails){
		System.out.println("principal"+((MyUserDetails)userDetails).getEmail());
		return personService.getUserById(userId);
	}
	
	@PostMapping("/sendFriendRequest")
	public FriendRequest sendFriendRequest(@RequestBody FriendRequest request) {
		request = friendRequestService.saveFriendRequest(request);
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

	@GetMapping("/getFriendRequests/{userId}")
	public List<FriendRequest> getFriendRequests(@PathVariable Long userId){
		return friendRequestService.getFriendRequestsByUserId(userId);
	}

	@GetMapping("/getConversationId/{fromId}/{toId}")
	public Long getConversationId(@PathVariable("fromId") Long fromId,@PathVariable("toId") Long toId){
		return conversationService.getConversationId(fromId,toId);
	}

	@GetMapping("/getConversation/{fromId}/{toId}")
	public Conversation getConversation(@PathVariable("fromId") Long fromId,@PathVariable("toId") Long toId){
		return conversationService.getConversation(fromId,toId);
	}

	@GetMapping("/getUserConversations/{id}")
	public List<ConversationDto> getUserConversations(@PathVariable("id") Long id){
		return conversationService.getConversationsForUser(id);
	}

	@GetMapping("/getUserDetails/{value}")
	public Person getUserDetails(@PathVariable("value") String value){
		return personService.getUserDetails(value);
	}

	@PutMapping("/updateUserDetails")
	public Person updateUserDetails(@RequestBody Person person){
		return personService.updateUserDetails(person);
	}

//	@GetMapping("/getUserConversations/{id}")
//	public String getUserConversations(@PathVariable("id") Long id){
//		return conversationService.getConversationsForUser(id);
//	}


}
