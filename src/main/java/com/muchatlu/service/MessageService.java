package com.muchatlu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muchatlu.model.MessageModel;
import com.muchatlu.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	MessageRepository messageRepo;
	
	public void saveMessage(MessageModel message) {
		messageRepo.save(message);
	}
	
}