package com.muchatlu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.muchatlu.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{ 

	User findByEmailAndPassword(String email,String password);
	
	Optional<User> findById(Long id);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findBySessionId(String sessionId);
	
	@Modifying
	@Query("update User set session_id  = :sessionId,is_online= :isOnline where id = :id")
	int updateSessionIdForAUser(@Param("sessionId") String sessionId,@Param("isOnline") boolean isOnline, @Param("id") Long id);

	@Query("select avatar from User where id = :id")
	String getUserAvatar(@Param("id") Long id);
		
}
