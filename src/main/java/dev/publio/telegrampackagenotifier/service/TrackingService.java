package dev.publio.telegrampackagenotifier.service;

import dev.publio.telegrampackagenotifier.dto.ShippingUpdateDTO;
import dev.publio.telegrampackagenotifier.exceptions.NoPackagesFoundException;
import dev.publio.telegrampackagenotifier.exceptions.NoUpdatesFoundException;
import dev.publio.telegrampackagenotifier.models.Package;
import dev.publio.telegrampackagenotifier.repository.PackageRepository;
import dev.publio.telegrampackagenotifier.shipping.companies.ShippingCompanies;
import dev.publio.telegrampackagenotifier.shipping.factory.ShippingCompanyFactory;
import java.util.Comparator;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TrackingService {

  private final ShippingCompanyFactory shippingCompanyFactory;
  private final PackageRepository packageRepository;

  public TrackingService(
      ShippingCompanyFactory shippingCompanyFactory,
      PackageRepository packageRepository) {
    this.shippingCompanyFactory = shippingCompanyFactory;
    this.packageRepository = packageRepository;
  }

  public ShippingUpdateDTO getLastUpdate(String trackingNumber, ShippingCompanies company) {
    log.info("Getting last update for tracking number: " + trackingNumber);
    Set<ShippingUpdateDTO> shippingUpdate = shippingCompanyFactory.findCompany(company)
        .getShippingUpdate(trackingNumber);

    log.info("Found " + shippingUpdate.size() + " updates");
    return shippingUpdate.stream()
        .max(Comparator.comparing(ShippingUpdateDTO::dateTime))
        .orElseThrow(() -> new NoUpdatesFoundException(trackingNumber));
  }

  public Set<ShippingUpdateDTO> getAllUpdates(String trackingNumber, ShippingCompanies company) {
    log.info("Getting all updates for tracking number: " + trackingNumber);
    return shippingCompanyFactory.findCompany(company)
        .getShippingUpdate(trackingNumber);
  }

  public Set<Package> getAllActivePackages() {
    log.info("Searching active packages");
    return packageRepository.findAllByIsActiveTrue();
  }

  public Package addShippingUpdate(Package activePackage, ShippingUpdateDTO lastUpdate) {
    log.info("Saving update for package: " + activePackage.getTrackId());
    activePackage.getUpdates().add(lastUpdate.toShippingUpdate());
    return packageRepository.save(activePackage);
  }

  public Package savePackage(Package newPackage) {
    log.info("Saving new package: " + newPackage.getTrackId());
    return packageRepository.save(newPackage);
  }

  public Set<Package> getAllActivePackagesByUser(String id) {
    log.info("Searching active packages for user: " + id);
    Set<Package> packages = packageRepository.findAllByIsActiveTrueAndUser(id);
    log.info("Found " + packages.size() + " packages");
    return packages;
  }

  public Package getPackageByIdAndUser(String trackId, String userId) {
    log.info("Searching package: " + trackId);
    return packageRepository.findByIdAndUser(trackId, userId)
        .orElseThrow(() -> new NoPackagesFoundException(trackId));
  }

  public Package getPackageByTrackIdAndUserId(String trackId, String userId) {
    log.info("Searching package: " + trackId);
    return packageRepository.findByTrackIdAndUser(trackId, userId)
        .orElseThrow(() -> new NoPackagesFoundException(trackId));
  }

  public Package createPackage(String trackId, ShippingCompanies company, String userId) {
    log.info("Creating new package: " + trackId);
    final var build = Package.builder()
        .trackId(trackId)
        .isActive(true)
        .transporter(company)
        .user(userId)
        .build();
    return savePackage(build);
  }
}
