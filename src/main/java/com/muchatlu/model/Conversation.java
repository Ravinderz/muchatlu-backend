package com.muchatlu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Entity
public class Conversation {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long userIdFrom;
	private Long userIdTo;
	private String usernameFrom;
	private String usernameTo;
	private String avatarFrom;
	private String avatarTo;


	@OneToMany
	@JoinColumn(name="CONVERSATION_ID", referencedColumnName="ID")
	private List<Message> message;

	public Conversation() {

	}

	public Conversation(Long id, Long userIdFrom,  Long userIdTo, String usernameFrom,String usernameTo,String avatarFrom,String avatarTo) {
		this.id = id;
		this.userIdFrom = userIdFrom;
		this.usernameFrom = usernameFrom;
		this.avatarFrom = avatarFrom;
		this.avatarTo = avatarTo;
		this.usernameTo = usernameTo;
		this.userIdTo = userIdTo;
	}
}
