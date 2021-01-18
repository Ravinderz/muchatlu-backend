package com.muchatlu.configuration;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.muchatlu.service.PersonService;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer{
	
	@Autowired
    PersonService personService;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat").setHandshakeHandler(new MyHandshakeHandler())
		.setAllowedOrigins("http://localhost:4200","https://muchatlu-ui.herokuapp.com","http://muchatlu-ui.herokuapp.com")
		.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app")
				.enableSimpleBroker("/topic");
	}
	
	public class MyHandshakeHandler extends DefaultHandshakeHandler {

		
        @Override
        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, 
                                          Map<String, Object> attributes) {
        	String userId = request.getURI().getQuery().split("=")[1];
        	String sessionId = request.getURI().toString().split("/chat/")[1].split("/")[1];
        	attributes.put(userId, sessionId);
        	
        	updateQuery(sessionId, true ,
        			Long.parseLong(userId));
        	
        	Principal principal = request.getPrincipal();
        	 if (principal == null) {
                 Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                 authorities.add(new SimpleGrantedAuthority("USER_ROLE"));
                 principal = new AnonymousAuthenticationToken("user", userId, authorities);
             }
            return principal;
        }
        
        private void updateQuery(String sessionId,boolean isOnline,Long userId) {
        	personService.updateSessionIdByUserId(sessionId,isOnline,userId);
        }
        
    }
	
	
}
