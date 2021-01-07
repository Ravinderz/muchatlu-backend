package com.muchatlu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muchatlu.model.UserModel;

public interface UserRepository  extends JpaRepository<UserModel, Long>{ 

	UserModel findByUsernameAndPassword(String username,String password);
}
