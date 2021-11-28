package dev.publio.telegrampackagenotifier.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.publio.telegrampackagenotifier.dto.QueueTelegramMessage;
import dev.publio.telegrampackagenotifier.dto.ShippingUpdateDTO;
import dev.publio.telegrampackagenotifier.models.Package;
import dev.publio.telegrampackagenotifier.models.ShippingUpdate;
import dev.publio.telegrampackagenotifier.service.QueueService;
import dev.publio.telegrampackagenotifier.service.TrackingService;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ShippingTrackerUpdate {

  private final TrackingService trackingService;
  private final QueueService queueService;

  public ShippingTrackerUpdate(
      TrackingService trackingService,
      QueueService queueService) {
    this.trackingService = trackingService;
    this.queueService = queueService;
  }

  @Scheduled(fixedDelay = 1, timeUnit = java.util.concurrent.TimeUnit.MINUTES)
  public void updateTrackers() throws JsonProcessingException {
    log.info("Updating trackers");
    Set<Package> allActivePackages = trackingService.getAllActivePackages();

    for (Package activePackage : allActivePackages) {
      var lastUpdate = trackingService.getLastUpdate(activePackage.getTrackId(),
          activePackage.getTransporter());
    log.info("Last update: {}", lastUpdate);
      Optional<ShippingUpdate> lastSavedUpdate = activePackage.getUpdates().stream()
          .max(Comparator.comparing(ShippingUpdate::dateTime));
      log.info("Last saved update: {}", lastSavedUpdate);
      if (lastSavedUpdate.isPresent() && lastSavedUpdate.get()
          .equals(lastUpdate.toShippingUpdate())) {
        log.info("No new updates for package {}", activePackage.getTrackId());
        continue;
      }
      if (lastSavedUpdate.isEmpty()) {
        log.info("No updates saved for package {}", activePackage.getTrackId());
        Set<ShippingUpdate> shippingUpdates = trackingService.getAllUpdates(
                activePackage.getTrackId(),
                activePackage.getTransporter()).stream().map(ShippingUpdateDTO::toShippingUpdate)
            .collect(Collectors.toUnmodifiableSet());
        activePackage.setUpdates(shippingUpdates);
      } else {
        log.info("New updates for package {}", activePackage.getTrackId());
        activePackage.getUpdates().add(lastUpdate.toShippingUpdate());
      }

      Package savedPackage = trackingService.savePackage(activePackage);
      log.info("Saved update for package {}", savedPackage.getTrackId());
      QueueTelegramMessage queueTelegramMessage = new QueueTelegramMessage(
          savedPackage.getTrackId(),activePackage.getUser(),  lastUpdate);
      queueService.sendToTelegramNotification(queueTelegramMessage);
    }

    log.info("Finished updating trackers");
  }
}
