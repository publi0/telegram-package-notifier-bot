package dev.publio.telegrampackagenotifier.repository;


import dev.publio.telegrampackagenotifier.models.ShippingCompany;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface ShippingCompanyRepository extends Repository<ShippingCompany, String> {

  Optional<ShippingCompany> findById(String id);

  Optional<ShippingCompany> findByName(String name);
}
