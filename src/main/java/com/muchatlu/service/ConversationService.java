package com.muchatlu.service;

import com.muchatlu.model.*;
import com.muchatlu.repository.ConversationRepository;
import com.muchatlu.repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

	@Autowired
	ConversationRepository repo;

	@Autowired
	UserService userService;

	public List<Conversation> saveConversation(FriendRequestModel request){
		String fromAvatar = userService.getUserAvatar(request.getRequestFromUserId());
		String toAvatar = userService.getUserAvatar(request.getRequestToUserId());

		Conversation fromConvo = new Conversation();
		fromConvo.setUserIdFrom(request.getRequestFromUser().getId());
		fromConvo.setUserIdTo(request.getRequestToUser().getId());
		fromConvo.setUsernameFrom(request.getRequestFromUser().getUsername());
		fromConvo.setUsernameTo(request.getRequestToUser().getUsername());
		fromConvo.setAvatarFrom(fromAvatar);
		fromConvo.setAvatarTo(toAvatar);


//		Conversation toConvo = new Conversation();
//		toConvo.setUserIdFrom(request.getRequestToUser().getId());
//		toConvo.setUserIdTo(request.getRequestFromUser().getId());
//		toConvo.setUsernameFrom(request.getRequestToUser().getUsername());
//		toConvo.setUsernameTo(request.getRequestFromUser().getUsername());
//		toConvo.setAvatarFrom(toAvatar);
//		toConvo.setAvatarTo(fromAvatar);

		return repo.saveAll(Arrays.asList(fromConvo));

	}

	public Long getConversationId(Long fromId,Long toId){
		Long id = repo.getConversationId(fromId, toId);
		if(id == null){
			id = repo.getConversationId(toId,fromId);
		}
		return id;
	}

	public Conversation getConversation(Long fromId,Long toId){
		Conversation convo = repo.findByUserIdFromAndUserIdTo(fromId, toId);
		if(convo == null){
			convo = repo.findByUserIdFromAndUserIdTo(toId,fromId);
		}
		return convo;
	}


}
