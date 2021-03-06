package com.muchatlu.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import com.muchatlu.model.Person;
import com.muchatlu.model.UserStatus;
import com.muchatlu.service.PersonService;

@Component
public class PresenceEventListener {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	PersonService userService;
	
	public static final String loginDestination = "/topic/public.login";
	
	public static final String logoutDestination = "/topic/public.logout";
	
	@EventListener
	private void handleSessionConnected(SessionConnectEvent event) {
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		Long id = Long.parseLong(headers.getNativeHeader("userId").get(0));
		Person user = userService.getUserByUserId(id).get();
		UserStatus status = new UserStatus(user.getId(),user.getUsername(),true);
		messagingTemplate.convertAndSend(loginDestination, status);
		
	}
	
	@EventListener
	private void handleSessionConnected(SessionSubscribeEvent event) {
	}
	
	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) {
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
////		Long id = Long.parseLong(headers.getNativeHeader("userId").get(0));
//		Person user = userService.getUserByUserId(1l).get();
		String sessionId = headers.getSessionId();
		Person user = userService.getUserBySessionId(sessionId);
		userService.updateSessionIdByUserId(null, false, user.getId());
		UserStatus status = new UserStatus(user.getId(),user.getUsername(),false);
		messagingTemplate.convertAndSend(logoutDestination, status);
	}
}
