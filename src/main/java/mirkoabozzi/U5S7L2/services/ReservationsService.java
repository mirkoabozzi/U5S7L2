package mirkoabozzi.U5S7L2.services;

import mirkoabozzi.U5S7L2.dto.ReservationsDTO;
import mirkoabozzi.U5S7L2.dto.ReservationsUpdateDTO;
import mirkoabozzi.U5S7L2.entities.Employee;
import mirkoabozzi.U5S7L2.entities.Reservation;
import mirkoabozzi.U5S7L2.entities.Trip;
import mirkoabozzi.U5S7L2.exceptions.BadRequestException;
import mirkoabozzi.U5S7L2.exceptions.NotFoundException;
import mirkoabozzi.U5S7L2.repositories.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationsService {
    @Autowired
    private ReservationsRepository reservationsRepository;
    @Autowired
    private EmployeesService employeesService;
    @Autowired
    private TripsService tripsService;
    @Autowired
    private JavaMailSender javaMailSender;

    //POST
    public Reservation save(ReservationsDTO payload) {
        Employee employeeFound = this.employeesService.findById(payload.employeeId());
        Trip tripFound = this.tripsService.findById(payload.tripId());
        if (reservationsRepository.existsByEmployeeAndTripDate(employeeFound, tripFound.getDate()))
            throw new BadRequestException("You already have a reservation for this day: " + tripFound.getDate());
        Reservation reservation = new Reservation(payload.note(), employeeFound, tripFound);
        Reservation saved = this.reservationsRepository.save(reservation);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(employeeFound.getEmail());
        msg.setSubject("Enjoy your trip!");
        msg.setText("Hi " + employeeFound.getName() + " " + employeeFound.getSurname() + " your reservation for " + tripFound.getDestination() + " on " + tripFound.getDate() + " is confirmed!");
        javaMailSender.send(msg);

        return saved;
    }

    //GET
    public Page<Reservation> findAll(int page, int size, String shortBy) {
        if (page > 50) page = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(shortBy));
        return this.reservationsRepository.findAll(pageable);
    }

    //GET BY ID
    public Reservation findById(UUID id) {
        return reservationsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    //PUT
    public Reservation update(UUID id, ReservationsUpdateDTO payload) {
        Reservation found = this.findById(id);
        found.setNote(payload.note());
        return this.reservationsRepository.save(found);
    }

    //DELETE
    public void delete(UUID id) {
        this.reservationsRepository.delete(this.findById(id));
    }


    //GET
    public List<Reservation> findReservationByEmployee(Employee employee) {
        return this.reservationsRepository.findByEmployee(employee);
    }
}
