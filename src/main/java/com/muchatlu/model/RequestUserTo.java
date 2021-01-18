package com.muchatlu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserTo {

	private Long id;
	private String sessionId;
	private String username;
	private String password;
	private String email;
	private String avatar;
	private Boolean isOnline;

	
	public RequestUserTo(Person user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.avatar = user.getAvatar();
		this.isOnline = user.getIsOnline();
		this.sessionId = user.getId().toString();
	}
}
