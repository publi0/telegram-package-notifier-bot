package dev.publio.telegrampackagenotifier.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "packages")
public class Package {

  @Id
  private String id;
  private String trackId;
  private String transporter;
  private String user;
  private Boolean isActive;
  private Set<ShippingUpdate> updates = new HashSet<>();

  public Package(String id, String trackId, String transporter, String user,
      Boolean isActive, Set<ShippingUpdate> updates) {
    this.id = id;
    this.trackId = trackId;
    this.transporter = transporter;
    this.user = user;
    this.isActive = isActive;
    this.updates = updates;
  }

  public Package(String trackId, String transporter, String user, Boolean isActive,
      Set<ShippingUpdate> updates) {
    this.trackId = trackId;
    this.transporter = transporter;
    this.user = user;
    this.isActive = isActive;
    this.updates = updates;
  }

  public Package() {
  }

  public String getTrackId() {
    return trackId;
  }

  public String getTransporter() {
    return transporter;
  }


  public Set<ShippingUpdate> getUpdates() {
    return updates;
  }

  public String getId() {
    return id;
  }

  public String getUser() {
    return user;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setTrackId(String trackId) {
    this.trackId = trackId;
  }

  public void setTransporter(String transporter) {
    this.transporter = transporter;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public void setUpdates(Set<ShippingUpdate> updates) {
    this.updates = updates;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    var that = (Package) obj;
    return Objects.equals(this.id, that.id) &&
        Objects.equals(this.trackId, that.trackId) &&
        Objects.equals(this.transporter, that.transporter) &&
        Objects.equals(this.user, that.user) &&
        Objects.equals(this.isActive, that.isActive) &&
        Objects.equals(this.updates, that.updates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, trackId, transporter, user, isActive, updates);
  }

  @Override
  public String toString() {
    return "Package[" +
        "id=" + id + ", " +
        "trackId=" + trackId + ", " +
        "transporter=" + transporter + ", " +
        "user=" + user + ", " +
        "isActive=" + isActive + ", " +
        "updates=" + updates + ']';
  }

}
