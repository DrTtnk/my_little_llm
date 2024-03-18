package com.my_little_llm.domain.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chats")
public class ChatEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Nonnull
  private String name;

  @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
  @Fetch(FetchMode.JOIN)
  @Builder.Default
  private List<MessageEntity> messages = List.of();

  @ManyToOne(cascade = { CascadeType.MERGE })
  @JoinColumn(name = "user_id")
  @Nonnull
  private UserEntity user;

  private String prompt = "";
}
