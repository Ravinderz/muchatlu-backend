package com.muchatlu.service;

import com.muchatlu.model.PushNotificationObject;
import com.muchatlu.model.UserStatus;
import com.muchatlu.repository.ApplicationPropertiesRepository;
import com.muchatlu.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.muchatlu.model.Message;
import com.muchatlu.repository.MessageRepository;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
public class MessageService {

	@Autowired
	MessageRepository messageRepo;

	@Autowired
	PersonService personService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ApplicationPropertiesRepository applicationPropertiesRepository;
	
	public void saveMessage(Message message) {
		ZonedDateTime zdtInstanceAtUTC = ZonedDateTime.parse(message.getTimestamp(), DateTimeFormatter.ISO_DATE_TIME);
		message.setTimestamp(zdtInstanceAtUTC.toString());
		messageRepo.save(message);

		Long userIdTo = message.getUserIdTo();
		UserStatus status = personService.getUserOnlinePresence(userIdTo);
		if(status != null && !status.isOnline()){

			String token = applicationPropertiesRepository.getExpoToken();
			String userPushToken = personService.getUserPushToken(userIdTo);
			if(userPushToken != null){
				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				headers.set("Authorization","Bearer "+token);
				PushNotificationObject obj = new PushNotificationObject(userPushToken,message.getUsernameFrom(),message.getMessage(),message);
				HttpEntity<PushNotificationObject> entity = new HttpEntity<>(obj,headers);
				Object resp =  restTemplate.exchange(
						"https://exp.host/--/api/v2/push/send", HttpMethod.POST, entity, Object.class).getBody();
			}

		}
	}
	
}
