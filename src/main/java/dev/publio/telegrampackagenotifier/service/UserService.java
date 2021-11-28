package dev.publio.telegrampackagenotifier.service;

import dev.publio.telegrampackagenotifier.models.User;
import dev.publio.telegrampackagenotifier.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Boolean isUserActive(String chatId) {
    log.info("Is user active? chatId: {}", chatId);
    return userRepository.findByChatId(chatId)
        .map(x -> x.getActive() == Boolean.TRUE)
        .orElse(Boolean.FALSE);
  }

  public User createUserIfNotExists(Long chatId, String firstName, String username) {
    log.info("Create user if not exists? chatId: {}", chatId);
    return userRepository.findByChatId(chatId.toString()).orElseGet(() -> {
      log.info("Creating new user: {}", chatId);
      return userRepository.save(new User(chatId.toString(), firstName, username));
    });
  }

  public User findUser(String chatId) {
    log.info("Get user? chatId: {}", chatId);
    return userRepository.findByChatId(chatId)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
