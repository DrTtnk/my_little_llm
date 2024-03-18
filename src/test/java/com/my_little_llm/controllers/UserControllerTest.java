package com.my_little_llm.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.my_little_llm.domain.dtos.InitUserDto;
import com.my_little_llm.domain.dtos.UpdateUserDto;
import com.my_little_llm.domain.dtos.UserDto;
import com.my_little_llm.repositories.ChatRepo;
import com.my_little_llm.repositories.UserRepo;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerTest {

  @Autowired
  UserController userController;

  @Autowired
  UserRepo userRepo;

  @Autowired
  ChatRepo chatRepo;

  @BeforeEach
  void setUp() {
    userRepo.deleteAll();
    chatRepo.deleteAll();
  }

  @AfterEach
  void tearDown() {}

  @Test
  void the_user_crud_work() {
    // CREATE
    final var user = userController
      .createUser(InitUserDto.builder().name("test").hashedPwd("test").build())
      .getBody();

    final var currentUserId = user.getId();

    final var expectedUser = UserDto.builder()
      .hashedPwd("test")
      .name("test")
      .id(currentUserId)
      .chats(new ArrayList<>())
      .build();

    assertEquals(expectedUser, user);

    // READ
    final var users = userController.getUser(currentUserId).getBody();

    assertEquals(expectedUser, users);

    // UPDATE
    final var updatedUser = userController
      .updateUser(
        UpdateUserDto.builder().id(currentUserId).name("test-rename").hashedPwd("test").build()
      )
      .getBody();

    assertEquals(
      UserDto.builder()
        .hashedPwd("test")
        .name("test-rename")
        .id(currentUserId)
        .chats(new ArrayList<>())
        .build(),
      updatedUser
    );

    // DELETE
    userController.deleteUser(currentUserId);

    final var deletedUser = userController.getUsers().getBody();

    assertEquals(Collections.emptyList(), deletedUser);
  }

  @Test
  void the_user_can_start_a_chat() {
    final var currentUserId = userController
      .createUser(InitUserDto.builder().name("test").hashedPwd("test").build())
      .getBody()
      .getId();

    final var chat = userController.startNewChat(currentUserId, "test-chat", "").getBody();

    assertEquals(chat.getName(), "test-chat");

    var newChat = chatRepo.findById(chat.getId()).orElseThrow();

    assertEquals(newChat.getUser().getId(), currentUserId);
  }
}
