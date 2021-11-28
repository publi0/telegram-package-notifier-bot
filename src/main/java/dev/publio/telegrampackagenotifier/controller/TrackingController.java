package dev.publio.telegrampackagenotifier.controller;

import dev.publio.telegrampackagenotifier.service.TrackingService;
import dev.publio.telegrampackagenotifier.shipping.companies.ShippingCompanies;
import dev.publio.telegrampackagenotifier.dto.ShippingUpdateDTO;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tracking")
public class TrackingController {

    private final TrackingService trackingService;

    public TrackingController(
        TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/{company}/{trackingNumber}")
    public ResponseEntity<Set<ShippingUpdateDTO>> findPackage(
        @PathVariable String company,
        @PathVariable String trackingNumber) {
        log.info("Request for find package {} in company {}", trackingNumber, company);

        log.info("Validating company support");
        ShippingCompanies shippingCompany = ShippingCompanies.getShippingCompany(company);

        Set<ShippingUpdateDTO> allUpdates = trackingService.getAllUpdates(trackingNumber,
            shippingCompany);

        return ResponseEntity.ok(allUpdates);
    }

    @GetMapping("/{company}/{trackingNumber}/latest")
    public ResponseEntity<ShippingUpdateDTO> findLatestPackage(
        @PathVariable String company,
        @PathVariable String trackingNumber) {
        log.info("Request for find latest package {} in company {}", trackingNumber, company);

        log.info("Validating company support");
        ShippingCompanies shippingCompany = ShippingCompanies.getShippingCompany(company);

        ShippingUpdateDTO allUpdates = trackingService.getLastUpdate(trackingNumber,
            shippingCompany);

        return ResponseEntity.ok(allUpdates);
    }
}
