package dev.publio.telegrampackagenotifier.exceptions;

import dev.publio.telegrampackagenotifier.models.UserChatActions;

public class UserHasActionsException extends
    RuntimeException {

  private final UserChatActions userChatActions;

  public UserHasActionsException(UserChatActions userChatActions) {
    super(userChatActions.action().toString());
    this.userChatActions = userChatActions;
  }

  public UserChatActions getUserChatActions() {
    return userChatActions;
  }
}
