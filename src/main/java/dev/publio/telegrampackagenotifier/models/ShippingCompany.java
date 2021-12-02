package dev.publio.telegrampackagenotifier.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "shipping_companies")
public class ShippingCompany {

  @Id
  private String id;

  private String name;

  private boolean active;

  private String url;
}
