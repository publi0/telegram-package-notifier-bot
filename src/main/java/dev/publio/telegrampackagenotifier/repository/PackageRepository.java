package dev.publio.telegrampackagenotifier.repository;

import dev.publio.telegrampackagenotifier.models.Package;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface PackageRepository extends Repository<Package, String> {

  Set<Package> findAllByIsActiveTrue();

  Optional<Package> findByTrackId(String trackId);

  Package save(Package item);

  Set<Package> findAllByIsActiveTrueAndUser(String user);
}
