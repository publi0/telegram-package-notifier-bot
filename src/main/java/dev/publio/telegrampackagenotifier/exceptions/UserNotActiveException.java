package dev.publio.telegrampackagenotifier.exceptions;

public class UserNotActiveException extends Throwable {

  public UserNotActiveException(String id) {
    super(id);
  }
}
