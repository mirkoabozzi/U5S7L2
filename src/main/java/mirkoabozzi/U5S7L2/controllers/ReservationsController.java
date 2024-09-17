package mirkoabozzi.U5S7L2.controllers;

import mirkoabozzi.U5S7L2.dto.ReservationsDTO;
import mirkoabozzi.U5S7L2.dto.ReservationsUpdateDTO;
import mirkoabozzi.U5S7L2.entities.Reservation;
import mirkoabozzi.U5S7L2.exceptions.BadRequestException;
import mirkoabozzi.U5S7L2.services.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
    @Autowired
    private ReservationsService reservationsService;

    //POST RESERVATION
    @PostMapping
    private Reservation saveReservation(@RequestBody @Validated ReservationsDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(" ."));
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return reservationsService.save(payload);
        }
    }

    //GET
    @GetMapping
    private Page<Reservation> getReservations(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "date") String sortBy) {
        return this.reservationsService.findAll(page, size, sortBy);
    }

    //GET BY ID
    @GetMapping("/{id}")
    public Reservation findById(@PathVariable UUID id) {
        return reservationsService.findById(id);
    }

    //PUT
    @PutMapping("/{id}")
    private Reservation update(@PathVariable UUID id, @RequestBody @Validated ReservationsUpdateDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException("Payload Error: " + msg);
        } else {
            return this.reservationsService.update(id, payload);
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        this.reservationsService.delete(id);
    }
}
