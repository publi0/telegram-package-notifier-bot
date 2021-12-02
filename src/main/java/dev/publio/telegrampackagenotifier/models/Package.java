package dev.publio.telegrampackagenotifier.models;

import dev.publio.telegrampackagenotifier.shipping.companies.ShippingCompanies;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "packages")
@Data
@NoArgsConstructor
public class Package {

  @Id
  private String id;

  private String trackId;

  private ShippingCompanies transporter;

  private String user;

  private Boolean isActive;

  private Set<ShippingUpdate> updates = new HashSet<>();

  private LocalDateTime lastUpdate;
}
