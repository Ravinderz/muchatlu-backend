package com.muchatlu.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.muchatlu.model.Message;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>{

    @Query(value = "select id,user_id_from,username_from,avatar_from,avatar_to,username_to,user_id_to,timestamp,message,from_read,to_read,type,data,conversation_id from message where conversation_id = :id",nativeQuery = true)
    List<Message> getMessagesByConversationId(@Param("id") Long id);
}
