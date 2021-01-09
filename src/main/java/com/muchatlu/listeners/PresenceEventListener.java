package com.muchatlu.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import com.muchatlu.model.UserModel;
import com.muchatlu.model.UserStatusModel;
import com.muchatlu.service.UserService;

@Component
public class PresenceEventListener {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	UserService userService;
	
	public static final String loginDestination = "/topic/public.login";
	
	public static final String logoutDestination = "/topic/public.logout";
	
	@EventListener
	private void handleSessionConnected(SessionConnectEvent event) {
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String sessionId = headers.getSessionId();
		System.out.println(sessionId);
		
		UserModel user = userService.getUserBySessionId(sessionId);
		UserStatusModel status = new UserStatusModel(user.getId(),user.getUsername(),user.getSessionId() != null ? true : false);
		messagingTemplate.convertAndSend(loginDestination, status);
		
	}
	
	@EventListener
	private void handleSessionConnected(SessionSubscribeEvent event) {
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
	}
	
	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) {
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String sessionId = headers.getSessionId();
		System.out.println(sessionId);
		UserModel user = userService.getUserBySessionId(sessionId);
		userService.updateSessionIdByUserId(null, user.getId());
		UserStatusModel status = new UserStatusModel(user.getId(),user.getUsername(),false);
		messagingTemplate.convertAndSend(logoutDestination, status);
	}
}
