package com.muchatlu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muchatlu.model.Message;
import com.muchatlu.repository.MessageRepository;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MessageService {

	@Autowired
	MessageRepository messageRepo;
	
	public void saveMessage(Message message) {
		System.out.println(message);
		ZonedDateTime zdtInstanceAtUTC = ZonedDateTime.parse(message.getTimestamp(), DateTimeFormatter.ISO_DATE_TIME);
		message.setTimestamp(zdtInstanceAtUTC.toString());
		System.out.println(zdtInstanceAtUTC.toString());
		messageRepo.save(message);
	}
	
}
