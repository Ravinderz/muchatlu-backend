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

import com.muchatlu.service.UserService;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer{
	
	@Autowired
	UserService userService;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat").setHandshakeHandler(new MyHandshakeHandler())
		.setAllowedOrigins("http://localhost:4200")
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
        	System.out.println(request);
        	String userId = request.getURI().getQuery().split("=")[1];
        	String sessionId = request.getURI().toString().split("/chat/")[1].split("/")[1];
        	attributes.put(userId, sessionId);
        	
        	updateQuery(sessionId,  Long.parseLong(userId));
        	
        	Principal principal = request.getPrincipal();
        	 if (principal == null) {
                 Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                 authorities.add(new SimpleGrantedAuthority("USER_ROLE"));
                 principal = new AnonymousAuthenticationToken("user", userId, authorities);
             }
        	System.out.println(request.getPrincipal());
        	System.out.println(wsHandler);
            return principal;
        }
        
        private void updateQuery(String sessionId,Long userId) {
        	userService.updateSessionIdByUserId(sessionId,userId);
        }
        
    }
	
//	 @Override
//	    public void configureClientInboundChannel(ChannelRegistration registration) {
//	        registration.interceptors(new ChannelInterceptor() {
//	            @Override
//	            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//	                StompHeaderAccessor accessor =
//	                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//	                
//	                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//	                	System.out.println(accessor.getNativeHeader("X-Authorization"));
//	                	System.out.println(message);
//	                	System.out.println(accessor);
//	                }
//	                return message;
//	            }
//	        });
//	    }

	
}
