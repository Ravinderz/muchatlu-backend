package com.muchatlu.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import com.muchatlu.model.UserModel;
import com.muchatlu.service.UserService;

@Component
public class SessionConnectListener implements ApplicationListener<SessionConnectEvent> {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private SimpMessagingTemplate simpMessageTemplate;
	

	   @Override
	   public void onApplicationEvent(SessionConnectEvent event) {
	       GenericMessage message = (GenericMessage) event.getMessage();
	       String simpDestination = (String) message.getHeaders().get("simpDestination");
	       System.out.println(simpDestination);
	       
	       if (simpDestination.contains("/topic/public")) {
	    	   String userId = simpDestination.split("/")[3];
	    		UserModel user = userService.getUserById(Long.parseLong(userId));
	    		simpMessageTemplate.convertAndSend("/topic/public",user);
	       }
	   }

}
