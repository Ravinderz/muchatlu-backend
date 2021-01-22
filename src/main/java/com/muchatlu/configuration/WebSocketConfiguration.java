package com.muchatlu.configuration;

import java.security.Principal;
import java.util.*;

import com.amazonaws.services.dynamodbv2.xspec.M;
import com.muchatlu.model.MyUserDetails;
import com.muchatlu.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.muchatlu.service.PersonService;

import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSocketMessageBroker
//@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer{
	
	@Autowired
    PersonService personService;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat").setHandshakeHandler(new MyHandshakeHandler())
		.setAllowedOrigins("http://localhost:4200","https://muchatlu-ui.herokuapp.com","http://muchatlu-ui.herokuapp.com","https://www.muchatlu.in","http://www.muchatlu.in")
		.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app")
				.enableSimpleBroker("/topic");
	}

//	@Override
//	public void configureClientInboundChannel(ChannelRegistration registration) {
//		registration.interceptors(new ChannelInterceptor() {
//			@Override
//			public Message<?> preSend(Message<?> message, MessageChannel channel) {
//				StompHeaderAccessor accessor =
//						MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//				if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//					List<String> authorization = accessor.getNativeHeader("userId");
//					System.out.println("X-Authorization: {}"+ authorization);
//					Long id = Long.parseLong(authorization.get(0));
//					Optional<Person> person = personService.getUserByUserId(id);
////					Optional<Person> opt = Optional.of(person);
//					accessor.setUser((Principal) person.map(MyUserDetails::new).get());
//				}
////				if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
////					//List<String> authorization = accessor.getNativeHeader("X-Authorization");
////					List<String> authorization = accessor.getNativeHeader("X-email");
////					System.out.println("X-Authorization: {}"+ authorization);
////					String email = authorization.get(0);
//////					Optional<Person> person = personService.getUserByUsername(email);
//////					accessor.setUser((Principal) person.map(MyUserDetails::new).get());
////				}
//				return message;
//			}
//		});
//	}
	
	public class MyHandshakeHandler extends DefaultHandshakeHandler implements HandshakeInterceptor {

		@Override
		public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
									   Map attributes) throws Exception {
			if (request instanceof ServletServerHttpRequest) {
				ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
				HttpSession session = servletRequest.getServletRequest().getSession();
				attributes.put("sessionId", session.getId());
			}
			return true;
		}

		@Override
		public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
								   Exception ex) {
		}


        @Override
        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                          Map<String, Object> attributes) {

			request.getHeaders().get("userId");

        	String userId = request.getURI().getQuery().split("=")[1];
        	String sessionId = request.getURI().toString().split("/chat/")[1].split("/")[1];
        	attributes.put(userId, sessionId);

        	updateQuery(sessionId, true ,
        			Long.parseLong(userId));

        	Principal principal = request.getPrincipal();

//        	 if (principal == null) {
//                 Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                 authorities.add(new SimpleGrantedAuthority("USER_ROLE"));
//                 principal = new AnonymousAuthenticationToken("user", userId, authorities);
//             }
            return principal;
        }

        private void updateQuery(String sessionId,boolean isOnline,Long userId) {
        	personService.updateSessionIdByUserId(sessionId,isOnline,userId);
        }

    }
	
	
}
