package com.muchatlu.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.muchatlu.exception.UserExistsException;
import com.muchatlu.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muchatlu.model.Person;
import com.muchatlu.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepo;
	
	public Person register(Person user) throws Exception {
		Optional<Person> newUser = personRepo.findByEmail(user.getEmail());
		if(newUser.isPresent()){
			throw new UserExistsException("User already exists");
		}
		personRepo.save(user);
		return user;
	}
	
	public Person login(Person user) {
		return personRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
	}
	
	public Person getUserBySessionId(String sessionId) {
		Optional<Person> userModel =  personRepo.findBySessionId(sessionId);
		if(userModel.isPresent()) {
			return userModel.get();
		}else{
			throw new UserNotFoundException("User not found");
		}

	}

	public Person getUserByUserId(Long id) {
		Optional<Person> userModel =  personRepo.findById(id);
		if(userModel.isPresent()) {
			return userModel.get();
		}else{
			throw new UserNotFoundException("User not found");
		}

	}
	
	@Transactional
	public int updateSessionIdByUserId(String sessionId,boolean isOnline,Long userId) {
		return personRepo.updateSessionIdForAUser(sessionId, isOnline, userId);
	}
	
	public Optional<Person> getUserByUsername(String email) {
		return personRepo.findByEmail(email);
	}
	
	public List<Person> getAllUsers(Long UserId){
		List<Person> list = new ArrayList<>();
		personRepo.findAll().forEach(list::add);
		return list;
	}
	
	public List<Person> saveAllUsers(List<Person> users){
		return personRepo.saveAll(users);
	}
	
	public Person getUserById(Long id) {
		Optional<Person> userModel = personRepo.findById(id);
		Person user = new Person();
		Set<Person> set = new HashSet<>();
		if(userModel.isPresent()) {
			user = userModel.get();
			set.addAll(user.getFriends().stream().filter(p -> p instanceof Person).collect(Collectors.toList()));
			set.addAll(user.getFriendsTo().stream().filter(p -> p instanceof Person).collect(Collectors.toList()));
			user.getFriends().addAll(set);
			return user;
		}else
			return null;
	}

	public String getUserAvatar(Long id){
		return personRepo.getUserAvatar(id);
	}

	public Person getUserDetails(String value){
		Person person = new Person();
		Optional<Person> optionalPerson = null;
		if(value.contains("@")){
			optionalPerson = personRepo.findByEmail(value);
		}else{
			optionalPerson = personRepo.findById(Long.parseLong(value));
		}
		if(optionalPerson != null && optionalPerson.isPresent()){
			person = optionalPerson.get();
		}

		return person;

	}

	public Person updateUserDetails(Person person){
		personRepo.updateStatus(person.getStatus(), person.getId());
		return person;
	}
}
