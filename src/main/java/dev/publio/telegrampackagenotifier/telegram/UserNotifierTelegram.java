package dev.publio.telegrampackagenotifier.telegram;

import static dev.publio.telegrampackagenotifier.telegram.MessageBuilderTelegram.buildTrackingUpdateMessage;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import dev.publio.telegrampackagenotifier.dto.QueueTelegramMessage;
import dev.publio.telegrampackagenotifier.service.UserService;
import java.util.ArrayList;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class UserNotifierTelegram {

  private final TelegramBot telegramBot;
  private final UserService userService;

  public UserNotifierTelegram(TelegramBot telegramBot,
      UserService userService) {
    this.telegramBot = telegramBot;
    this.userService = userService;
  }


  public void notify(QueueTelegramMessage queueTelegramMessage) {
    log.info("Sending message to user: " +queueTelegramMessage.getUserId());
    var messages = new ArrayList<SendMessage>();
    final var user = userService.findUserById(queueTelegramMessage.getUserId());

    messages.add(new SendMessage(user.getChatId(), "🆕 Atualização de pacote: " + queueTelegramMessage.getUpdateDTO().shippingCompany().getName()));
    messages.add(new SendMessage(user.getChatId(), buildTrackingUpdateMessage(queueTelegramMessage.getUpdateDTO().toShippingUpdate())));

    messages.forEach(telegramBot::execute);
  }
}
