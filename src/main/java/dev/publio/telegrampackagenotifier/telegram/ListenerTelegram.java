package dev.publio.telegrampackagenotifier.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class ListenerTelegram {

  private final TelegramBot bot;
  private final ProcessMessageTelegram processMessageTelegram;

  public ListenerTelegram(TelegramBot bot,
      ProcessMessageTelegram processMessageTelegram) {
    this.bot = bot;
    this.processMessageTelegram = processMessageTelegram;
  }

  @PostConstruct
  public void run() {
    bot.setUpdatesListener(updates -> {
      updates.forEach(update -> {
        if (update.callbackQuery() != null) {
          processMessageTelegram.processCallback(update);
        }
        if (update.message() != null) {
          processMessageTelegram.processMessage(update);
        }
      });
      return UpdatesListener.CONFIRMED_UPDATES_ALL;
    });
  }
}
