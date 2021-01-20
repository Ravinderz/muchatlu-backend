package com.muchatlu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.muchatlu.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

	Person findByEmailAndPassword(String email, String password);
	
	Optional<Person> findById(Long id);
	
	Optional<Person> findByEmail(String email);
	
	Optional<Person> findBySessionId(String sessionId);
	
	@Modifying
	@Query("update Person set session_id  = :sessionId,is_online= :isOnline where id = :id")
	int updateSessionIdForAUser(@Param("sessionId") String sessionId,@Param("isOnline") boolean isOnline, @Param("id") Long id);

	@Modifying
	@Query("update Person set status  = :status where id = :id")
	int updateStatus(@Param("status") String status, @Param("id") Long id);

	@Query("select avatar from Person where id = :id")
	String getUserAvatar(@Param("id") Long id);
		
}
