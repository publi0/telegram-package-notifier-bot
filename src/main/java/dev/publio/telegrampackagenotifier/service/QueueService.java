package dev.publio.telegrampackagenotifier.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.publio.telegrampackagenotifier.dto.QueueTelegramMessage;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class QueueService {

  private final RabbitTemplate template;
  private final ObjectMapper mapper;
  private final Queue telegramQueue;

  public QueueService(RabbitTemplate template, ObjectMapper mapper,
      Queue telegramQueue) {
    this.template = template;
    this.mapper = mapper;
    this.telegramQueue = telegramQueue;
  }

  @SneakyThrows
  public void sendToTelegramNotification(QueueTelegramMessage message) {
    log.info("Sending message [{}] to queue: {}", message, telegramQueue.getName());
    String valueAsString = mapper.writeValueAsString(message);
    template.convertAndSend(telegramQueue.getName(), valueAsString);
    log.info("Message[{}] sent to queue: {}", valueAsString, telegramQueue.getName());
  }
}
