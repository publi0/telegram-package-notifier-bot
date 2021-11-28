package dev.publio.telegrampackagenotifier.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeMessage {
  private String attribute;
  private String message;
}