package com.muchatlu.repository;

import org.springframework.data.repository.CrudRepository;

import com.muchatlu.model.MessageModel;

public interface MessageRepository extends CrudRepository<MessageModel, Long>{

}
