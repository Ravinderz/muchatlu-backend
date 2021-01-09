package com.muchatlu.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.muchatlu.model.MessageModel;

@Repository
public interface MessageRepository extends CrudRepository<MessageModel, Long>{

}
