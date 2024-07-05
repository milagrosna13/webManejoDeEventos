package com.codingdojo.milagros.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.milagros.models.Event;
import com.codingdojo.milagros.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message,Long>{
	

}
