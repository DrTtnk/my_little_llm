package com.my_little_llm.domain.mappers;

import com.my_little_llm.domain.dtos.ChatDto;
import com.my_little_llm.domain.dtos.UserDto;
import com.my_little_llm.domain.entities.ChatEntity;
import com.my_little_llm.domain.entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper implements Mapper<ChatEntity, ChatDto> {

  private final ModelMapper modelMapper;

  public ChatMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Override
  public ChatDto mapTo(ChatEntity user) {
    return modelMapper.map(user, ChatDto.class);
  }

  @Override
  public ChatEntity mapFrom(ChatDto userDto) {
    return modelMapper.map(userDto, ChatEntity.class);
  }
}
