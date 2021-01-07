package com.muchatlu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.muchatlu.model.MessageModel;
import com.muchatlu.service.MessageService;

@RestController
public class MessageController {
	
	@Autowired
	private SimpMessagingTemplate simpMessageTemplate;
	
	@Autowired
	MessageService messageService;

	@MessageMapping("/chat/{userId}")
	public void sendMessage(@DestinationVariable Long userId,MessageModel message) {
		System.out.println("user with id "+userId+"send a meesage"+message.getMessage());
		messageService.saveMessage(message);
		simpMessageTemplate.convertAndSend("/topic/messages/"+userId,message);
	}
}
