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
public class UserModel {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String sessionId;
	private String username;
	private String password;
	private String email;
	private String avatar;
	private String status;
}
