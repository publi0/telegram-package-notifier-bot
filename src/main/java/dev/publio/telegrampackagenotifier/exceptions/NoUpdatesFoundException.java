package dev.publio.telegrampackagenotifier.exceptions;

public class NoUpdatesFoundException extends
    RuntimeException {

  public NoUpdatesFoundException(String trackingNumber) {
    super(trackingNumber);
  }
}
