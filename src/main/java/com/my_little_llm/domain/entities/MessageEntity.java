package com.my_little_llm.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class MessageEntity { // ToDo - use Cassandra when reaching 100k users

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String prompt;

  private String response;

  @ManyToOne
  private ChatEntity chat;

  private java.sql.Timestamp timestamp;
}
