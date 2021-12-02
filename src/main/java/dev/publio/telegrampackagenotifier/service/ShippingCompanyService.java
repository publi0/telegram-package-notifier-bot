package dev.publio.telegrampackagenotifier.service;

import dev.publio.telegrampackagenotifier.repository.ShippingCompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ShippingCompanyService {

  private final ShippingCompanyRepository shippingCompanyRepository;

  public ShippingCompanyService(
      ShippingCompanyRepository shippingCompanyRepository) {
    this.shippingCompanyRepository = shippingCompanyRepository;
  }
}
