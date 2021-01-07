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
public class MessageModel {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long userIdFrom;
	private Long userIdTo;
	private String username;
	private LocalDateTime timestamp;
	private String message;
	
}
