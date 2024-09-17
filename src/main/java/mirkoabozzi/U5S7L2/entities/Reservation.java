package mirkoabozzi.U5S7L2.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "request_date")
    private LocalDate requestDate;
    private String note;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    public Reservation(String note, Employee employee, Trip trip) {
        this.requestDate = LocalDate.now();
        this.note = note;
        this.employee = employee;
        this.trip = trip;
    }
}
