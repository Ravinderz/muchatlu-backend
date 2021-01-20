package com.muchatlu.controller;

import com.muchatlu.model.Typing;
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

import java.time.LocalDateTime;

@RestController
public class MessageController {
	
	@Autowired
	private SimpMessagingTemplate simpMessageTemplate;
	
	@Autowired
	MessageService messageService;

	@MessageMapping("/chat.{userId}")
	public void sendMessage(@DestinationVariable("userId") Long userId,@Payload Message message,@Header(name = "simpSessionId") String sessionId) {
		message.setTimestamp(LocalDateTime.now());
		messageService.saveMessage(message);
		simpMessageTemplate.convertAndSend("/topic/"+userId+"/messages",message);
	}

	@MessageMapping("/chat.typing.{userId}")
	public void typing(@DestinationVariable("userId") Long userId, @Payload Typing typing, @Header(name = "simpSessionId") String sessionId) {
		simpMessageTemplate.convertAndSend("/topic/"+userId+"/messages.typing",typing);
	}
	
}
