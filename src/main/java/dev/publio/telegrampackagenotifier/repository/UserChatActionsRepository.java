package dev.publio.telegrampackagenotifier.repository;

import dev.publio.telegrampackagenotifier.models.UserChatActions;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface UserChatActionsRepository extends Repository<UserChatActions, String> {
  Optional<UserChatActions> findByUserId(String userId);

  void deleteByUserId(String userId);

  void save(UserChatActions userChatActions);
}
