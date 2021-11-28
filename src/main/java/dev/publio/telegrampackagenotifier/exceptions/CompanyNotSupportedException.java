package dev.publio.telegrampackagenotifier.exceptions;

public class CompanyNotSupportedException extends RuntimeException {

  public CompanyNotSupportedException(String name) {
    super(String.format("Company [%s] current not supported", name));
  }
}
