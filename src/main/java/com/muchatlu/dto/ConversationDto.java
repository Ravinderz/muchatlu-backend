package com.muchatlu.dto;

import com.muchatlu.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConversationDto {

	private Long id;
	private Long userIdFrom;
	private String usernameFrom;
	private String avatarFrom;
	private Long userIdTo;
	private String usernameTo;
 	private String avatarTo;
	private String lastMessage;
	private String lastMessageFrom;
	private Boolean isUserFromOnline;
	private Boolean isUserToOnline;
	private Timestamp lastMessageTimestamp;

	public ConversationDto(Long id, Long userIdFrom, String usernameFrom, String avatarFrom, Long userIdTo, String usernameTo, String avatarTo) {
		this.id = id;
		this.userIdFrom = userIdFrom;
		this.usernameFrom = usernameFrom;
		this.avatarFrom = avatarFrom;
		this.userIdTo = userIdTo;
		this.usernameTo = usernameTo;
		this.avatarTo = avatarTo;
	}

	public ConversationDto(Long id, Long userIdFrom, String usernameFrom, String avatarFrom, Long userIdTo, String usernameTo, String avatarTo, String lastMessage, String lastMessageFrom) {
		this.id = id;
		this.userIdFrom = userIdFrom;
		this.usernameFrom = usernameFrom;
		this.avatarFrom = avatarFrom;
		this.userIdTo = userIdTo;
		this.usernameTo = usernameTo;
		this.avatarTo = avatarTo;
		this.lastMessage = lastMessage;
		this.lastMessageFrom = lastMessageFrom;
	}

	public ConversationDto(Long id, Long userIdFrom, String usernameFrom, String avatarFrom, Long userIdTo, String usernameTo, String avatarTo, String lastMessage, String lastMessageFrom, Boolean isUserFromOnline, Boolean isUserToOnline) {
		this.id = id;
		this.userIdFrom = userIdFrom;
		this.usernameFrom = usernameFrom;
		this.avatarFrom = avatarFrom;
		this.userIdTo = userIdTo;
		this.usernameTo = usernameTo;
		this.avatarTo = avatarTo;
		this.lastMessage = lastMessage;
		this.lastMessageFrom = lastMessageFrom;
		this.isUserFromOnline = isUserFromOnline;
		this.isUserToOnline = isUserToOnline;
	}

	public ConversationDto() {
	}
}
