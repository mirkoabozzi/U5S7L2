package mirkoabozzi.U5S7L2.controllers;

import mirkoabozzi.U5S7L2.dto.TripsChangeStateDTO;
import mirkoabozzi.U5S7L2.dto.TripsDTO;
import mirkoabozzi.U5S7L2.entities.Trip;
import mirkoabozzi.U5S7L2.exceptions.BadRequestException;
import mirkoabozzi.U5S7L2.services.TripsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trips")
public class TripsController {
    @Autowired
    private TripsService tripsService;

    //POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Trip saveTrip(@RequestBody @Validated TripsDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return tripsService.save(payload);
        }

    }

    //GET
    @GetMapping
    private Page<Trip> getTrips(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "destination") String sortBy) {
        return this.tripsService.findAll(page, size, sortBy);
    }

    //GET BY ID
    @GetMapping("/{id}")
    public Trip findById(@PathVariable UUID id) {
        return tripsService.findById(id);
    }

    //PUT
    @PutMapping("/{id}")
    private Trip update(@PathVariable UUID id, @RequestBody @Validated TripsDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return tripsService.update(id, payload);
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        this.tripsService.delete(id);
    }

    //PUT STATE TRIP
    @PutMapping("/state/{id}")
    private Trip updateStateTrip(@PathVariable UUID id, @RequestBody @Validated TripsChangeStateDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return tripsService.updateStateTrip(id, payload);
        }
    }
}
