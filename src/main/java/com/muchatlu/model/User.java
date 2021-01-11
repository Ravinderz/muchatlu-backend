package com.muchatlu.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String sessionId;
	private String username;
	private String password;
	private String email;
	private String avatar;
	private Boolean isOnline;
	
	@ManyToMany
	@JoinTable(name="UserFriends",
	 joinColumns=@JoinColumn(name="FriendOfId"),
	 inverseJoinColumns=@JoinColumn(name="friendToId")
	)
	private List<User> friends  = new ArrayList<>();
	
	@ManyToMany(mappedBy = "friends")
	@JsonIgnore
	private List<User> friendsTo  = new ArrayList<>();
	

}
