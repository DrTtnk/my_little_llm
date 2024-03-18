package com.my_little_llm.repositories;

import com.my_little_llm.domain.entities.ChatEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepo extends CrudRepository<ChatEntity, String> {}
