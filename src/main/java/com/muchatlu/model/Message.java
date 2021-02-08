package com.muchatlu.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long userIdFrom;
	private String usernameFrom;
	private String avatarFrom;
	private String avatarTo;
	private String usernameTo;
	private Long userIdTo;
	private String timestamp;
	private String message;
	private Boolean fromRead;
	private Boolean toRead;
	private String type;
	@Lob
	private byte[] data;

	@Column(name="CONVERSATION_ID")
	private Long conversationId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Message)) return false;
		Message message1 = (Message) o;
		return getId().equals(message1.getId()) && Objects.equals(getUserIdFrom(), message1.getUserIdFrom()) && Objects.equals(getUsernameFrom(), message1.getUsernameFrom()) && Objects.equals(getAvatarFrom(), message1.getAvatarFrom()) && Objects.equals(getAvatarTo(), message1.getAvatarTo()) && Objects.equals(getUsernameTo(), message1.getUsernameTo()) && Objects.equals(getUserIdTo(), message1.getUserIdTo()) && getTimestamp().equals(message1.getTimestamp()) && Objects.equals(getMessage(), message1.getMessage()) && Objects.equals(getFromRead(), message1.getFromRead()) && Objects.equals(getToRead(), message1.getToRead()) && Objects.equals(getType(), message1.getType()) && getConversationId().equals(message1.getConversationId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getUserIdFrom(), getUsernameFrom(), getUsernameTo(), getUserIdTo(), getTimestamp(), getMessage(), getConversationId());
	}
}
