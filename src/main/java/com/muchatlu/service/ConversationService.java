package com.muchatlu.service;

import com.muchatlu.dto.ConversationDto;
import com.muchatlu.model.*;
import com.muchatlu.repository.ConversationRepository;
import com.muchatlu.repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

//	public List<ConversationDto> getConversationsForUser(Long id){
//		List<ConversationDto> list = repo.getConversationForUser(id);
//
//		if(list != null && list.size() > 0){
//			return list;
//		}else{
//			list = repo.getConversationAltForUser(id);
//		}
//		return list;
//	}



	public List<ConversationDto> getConversationsForUser(Long id){
		List<Object[]> list = repo.getConversationForUser(id);
		if(list == null && list.size() == 0){
			list = repo.getConversationForUserAlt(id);
		}
		List<ConversationDto> conversationList = new ArrayList<>();
		if(list != null && list.size() > 0) {
			list.stream().forEach(o -> {
				ConversationDto dto = new ConversationDto();
				for(int i = 0; i < o.length ; i++){
					switch(i){
						case 0: {
							dto.setId( Long.parseLong(o[i].toString()));
							break;
						}
						case 1:{
							dto.setUserIdFrom( Long.parseLong(o[i].toString()));
							break;
						}
						case 2:{
							dto.setUsernameFrom((String) o[i]);
							break;
						}
						case 3:{
							dto.setAvatarFrom((String) o[i]);
							break;
						}
						case 4:{
							dto.setUserIdTo(Long.parseLong(o[i].toString()));
							break;
						}
						case 5:{
							dto.setUsernameTo((String) o[i]);
							break;
						}
						case 6:{
							dto.setAvatarTo((String) o[i]);
							break;
						}
						case 7:{
							dto.setLastMessage((String) o[i]);
							break;
						}
						case 8:{
							dto.setLastMessageFrom((String) o[i]);
							break;
						}
						case 9:{
							dto.setLastMessageTimestamp((Timestamp) o[i]);
							break;
						}
					}
				}
				conversationList.add(dto);
			});

		}
		return conversationList;
	}


}
