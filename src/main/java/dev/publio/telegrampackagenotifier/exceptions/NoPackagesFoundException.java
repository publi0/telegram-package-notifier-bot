package dev.publio.telegrampackagenotifier.exceptions;

public class NoPackagesFoundException extends
    RuntimeException {

  public NoPackagesFoundException(String id) {
    super("No packages found for id: " + id);
  }
}
