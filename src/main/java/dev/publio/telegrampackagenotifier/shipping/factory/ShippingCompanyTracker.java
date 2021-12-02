package dev.publio.telegrampackagenotifier.shipping.factory;

import dev.publio.telegrampackagenotifier.dto.ShippingUpdateDTO;
import dev.publio.telegrampackagenotifier.shipping.companies.ShippingCompanies;
import java.util.Set;

public interface ShippingCompanyTracker {

  ShippingCompanies getCompanyName();

  //  @Cacheable("shippingUpdate")
  Set<ShippingUpdateDTO> getShippingUpdate(String trackId);

}
