package dev.publio.telegrampackagenotifier.dto;

import java.util.Objects;

public class QueueTelegramMessage {

  private String trackingId;
  private String userId;
  private ShippingUpdateDTO updateDTO;

  public QueueTelegramMessage(String trackingId, String userId,
      ShippingUpdateDTO updateDTO) {
    this.trackingId = trackingId;
    this.userId = userId;
    this.updateDTO = updateDTO;
  }

  public QueueTelegramMessage() {
  }

  public String getTrackingId() {
    return trackingId;
  }

  public void setTrackingId(String trackingId) {
    this.trackingId = trackingId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public ShippingUpdateDTO getUpdateDTO() {
    return updateDTO;
  }

  public void setUpdateDTO(ShippingUpdateDTO updateDTO) {
    this.updateDTO = updateDTO;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (QueueTelegramMessage) obj;
    return Objects.equals(this.trackingId, that.trackingId) &&
        Objects.equals(this.userId, that.userId) &&
        Objects.equals(this.updateDTO, that.updateDTO);
  }

  @Override
  public int hashCode() {
    return Objects.hash(trackingId, userId, updateDTO);
  }

  @Override
  public String toString() {
    return "QueueTelegramMessage[" +
        "trackingId=" + trackingId + ", " +
        "userId=" + userId + ", " +
        "updateDTO=" + updateDTO + ']';
  }

}
