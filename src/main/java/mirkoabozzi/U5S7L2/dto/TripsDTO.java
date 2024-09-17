package mirkoabozzi.U5S7L2.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TripsDTO(
        @NotNull(message = "Destination is required. ")
        String destination,
        @NotNull(message = "Date is required. ")
        LocalDate date,
        @NotNull(message = "State is required. ")
        String state
) {
}
