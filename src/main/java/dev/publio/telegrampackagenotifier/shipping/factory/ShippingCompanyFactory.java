package dev.publio.telegrampackagenotifier.shipping.factory;

import dev.publio.telegrampackagenotifier.shipping.companies.ShippingCompanies;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShippingCompanyFactory {

  private Map<ShippingCompanies, ShippingCompanyTracker> companies;

  @Autowired
  public ShippingCompanyFactory(Set<ShippingCompanyTracker> strategySet) {
    createStrategy(strategySet);
  }

  public ShippingCompanyTracker findCompany(ShippingCompanies company) {
    return companies.get(company);
  }

  private void createStrategy(Set<ShippingCompanyTracker> strategySet) {
    companies = new HashMap<>();
    strategySet.forEach(strategy -> companies.put(strategy.getCompanyName(), strategy));
  }
}
