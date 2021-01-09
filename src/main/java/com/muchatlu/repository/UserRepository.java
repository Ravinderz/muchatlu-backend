package com.muchatlu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muchatlu.model.UserModel;

public interface UserRepository  extends JpaRepository<UserModel, Long>{ 

	UserModel findByEmailAndPassword(String email,String password);
	
	Optional<UserModel> findById(Long id);
}
