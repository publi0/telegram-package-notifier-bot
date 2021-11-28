package dev.publio.telegrampackagenotifier.models;

import dev.publio.telegrampackagenotifier.shipping.companies.ShippingCompanies;
import java.time.LocalDateTime;

public record ShippingUpdate(LocalDateTime dateTime, String currentLocation, String operation, ShippingCompanies shippingCompany, LocalDateTime userAcknowledgeDateTime) {
}
