package com.muchatlu.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private String usernameTo;
	private Long userIdTo;
	private LocalDateTime timestamp;
	private String message;
	
}
