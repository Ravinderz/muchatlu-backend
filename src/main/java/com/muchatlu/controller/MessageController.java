package com.muchatlu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.muchatlu.model.MessageModel;
import com.muchatlu.service.MessageService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MessageController {
	
	@Autowired
	private SimpMessagingTemplate simpMessageTemplate;
	
	@Autowired
	MessageService messageService;

	@MessageMapping("/chat.{userId}")
	public void sendMessage(@DestinationVariable("userId") Long userId,@Payload MessageModel message,@Header(name = "simpSessionId") String sessionId) {
		messageService.saveMessage(message);
		simpMessageTemplate.convertAndSend("/topic/"+userId+"/messages",message);
	}
	
//	@MessageMapping("/chat.login")
//	public void onlineStatus(@DestinationVariable Long userId,MessageModel message,Principal principal) {
//		simpMessageTemplate.convertAndSend("/topic/public/login",message);
//	}
//	
//	@MessageMapping("/chat.logout")
//	public void onlineStatus(@DestinationVariable Long userId,MessageModel message,Principal principal) {
//		simpMessageTemplate.convertAndSend("/topic/public/login",message);
//	}
//	
}
