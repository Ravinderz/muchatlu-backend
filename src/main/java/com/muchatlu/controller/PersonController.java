package com.muchatlu.controller;

import com.muchatlu.dto.ConversationDto;
import com.muchatlu.exception.NotAuthorizedException;
import com.muchatlu.exception.UserIsAlreadyFriendException;
import com.muchatlu.model.*;
import com.muchatlu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
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
	AuthorizationService authorizationService;

	@Autowired
	AuthenticationTokenService authTokenService;
	
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

	@PostMapping("/logoutUser")
	public UserStatus logoutUser(@RequestBody Person user, @RequestHeader("Authorization") String token) throws Exception{
		token = token.replace("Bearer ","");
		AuthenticateToken authToken = authTokenService.getAuthToken(token);
				authToken.setIsActive(false);
		authTokenService.saveToken(authToken);
		personService.updateSessionIdByUserId(null,false,user.getId());
		UserStatus status = new UserStatus(user.getId(),user.getUsername(),false);
		return status;
	}
	
	@GetMapping("/getAllUsers")
	public List<Person> getAllUsers(){
		return personService.getAllUsers();
	}
	
	@GetMapping("/getAllFriends/{userId}")
	public Person getFriendsOfUser(@PathVariable Long userId, @AuthenticationPrincipal UserDetails userDetails){
		System.out.println("principal"+((MyUserDetails)userDetails).getEmail());
		if(authorizationService.validateRequest("getAllFriends","self",userId,(MyUserDetails)userDetails)){
			return personService.getUserById(userId);
		}else{
			throw new NotAuthorizedException("Not Authorized");
		}

	}
	
	@PostMapping("/sendFriendRequest")
	public FriendRequest sendFriendRequest(@RequestBody FriendRequest request, @AuthenticationPrincipal UserDetails userDetails) {
		if(authorizationService.validateRequest("sendFriendRequest","self",request,(MyUserDetails)userDetails)){
			int i = personService.checkIfUserIsFriend(request.getRequestFromUserId(), request.getRequestToUserId());
			if(i > 0){
				throw new UserIsAlreadyFriendException("User is already Friend");
			}
			request = friendRequestService.saveFriendRequest(request);
			simpMessageTemplate.convertAndSend("/topic/"+request.getRequestToUserId()+".friendRequest",request);
			return request;
		}else{
			throw new NotAuthorizedException("Not Authorized");
		}

	}
	
	@PostMapping("/updateFriendRequest")
	public FriendRequestModel updateFriendRequest(@RequestBody FriendRequest request, @AuthenticationPrincipal UserDetails userDetails) {
		if(authorizationService.validateRequest("updateFriendRequest","self",request,(MyUserDetails)userDetails)) {
			FriendRequestModel model = friendRequestService.updateFriendRequest(request);
			simpMessageTemplate.convertAndSend("/topic/" + request.getRequestToUserId() + ".friendRequest", model);
			simpMessageTemplate.convertAndSend("/topic/" + request.getRequestFromUserId() + ".friendRequest", model);
			return model;
		}else{
			throw new NotAuthorizedException("Not Authorized");
		}
	}

	@GetMapping("/getFriendRequests/{userId}")
	public List<FriendRequest> getFriendRequests(@PathVariable Long userId, @AuthenticationPrincipal UserDetails userDetails){
		if(authorizationService.validateRequest("getFriendRequests","self",userId,(MyUserDetails)userDetails)) {
			return friendRequestService.getFriendRequestsByUserId(userId);
		}else{
			throw new NotAuthorizedException("Not Authorized");
		}
	}

	@GetMapping("/getConversationId/{fromId}/{toId}")
	public Long getConversationId(@PathVariable("fromId") Long fromId,@PathVariable("toId") Long toId, @AuthenticationPrincipal UserDetails userDetails) {
		if(authorizationService.validateRequest("getConversationId","self", Arrays.asList(fromId,toId),(MyUserDetails)userDetails)){
		return conversationService.getConversationId(fromId, toId);
		}else{
			throw new NotAuthorizedException("Not Authorized");
		}
	}

	@GetMapping("/getConversation/{fromId}/{toId}")
	public Conversation getConversation(@PathVariable("fromId") Long fromId,@PathVariable("toId") Long toId, @AuthenticationPrincipal UserDetails userDetails){
		if(authorizationService.validateRequest("getConversation","self", Arrays.asList(fromId,toId),(MyUserDetails)userDetails)) {
			return conversationService.getConversation(fromId, toId);
		}else{
			throw new NotAuthorizedException("Not Authorized");
		}
	}

	@GetMapping("/getUserConversations/{id}")
	public List<ConversationDto> getUserConversations(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails){
		if(authorizationService.validateRequest("getUserConversations","self",id,(MyUserDetails)userDetails)) {
			return conversationService.getConversationsForUser(id);
		}else{
			throw new NotAuthorizedException("Not Authorized");
		}
	}

	@GetMapping("/getUserDetails/{value}")
	public Person getUserDetails(@PathVariable("value") String value, @AuthenticationPrincipal UserDetails userDetails){
//		if(authorizationService.validateRequest("getUserDetails","self",value,(MyUserDetails)userDetails)) {
			return personService.getUserDetails(value);
//		}else{
//			throw new NotAuthorizedException("Not Authorized");
//		}
	}

	@GetMapping("/getUserOnlinePresence/{userId}")
	public UserStatus getUserOnlinePresence(@PathVariable("userId") Long id){
		return personService.getUserOnlinePresence(id);
	}

	@PutMapping("/updateUserDetails")
	public Person updateUserDetails(@RequestBody Person person, @AuthenticationPrincipal UserDetails userDetails){
		if(authorizationService.validateRequest("updateUserDetails","self",person,(MyUserDetails)userDetails)) {
			return personService.updateUserDetails(person);
		}else{
			throw new NotAuthorizedException("Not Authorized");
		}
	}

//	@GetMapping("/getUserConversations/{id}")
//	public String getUserConversations(@PathVariable("id") Long id){
//		return conversationService.getConversationsForUser(id);
//	}


}
