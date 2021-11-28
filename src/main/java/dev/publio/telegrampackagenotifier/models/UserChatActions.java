package dev.publio.telegrampackagenotifier.models;

import dev.publio.telegrampackagenotifier.models.enums.ActionsType;
import dev.publio.telegrampackagenotifier.models.enums.ActionsValues;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_chat_actions")
public record UserChatActions(@Id String userId, ActionsType action, Map<ActionsValues, String> values) {
}
