package dev.publio.telegrampackagenotifier.exceptions;

public class ActionNotFoundException extends
    RuntimeException {

  public ActionNotFoundException(String action_not_found) {
    super(action_not_found);
  }
}
