package dev.publio.telegrampackagenotifier.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.publio.telegrampackagenotifier.dto.QueueTelegramMessage;
import dev.publio.telegrampackagenotifier.telegram.UserNotifierTelegram;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class PackageUpdateListener {

  private final UserNotifierTelegram userNotifierTelegram;
  private final ObjectMapper objectMapper;

  public PackageUpdateListener(
      UserNotifierTelegram userNotifierTelegram,
      ObjectMapper objectMapper) {
    this.userNotifierTelegram = userNotifierTelegram;
    this.objectMapper = objectMapper;
  }

  @RabbitListener(queues = "${queue.name}")
  public void listen(String queueTelegramMessage) {
    log.info("Received message: " + queueTelegramMessage);
    try {
      final QueueTelegramMessage telegramMessage = objectMapper.readValue(queueTelegramMessage,
          QueueTelegramMessage.class);
      userNotifierTelegram.notify(telegramMessage);
    } catch (JsonProcessingException e) {
      log.error("Error processing message: " + queueTelegramMessage, e);
    }
  }
}
