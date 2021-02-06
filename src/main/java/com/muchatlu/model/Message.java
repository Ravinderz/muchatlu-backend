package com.muchatlu.model;

import java.time.LocalDateTime;

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

}
