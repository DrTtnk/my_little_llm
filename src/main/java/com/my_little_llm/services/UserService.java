package com.my_little_llm.services;

import static org.modelmapper.internal.util.Objects.firstNonNull;

import com.my_little_llm.domain.dtos.InitUserDto;
import com.my_little_llm.domain.dtos.UpdateUserDto;
import com.my_little_llm.domain.entities.UserEntity;
import com.my_little_llm.repositories.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepo userRepo;

  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  public UserEntity createUser(InitUserDto user) {
    var userEntity = UserEntity.builder()
      .name(user.getName())
      .hashedPwd(user.getHashedPwd())
      .build();

    return userRepo.save(userEntity);
  }

  public UserEntity getUser(String id) {
    return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
  }

  public Iterable<UserEntity> getUsers() {
    return userRepo.findAll();
  }

  public UserEntity updateUser(UpdateUserDto user) {
    var current = userRepo
      .findById(user.getId())
      .orElseThrow(() -> new RuntimeException("User not found"));

    current.setName(firstNonNull(user.getName(), current.getName()));
    current.setHashedPwd(firstNonNull(user.getHashedPwd(), current.getHashedPwd()));

    return userRepo.save(current);
  }

  public void deleteUser(String currentUserId) {
    userRepo.deleteById(currentUserId);
  }
}
