package com.muchatlu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.muchatlu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muchatlu.repository.FriendRequestRepository;

@Service
public class FriendRequestService {

	@Autowired
	FriendRequestRepository repo;
	
	@Autowired
	PersonService personService;

	@Autowired
	ConversationService conversationService;
	
	private static final String ACCEPTED = "Accepted";
	private static final String DECLINED = "Rejected";
	private static final String PENDING = "Pending";
	
	public FriendRequest saveFriendRequest(FriendRequest request) {
		Optional<Person> user = personService.getUserByUsername(request.getRequestToEmailId());
		Long userId = null;
		if(user.isPresent())
			userId = user.get().getId();
		
		request.setRequestToUserId(userId);
		return repo.save(request);
	}
	
	@Transactional
	public FriendRequestModel updateFriendRequest(FriendRequest request) {
		repo.updateFriendRequest(request.getStatus(), request.getId());
		FriendRequestModel model = new FriendRequestModel();
		model.setId(request.getId());
		model.setRequestFromUserId(request.getRequestFromUserId());
		model.setRequestToUserId(request.getRequestToUserId());
		model.setStatus(request.getStatus());
		
		if(ACCEPTED.equalsIgnoreCase(request.getStatus())) {
			Person fromUser = personService.getUserById(request.getRequestFromUserId());
			model.setRequestFromUser(fromUser);
			Person toUser = personService.getUserByUsername(request.getRequestToEmailId()).get();
			model.setRequestToUser(new RequestUserTo(toUser));
			model.setRequestToUserId(toUser.getId());
			fromUser.getFriends().add(toUser);
			personService.saveAllUsers(Arrays.asList(fromUser));
			conversationService.saveConversation(model);

		}
		return model;
	}

	public List<FriendRequest> getFriendRequestsByUserId(Long userId){
		List<FriendRequest> friendRequests = new ArrayList<>();
		friendRequests.addAll(repo.findAllByRequestFromUserId(userId));
		friendRequests.addAll(repo.findAllByRequestToUserId(userId));
		return friendRequests;
	}
}
