package com.muchatlu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusModel {

	private Long userId;
	private String username;
	private boolean isOnline;
}
