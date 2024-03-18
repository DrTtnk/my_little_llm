package com.my_little_llm.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

  private String id;
  private String name;
  private String hashedPwd;

  @Builder.Default
  private List<ChatDto> chats = new ArrayList<>();
}
