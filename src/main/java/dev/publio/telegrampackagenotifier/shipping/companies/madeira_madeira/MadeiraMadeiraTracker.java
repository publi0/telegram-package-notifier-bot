package dev.publio.telegrampackagenotifier.shipping.companies.madeira_madeira;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.publio.telegrampackagenotifier.dto.ShippingUpdateDTO;
import dev.publio.telegrampackagenotifier.exceptions.UnableToGetShippingUpdateException;
import dev.publio.telegrampackagenotifier.shipping.companies.ShippingCompanies;
import dev.publio.telegrampackagenotifier.shipping.factory.ShippingCompanyTracker;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MadeiraMadeiraTracker implements ShippingCompanyTracker {

  @Value("${transporter.urls.madeira_madeira}")
  private String madeiraMadeiraUrl;

  private final ObjectMapper objectMapper;

  public MadeiraMadeiraTracker(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public ShippingCompanies getCompanyName() {
    return ShippingCompanies.MADEIRA_MADEIRA;
  }

  @Override
  public Set<ShippingUpdateDTO> getShippingUpdate(String trackId) {
    try {
      final var response = HttpClient.newBuilder()
          .build()
          .send(HttpRequest
                  .newBuilder()
                  .uri(URI.create(mountUrlWithTrackId(trackId)))
                  .GET()
                  .build(),
              BodyHandlers.ofString());
      return Arrays.stream(objectMapper.readValue(response.body(), MadeiraTracker[].class)).map(
              x -> new ShippingUpdateDTO(x.date(), x.origem(), x.description(),
                  ShippingCompanies.MADEIRA_MADEIRA))
          .collect(Collectors.toUnmodifiableSet());
    } catch (Exception e) {
      e.printStackTrace();
      throw new UnableToGetShippingUpdateException(e.getMessage());
    }
  }

  private String mountUrlWithTrackId(String trackId) {
    log.info("Mounting correios url with track id {}", trackId);
    return String.format(madeiraMadeiraUrl, trackId);
  }

}
