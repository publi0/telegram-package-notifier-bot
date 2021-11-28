package dev.publio.telegrampackagenotifier.shipping.companies.directlog;

import dev.publio.telegrampackagenotifier.exceptions.UnableToGetShippingUpdateException;
import dev.publio.telegrampackagenotifier.shipping.companies.ShippingCompanies;
import dev.publio.telegrampackagenotifier.shipping.factory.ShippingCompany;
import dev.publio.telegrampackagenotifier.dto.ShippingUpdateDTO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DirectLogTracker implements ShippingCompany {

  public static final int DATE_INDEX = 0;
  public static final int HOUR_INDEX = 1;
  public static final int OPERATION_INDEX = 2;
  public static final int LOCATION_INDEX = 3;
  public static final int OBSERVATION_INDEX = 4;

  @Value("${transporter.urls.direct_log}")
  private String url;

  @Override
  public ShippingCompanies getCompanyName() {
    return ShippingCompanies.DIRECT;
  }

  @Override
  public Set<ShippingUpdateDTO> getShippingUpdate(String trackId) {
    log.info("Get shipping information for track id [{}]", trackId);
    try {
      String urlWithTrackId = mountUrlWithTrackId(trackId);

      log.info("Searching direclog domain");
      Document doc = Jsoup.connect(urlWithTrackId).get();

      log.info("Parsing to our domain object");
      Set<DirectTrackDTO> trackUpdates = getTrackUpdates(doc);

      if (trackUpdates.isEmpty()) {
        log.info("No updates found");
        return Collections.emptySet();
      }

      log.info("Returning parsed object");
      return trackUpdates.stream().map(
          x -> new ShippingUpdateDTO(LocalDateTime.of(x.date(), x.time()), x.location(),
              x.operation(), ShippingCompanies.DIRECT)).collect(
          Collectors.toUnmodifiableSet());

    } catch (IOException e) {
      throw new UnableToGetShippingUpdateException(e.getMessage());
    }
  }

  private Set<DirectTrackDTO> getTrackUpdates(Document doc) {
    return doc.select("[style][bgcolor]").stream()
        .map(element -> element.select("td"))
        .map(row -> new DirectTrackDTO(
            LocalDate.parse(row.get(DATE_INDEX).text(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse(row.get(HOUR_INDEX).text()),
            row.get(OPERATION_INDEX).text(),
            row.get(LOCATION_INDEX).text(),
            row.get(OBSERVATION_INDEX).text()))
        .collect(Collectors.toSet());
  }

  private String mountUrlWithTrackId(String trackId) {
    return String.format(url, trackId);
  }
}
