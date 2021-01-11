package com.muchatlu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.muchatlu.model.Message;
import com.muchatlu.service.MessageService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MessageController {
	
	@Autowired
	private SimpMessagingTemplate simpMessageTemplate;
	
	@Autowired
	MessageService messageService;

	@MessageMapping("/chat.{userId}")
	public void sendMessage(@DestinationVariable("userId") Long userId,@Payload Message message,@Header(name = "simpSessionId") String sessionId) {
		messageService.saveMessage(message);
		simpMessageTemplate.convertAndSend("/topic/"+userId+"/messages",message);
	}
	
}
