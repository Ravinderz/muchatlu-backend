package com.muchatlu.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muchatlu.util.PersonSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@JsonIdentityInfo(
//		generator = ObjectIdGenerators.PropertyGenerator.class,property = "id",scope = Person.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(using = PersonSerializer.class)

@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	public String sessionId;
	public String username;
	public String password;
	public String email;
	public String avatar;
	public Boolean isOnline;
	public String status;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="UserFriends",
	 joinColumns=@JoinColumn(name="FriendOfId"),
	 inverseJoinColumns=@JoinColumn(name="friendToId")
	)
	private Set<Person> friends  = new HashSet<>();
	
	@ManyToMany(mappedBy = "friends")
	@JsonIgnore
	private Set<Person> friendsTo  = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Person person = (Person) o;
		return id.equals(person.id) && username.equals(person.username) && email.equals(person.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, email);
	}
}
