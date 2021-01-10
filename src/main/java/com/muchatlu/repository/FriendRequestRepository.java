package com.muchatlu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.muchatlu.model.FriendRequest;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long>{

	@Modifying
	@Query("update FriendRequest set status = :status where id = :id")
	int updateFriendRequest(@Param("status") String status, @Param("id") Long id);
}
