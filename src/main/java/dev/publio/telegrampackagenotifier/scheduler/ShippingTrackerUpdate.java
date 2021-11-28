package dev.publio.telegrampackagenotifier.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.publio.telegrampackagenotifier.dto.QueueTelegramMessage;
import dev.publio.telegrampackagenotifier.dto.ShippingUpdateDTO;
import dev.publio.telegrampackagenotifier.models.Package;
import dev.publio.telegrampackagenotifier.models.ShippingUpdate;
import dev.publio.telegrampackagenotifier.service.QueueService;
import dev.publio.telegrampackagenotifier.service.TrackingService;
import dev.publio.telegrampackagenotifier.shipping.companies.ShippingCompanies;
import dev.publio.telegrampackagenotifier.telegram.ListenerTelegram;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
public class ShippingTrackerUpdate {

  private final TrackingService trackingService;
  private final QueueService queueService;
  private final ListenerTelegram listenerTelegram;

  public ShippingTrackerUpdate(
      TrackingService trackingService,
      QueueService queueService,
      ListenerTelegram listenerTelegram) {
    this.trackingService = trackingService;
    this.queueService = queueService;
    this.listenerTelegram = listenerTelegram;
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
  @Scheduled(fixedDelay = 1, timeUnit = java.util.concurrent.TimeUnit.MINUTES)
  public void updateTrackers() throws JsonProcessingException {
    log.info("Updating trackers");
    Set<Package> allActivePackages = trackingService.getAllActivePackages();

    for (Package activePackage : allActivePackages) {
      ShippingUpdateDTO lastUpdate = trackingService.getLastUpdate(activePackage.getTrackId(),
          activePackage.getTransporter());

      Optional<ShippingUpdate> lastSavedUpdate = activePackage.getUpdates().stream()
          .max(Comparator.comparing(ShippingUpdate::dateTime));

      if (lastSavedUpdate.isPresent() && lastSavedUpdate.get().dateTime().equals(lastUpdate.dateTime())) {
          log.info("No new updates for package {}", activePackage.getTrackId());
          continue;
      }
      if (lastSavedUpdate.isEmpty()) {
        log.info("No updates saved for package {}", activePackage.getTrackId());
        Set<ShippingUpdate> shippingUpdates = trackingService.getAllUpdates(activePackage.getTrackId(),
                activePackage.getTransporter()).stream().map(ShippingUpdateDTO::toShippingUpdate)
            .collect(Collectors.toUnmodifiableSet());
        activePackage.setUpdates(shippingUpdates);
      }

      log.info("New updates for package {}", activePackage.getTrackId());
      Package savedPackage = trackingService.savePackage(activePackage);
      log.info("Saved update for package {}", savedPackage.getTrackId());
      QueueTelegramMessage queueTelegramMessage = new QueueTelegramMessage(savedPackage.getTrackId(), lastUpdate);
      queueService.sendToTelegramNotification(queueTelegramMessage);
    }

    log.info("Finished updating trackers");
  }
}
