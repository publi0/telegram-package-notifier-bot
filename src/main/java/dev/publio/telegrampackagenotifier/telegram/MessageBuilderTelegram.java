package dev.publio.telegrampackagenotifier.telegram;

import dev.publio.telegrampackagenotifier.models.Package;
import dev.publio.telegrampackagenotifier.models.ShippingUpdate;
import dev.publio.telegrampackagenotifier.shipping.companies.ShippingCompanies;
import java.time.format.DateTimeFormatter;

public class MessageBuilderTelegram {

  public static String buildTrackingUpdateMessage(ShippingUpdate shippingUpdate) {
    return String.format("""
            🚩 - %s
            📦 - %s
            📅 - %s
            """, shippingUpdate.currentLocation(), shippingUpdate.operation(),
        shippingUpdate.dateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
  }

  public static String buildPackageInfoMessage(Package packageInfo) {
    return String.format("""
            ℹ️‎‎‎ %s
            🏢‎‎‎ %s
            """, packageInfo.getTrackId(),
        ShippingCompanies.getShippingCompany(packageInfo.getTransporter()).getName());
  }

  public static String buildCompanyButtonText(ShippingCompanies shippingCompany) {
    return shippingCompany.getName();
  }
}
