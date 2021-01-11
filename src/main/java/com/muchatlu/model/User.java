package com.muchatlu.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
