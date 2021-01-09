package com.muchatlu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.muchatlu.model.UserModel;

@Repository
public interface UserRepository  extends JpaRepository<UserModel, Long>{ 

	UserModel findByEmailAndPassword(String email,String password);
	
	Optional<UserModel> findById(Long id);
	
	Optional<UserModel> findByEmail(String email);
	
	Optional<UserModel> findBySessionId(String sessionId);
	
	@Modifying
	@Query("update UserModel set session_id  = :sessionId,is_online=true where id = :id")
	int updateSessionIdForAUser(@Param("sessionId") String sessionId, @Param("id") Long id);
}
