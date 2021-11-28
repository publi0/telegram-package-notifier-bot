package dev.publio.telegrampackagenotifier.service;

import dev.publio.telegrampackagenotifier.models.UserChatActions;
import dev.publio.telegrampackagenotifier.models.enums.ActionsType;
import dev.publio.telegrampackagenotifier.models.enums.ActionsValues;
import dev.publio.telegrampackagenotifier.repository.UserChatActionsRepository;
import java.util.Map;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserChatActionsService {

  private final UserChatActionsRepository userChatRepository;

  public UserChatActionsService(
      UserChatActionsRepository userChatRepository) {
    this.userChatRepository = userChatRepository;
  }

  public void updateAction(String userId, ActionsType action, Map<ActionsValues, String> values) {
    log.info("Updating action for user {}", userId);
    final var userChatActions = new UserChatActions(userId, action, values);
    userChatRepository.save(userChatActions);
  }

  public void deleteAction(String userId) {
    log.info("Deleting action for user {}", userId);
    userChatRepository.deleteByUserId(userId);
  }

  public Optional<UserChatActions> getAction(String userId) {
    log.info("Getting action for user {}", userId);
    return userChatRepository.findByUserId(userId);
  }
}
