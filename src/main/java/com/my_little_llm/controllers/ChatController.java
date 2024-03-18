package com.my_little_llm.controllers;

import com.my_little_llm.domain.dtos.ChatDto;
import com.my_little_llm.domain.mappers.ChatMapper;
import com.my_little_llm.services.ChatService;
import com.my_little_llm.utils.OPipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

  private final ChatService chatService;
  private final ChatMapper chatMapper;

  public ChatController(ChatService chatService, ChatMapper chatMapper) {
    this.chatService = chatService;
    this.chatMapper = chatMapper;
  }

  @GetMapping("/chat/{id}")
  public ResponseEntity<ChatDto> getChat(@PathVariable String id) {
    return OPipe.of(id)
      .map(chatService::loadChat)
      .map(chatMapper::mapTo)
      .map(ResponseEntity::ok)
      .value;
  }

  @DeleteMapping("/chat/{id}")
  public ResponseEntity<?> deleteChat(@PathVariable String id) {
    chatService.deleteChat(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/chat/{id}/post-message")
  public ResponseEntity<String> postMessage(@PathVariable String id, @RequestBody String prompt) {
    return OPipe.of(chatService.getReply(id, prompt).getResponse()).map(ResponseEntity::ok).value;
  }

  @DeleteMapping("/chat/{id}/message/{messageId}")
  public ResponseEntity<?> deleteMessage(@PathVariable String id, @PathVariable String messageId) {
    chatService.deleteMessage(messageId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
