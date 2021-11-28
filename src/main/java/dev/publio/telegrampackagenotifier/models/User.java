package dev.publio.telegrampackagenotifier.models;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

  @Id
  private String id;

  private String chatId;

  private String username;

  private String firstName;

  private boolean isActive;

  private LocalDateTime createdAt;

  public User(String id, String chatId, String username, String firstName, boolean isActive,
      LocalDateTime createdAt) {
    this.id = id;
    this.chatId = chatId;
    this.username = username;
    this.firstName = firstName;
    this.isActive = isActive;
    this.createdAt = createdAt;
  }

  public User(String chatId, String firstName, String username) {
    this.id = null;
    this.chatId = chatId;
    this.firstName = firstName;
    this.username = username;
    this.isActive = false;
    this.createdAt = LocalDateTime.now();
  }

  public User() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getChatId() {
    return chatId;
  }

  public void setChatId(String chatId) {
    this.chatId = chatId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Boolean getActive() {
    return isActive;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(chatId, user.chatId)
        && Objects.equals(username, user.username) && Objects.equals(isActive,
        user.isActive) && Objects.equals(createdAt, user.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, chatId, username, isActive, createdAt);
  }

  @Override
  public String toString() {
    return "User{" +
        "id='" + id + '\'' +
        ", chatId='" + chatId + '\'' +
        ", username='" + username + '\'' +
        ", isActive=" + isActive +
        ", createdAt=" + createdAt +
        '}';
  }
}
