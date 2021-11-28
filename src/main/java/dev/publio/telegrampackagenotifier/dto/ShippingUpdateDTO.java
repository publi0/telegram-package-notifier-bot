package dev.publio.telegrampackagenotifier.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import dev.publio.telegrampackagenotifier.models.ShippingUpdate;
import dev.publio.telegrampackagenotifier.shipping.companies.ShippingCompanies;
import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record ShippingUpdateDTO(LocalDateTime dateTime, String currentLocation, String operation, ShippingCompanies shippingCompany) {

  public static final String UNAVAILABLE = "Unavailable";

  public ShippingUpdateDTO(LocalDateTime dateTime, String currentLocation, String operation,
      ShippingCompanies shippingCompany) {
    this.dateTime = dateTime;
    this.currentLocation = currentLocation.isBlank() ? UNAVAILABLE : currentLocation;
    this.operation = operation.isBlank() ? UNAVAILABLE : operation;
    this.shippingCompany = shippingCompany;
  }

  public ShippingUpdate toShippingUpdate() {
    return new ShippingUpdate(dateTime, currentLocation, operation, shippingCompany);
  }
}
