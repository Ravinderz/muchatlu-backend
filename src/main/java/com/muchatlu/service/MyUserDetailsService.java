package com.muchatlu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.muchatlu.model.MyUserDetails;
import com.muchatlu.model.User;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userService.getUserByUsername(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		
		return user.map(MyUserDetails::new).get();
	}

}
