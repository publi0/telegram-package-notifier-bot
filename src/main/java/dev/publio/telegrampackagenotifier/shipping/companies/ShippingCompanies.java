package dev.publio.telegrampackagenotifier.shipping.companies;

import dev.publio.telegrampackagenotifier.exceptions.CompanyNotSupportedException;
import java.util.Locale;

public enum ShippingCompanies {

  DIRECT("Direct log"),
  CORREIOS("Correios"),
  MADEIRA_MADEIRA("Madeira Madeira");

  private final String name;

  ShippingCompanies(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static ShippingCompanies getShippingCompany(String name) {
    try {
      return valueOf(name.toUpperCase(Locale.ROOT));
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw new CompanyNotSupportedException(name);
    }
  }

  public static ShippingCompanies getFromName(String name) {
    for (ShippingCompanies company : values()) {
      if (company.getName().trim().equalsIgnoreCase(name.trim())) {
        return company;
      }
    }
    throw new CompanyNotSupportedException(name);
  }
}
