package dev.publio.telegrampackagenotifier.shipping.factory;

import dev.publio.telegrampackagenotifier.shipping.companies.ShippingCompanies;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShippingCompanyFactory {

  private Map<ShippingCompanies, ShippingCompany> companies;

  @Autowired
  public ShippingCompanyFactory(Set<ShippingCompany> strategySet) {
    createStrategy(strategySet);
  }

  public ShippingCompany findCompany(ShippingCompanies company) {
    return companies.get(company);
  }

  private void createStrategy(Set<ShippingCompany> strategySet) {
    companies = new HashMap<>();
    strategySet.forEach(strategy -> companies.put(strategy.getCompanyName(), strategy));
  }
}
