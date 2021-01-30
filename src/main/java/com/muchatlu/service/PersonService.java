package com.muchatlu.service;

import com.muchatlu.exception.UserDoesntExistException;
import com.muchatlu.exception.UserExistsException;
import com.muchatlu.exception.UserNotFoundException;
import com.muchatlu.model.Person;
import com.muchatlu.model.UserStatus;
import com.muchatlu.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Person register(Person user) {
		Optional<Person> newUser = personRepo.findByEmailContainingIgnoreCase(user.getEmail().toLowerCase());
		if(newUser.isPresent()){
			throw new UserExistsException("User already exists");
		}
		user.setEmail(user.getEmail().toLowerCase());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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

	public Optional<Person> getUserByUserId(Long id) {
		return personRepo.findById(id);
//		if(userModel.isPresent()) {
//			return userModel.get();
//		}else{
//			throw new UserNotFoundException("User not found");
//		}

	}
	
	@Transactional
	public int updateSessionIdByUserId(String sessionId,boolean isOnline,Long userId) {
		return personRepo.updateSessionIdForAUser(sessionId, isOnline, userId);
	}
	
	public Optional<Person> getUserByUsername(String email) {
		return personRepo.findByEmailContainingIgnoreCase(email);
	}
	
	public List<Person> getAllUsers(){
		return new ArrayList<>(personRepo.findAll());
	}
	
	public List<Person> saveAllUsers(List<Person> users){
		return personRepo.saveAll(users);
	}

	public Person saveUser(Person user){
		return personRepo.save(user);
	}

	
	public Person getUserById(Long id) {
		Optional<Person> userModel = personRepo.findById(id);
		Person user;
		if(userModel.isPresent()) {
			user = userModel.get();
			return user;
		}else
			return null;
	}

	public String getUserAvatar(Long id){
		return personRepo.getUserAvatar(id);
	}

	public Person getUserDetails(String value){
		Person person = new Person();
		Optional<Person> optionalPerson;
		if(value.contains("@")){
			optionalPerson = personRepo.findByEmailContainingIgnoreCase(value);
		}else{
			optionalPerson = personRepo.findById(Long.parseLong(value));
		}
		if(optionalPerson != null && optionalPerson.isPresent()){
			person = optionalPerson.get();
		}else{
			throw new UserDoesntExistException("User does not exist");
		}

		return person;

	}

	@Transactional
	public Person updateUserDetails(Person person){
		personRepo.updateStatus(person.getStatus(), person.getId());
		return person;
	}

	public UserStatus getUserOnlinePresence(Long id){
		UserStatus status = new UserStatus(id,"",personRepo.getUserOnlinePresence(id));
		return status;
	}

	public List<Person> getFilterFriends(Long id,String text){
		text = "%"+text+"%";
		return personRepo.getFilterFriends(id,text);
	}

	public int checkIfUserIsFriend(Long fromId,Long toId){
		return personRepo.getCountFromUserFriend(fromId,toId);
	}
}
