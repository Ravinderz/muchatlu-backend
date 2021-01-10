package com.muchatlu.model;

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
public class FriendRequest {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long requestFromUserId;
	private String requestFromUsername;
	private String requestToEmailId;
	private String requestToUsername;
	private Long requestToUserId;
	private String status;
}
