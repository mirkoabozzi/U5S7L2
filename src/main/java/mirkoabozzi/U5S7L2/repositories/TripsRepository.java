package mirkoabozzi.U5S7L2.repositories;

import mirkoabozzi.U5S7L2.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface TripsRepository extends JpaRepository<Trip, UUID> {

    boolean existsByDestinationAndDate(String destination, LocalDate date);
}
