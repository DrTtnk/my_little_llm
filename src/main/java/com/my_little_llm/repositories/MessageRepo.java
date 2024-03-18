package com.my_little_llm.repositories;

import com.my_little_llm.domain.entities.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends CrudRepository<MessageEntity, String> {}
