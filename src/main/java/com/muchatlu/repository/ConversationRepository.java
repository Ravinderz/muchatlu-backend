package com.muchatlu.repository;

import com.muchatlu.dto.ConversationDto;
import com.muchatlu.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.SqlResultSetMapping;
import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>{

    @Query("select id from Conversation where user_id_from=:fromId and user_id_to=:toId")
    Long getConversationId(@Param("fromId") Long fromId,@Param("toId") Long toId);

    Conversation findByUserIdFromAndUserIdTo(Long fromId, Long toId);

    @Query(value = "select t.id,t.user_id_from,t.username_from,t.avatar_from,t.user_id_to,t.username_to,t.avatar_to,msg.message,msg.username_from as msg_from,msg.timestamp from (select c.id,c.avatar_from,c.avatar_to,c.user_id_from,c.user_id_to,c.username_from,c.username_to,nvl(max(m.id),0) msg_id from conversation c left join message m on c.id = m.conversation_id where c.user_id_from =:id group by c.id) t left join message msg on t.msg_id = msg.id",nativeQuery = true)
    List<Object[]> getConversationForUser(@Param("id") Long id);

//    @Query(value = "select new com.muchatlu.dto.ConversationDto(c.id,c.userIdFrom,c.usernameFrom,c.avatarFrom,c.userIdTo,c.usernameTo,c.avatarTo,lastMessage,lastMessageFrom) from Conversation c join where user_id_from=:id",nativeQuery = true)
//    List<ConversationDto> getConversationForUser(@Param("id") Long id);

    @Query(value = "select t.id,t.user_id_from,t.username_from,t.avatar_from,t.user_id_to,t.username_to,t.avatar_to,msg.message,msg.username_from as msg_from,msg.timestamp from (select c.id,c.avatar_from,c.avatar_to,c.user_id_from,c.user_id_to,c.username_from,c.username_to,nvl(max(m.id),0) msg_id from conversation c left join message m on c.id = m.conversation_id where c.user_id_to =:id group by c.id) t left join message msg on t.msg_id = msg.id",nativeQuery = true)
    List<Object[]> getConversationForUserAlt(@Param("id") Long id);

}
