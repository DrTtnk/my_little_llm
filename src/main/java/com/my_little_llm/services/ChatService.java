package com.my_little_llm.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my_little_llm.domain.entities.ChatEntity;
import com.my_little_llm.domain.entities.MessageEntity;
import com.my_little_llm.repositories.ChatRepo;
import com.my_little_llm.repositories.MessageRepo;
import com.my_little_llm.repositories.UserRepo;
import com.my_little_llm.utils.OPipe;
import io.vavr.control.Try;
import java.text.MessageFormat;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {

  private final MessageRepo messageRepo;
  private final UserRepo userRepo;
  private final ChatRepo chatRepo;
  private final RestTemplate restTemplate;

  @Value("${app.llm-server}")
  private String llmServer;

  ChatService(MessageRepo messageRepo, UserRepo userRepo, ChatRepo chatRepo) {
    this.messageRepo = messageRepo;
    this.userRepo = userRepo;
    this.chatRepo = chatRepo;
    this.restTemplate = new RestTemplate();
  }

  public ChatEntity loadChat(String chatId) {
    return this.chatRepo.findById(chatId).orElseThrow();
  }

  public ChatEntity startNewChat(String userId, String name, String prompt) {
    var user = userRepo.findById(userId).orElseThrow();

    var chat = ChatEntity.builder().name(name).prompt(prompt).user(user).build();

    return this.chatRepo.save(chat);
  }

  public MessageEntity getReply(String chatId, String prompt) {
    final var chat = this.chatRepo.findById(chatId).orElseThrow();

    final var newPrompt = chat
      .getMessages()
      .stream()
      .map(m -> MessageFormat.format("User: {0}\nAssistant: {1}\n", m.getPrompt(), m.getResponse()))
      .reduce("", String::concat)
      .concat(MessageFormat.format("User: {0}\nAssistant: ", prompt));

    final var response = new OPipe<>(new HttpHeaders())
      .act(h -> h.setContentType(MediaType.APPLICATION_JSON))
      .map(h -> new HttpEntity<>(Map.of("prompt", newPrompt, "n_predict", 2048), h))
      .map(e -> restTemplate.postForObject(llmServer + "/completion", e, String.class))
      .map(r -> Try.of(() -> new ObjectMapper().readTree(r).get("content").asText()).toEither())
      .value.getOrElseThrow(e -> new RuntimeException(e));

    final var newMessage = MessageEntity.builder()
      .prompt(prompt)
      .response(response)
      .chat(chat)
      .build();

    return this.messageRepo.save(newMessage);
  }

  public void deleteMessage(String messageId) {
    this.messageRepo.deleteById(messageId);
  }

  public void deleteChat(String chatId) {
    this.chatRepo.deleteById(chatId);
  }
}
