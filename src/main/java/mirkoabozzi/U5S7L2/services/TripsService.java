package mirkoabozzi.U5S7L2.services;

import mirkoabozzi.U5S7L2.dto.TripsChangeStateDTO;
import mirkoabozzi.U5S7L2.dto.TripsDTO;
import mirkoabozzi.U5S7L2.entities.Trip;
import mirkoabozzi.U5S7L2.enums.TripsState;
import mirkoabozzi.U5S7L2.exceptions.BadRequestException;
import mirkoabozzi.U5S7L2.exceptions.NotFoundException;
import mirkoabozzi.U5S7L2.repositories.TripsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TripsService {
    @Autowired
    private TripsRepository tripsRepository;

    //POST
    public Trip save(TripsDTO payload) {
        if (tripsRepository.existsByDestinationAndDate(payload.destination(), payload.date()))
            throw new BadRequestException("This trip " + payload.destination() + " on this date " + payload.date() + " already exists on DB");
        try {
            Trip trip = new Trip(payload.destination(), payload.date(), TripsState.valueOf(payload.state().toUpperCase()));
            return this.tripsRepository.save(trip);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid state use SCHEDULED or COMPLETED");
        }
    }

    //GET
    public Page<Trip> findAll(int page, int size, String sortBy) {
        if (page > 50) page = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.tripsRepository.findAll(pageable);
    }

    //GET BY ID
    public Trip findById(UUID id) {
        return tripsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    //PUT
    public Trip update(UUID id, TripsDTO payload) {
        Trip found = this.findById(id);
        found.setDestination(payload.destination());
        found.setDate(payload.date());
        found.setState(TripsState.valueOf(payload.state().toUpperCase()));
        return this.tripsRepository.save(found);
    }

    //PUT UPDATE STATE
    public Trip updateStateTrip(UUID id, TripsChangeStateDTO payload) {
        Trip found = this.findById(id);
        try {
            found.setState(TripsState.valueOf(payload.state()));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid state use SCHEDULED or COMPLETED");
        }
        return this.tripsRepository.save(found);
    }

    //DELETE
    public void delete(UUID id) {
        this.tripsRepository.delete(this.findById(id));
    }
}
