package com.muchatlu.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muchatlu.model.FriendRequest;
import com.muchatlu.model.FriendRequestModel;
import com.muchatlu.model.MyUserDetails;
import com.muchatlu.model.RequestUserTo;
import com.muchatlu.model.User;
import com.muchatlu.repository.FriendRequestRepository;

@Service
public class FriendRequestService {

	@Autowired
	FriendRequestRepository repo;
	
	@Autowired
	UserService userService;
	
	private static final String ACCEPTED = "ACCEPTED";
	private static final String DECLINED = "DECLINED";
	private static final String PENDING = "PENDING";
	
	public FriendRequest saveFriendRequest(FriendRequest request) {
		Optional<User> user = userService.getUserByUsername(request.getRequestToEmailId());
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
			User fromUser = userService.getUserById(request.getRequestFromUserId());
			model.setRequestFromUser(fromUser);
			User toUser = userService.getUserByUsername(request.getRequestToEmailId()).get();
			model.setRequestToUser(new RequestUserTo(toUser));
			fromUser.getFriends().add(toUser);
			userService.saveAllUsers(Arrays.asList(fromUser));
		}
		return model;
	}
}
