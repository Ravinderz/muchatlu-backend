package com.muchatlu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conversation {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long userIdFrom;
	private String usernameFrom;
	private String avatarFrom;
	private String avatarTo;
	private String usernameTo;
	private Long userIdTo;
	@OneToMany
	@JoinColumn(name="CONVERSATION_ID", referencedColumnName="ID")
	private List<Message> message;
	
}
