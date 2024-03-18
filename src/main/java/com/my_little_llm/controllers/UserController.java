package com.my_little_llm.controllers;

import com.my_little_llm.domain.dtos.ChatDto;
import com.my_little_llm.domain.dtos.InitUserDto;
import com.my_little_llm.domain.dtos.UpdateUserDto;
import com.my_little_llm.domain.dtos.UserDto;
import com.my_little_llm.domain.mappers.ChatMapper;
import com.my_little_llm.domain.mappers.UserMapper;
import com.my_little_llm.services.ChatService;
import com.my_little_llm.services.UserService;
import com.my_little_llm.utils.OPipe;
import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

  private final UserService userService;
  private final ChatService chatService;

  private final UserMapper userMapper;
  private final ChatMapper chatMapper;

  public UserController(
    UserService userService,
    ChatService chatService,
    UserMapper userMapper,
    ChatMapper chatMapper
  ) {
    this.userService = userService;
    this.chatService = chatService;
    this.userMapper = userMapper;
    this.chatMapper = chatMapper;
  }

  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UserDto> createUser(@RequestBody InitUserDto user) {
    return OPipe.of(user)
      .map(userService::createUser)
      .map(userMapper::mapTo)
      .map(u -> new ResponseEntity<>(u, HttpStatus.CREATED))
      .value;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserDto>> getUsers() {
    return OPipe.of(userService.getUsers())
      .map(Iterable::spliterator)
      .map(u -> StreamSupport.stream(u, false).map(userMapper::mapTo).toList())
      .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
      .value;
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserDto> getUser(@PathVariable String id) {
    return OPipe.of(userService.getUser(id))
      .map(userMapper::mapTo)
      .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
      .value;
  }

  @PatchMapping("/users/{id}")
  public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserDto user) {
    return OPipe.of(user)
      .map(userService::updateUser)
      .map(userMapper::mapTo)
      .map(ResponseEntity::ok)
      .value;
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable String id) {
    userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/users/{userId}/chats")
  public ResponseEntity<ChatDto> startNewChat(
    @PathVariable String userId,
    @RequestParam String chatName,
    @RequestParam String prompt
  ) {
    return OPipe.of(chatService.startNewChat(userId, chatName, prompt))
      .map(chatMapper::mapTo)
      .map(ResponseEntity::ok)
      .value;
  }
}
