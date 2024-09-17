package mirkoabozzi.U5S7L2.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mirkoabozzi.U5S7L2.enums.TripsState;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String destination;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private TripsState state;

    public Trip(String destination, LocalDate date, TripsState state) {
        this.destination = destination;
        this.date = date;
        this.state = state;
    }
}
