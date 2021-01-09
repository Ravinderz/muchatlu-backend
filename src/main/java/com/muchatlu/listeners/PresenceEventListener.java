package com.muchatlu.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class PresenceEventListener {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@EventListener
	private void handleSessionConnected(SessionConnectEvent event) {
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String username = headers.getUser().getName();
		System.out.println(headers);
		

//		UserModel user = new LoginEvent(username);
//		messagingTemplate.convertAndSend(loginDestination, loginEvent);
		
	}
	
	@EventListener
	private void handleSessionConnected(SessionSubscribeEvent event) {
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String username = headers.getUser().getName();
		System.out.println(headers);
		

//		UserModel user = new LoginEvent(username);
//		messagingTemplate.convertAndSend(loginDestination, loginEvent);
		
	}
	
	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) {
		
	}
}
