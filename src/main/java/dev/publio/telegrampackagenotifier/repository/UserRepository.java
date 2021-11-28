package dev.publio.telegrampackagenotifier.repository;

import dev.publio.telegrampackagenotifier.models.User;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, String> {

  Optional<User> findByChatIdAndActiveTrue(String chatId);

  Optional<User> findByChatId(String chatId);

  User save(User newUser);

  Optional<User> findById(String userId);

  Optional<User> findByUsername(String username);
}
