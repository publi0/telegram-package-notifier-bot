package dev.publio.telegrampackagenotifier.shipping.companies.directlog;

import java.time.LocalDate;
import java.time.LocalTime;

public record DirectTrackDTO(LocalDate date, LocalTime time,
                             String operation, String location,
                             String observation) {

}
