package com.muchatlu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestModel {

	private Long id;
	private Long requestFromUserId;
	private Long requestToUserId;
	private String status;
	private User requestFromUser;
	private RequestUserTo requestToUser;
	private User RequestSendToUserObject;
	
	
}
