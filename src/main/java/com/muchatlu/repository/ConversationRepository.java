package com.muchatlu.repository;

import com.muchatlu.model.Conversation;
import com.muchatlu.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>{


    @Query("select id from Conversation where user_id_from=:fromId and user_id_to=:toId")
    public Long getConversationId(@Param("fromId") Long fromId,@Param("toId") Long toId);

//    @Query("select * from Conversation where user_id_from=:fromId and user_id_to=:toId")
//    public Conversation getConversation(@Param("fromId") Long fromId,@Param("toId") Long toId);

    public Conversation findByUserIdFromAndUserIdTo(Long fromId, Long toId);

}
