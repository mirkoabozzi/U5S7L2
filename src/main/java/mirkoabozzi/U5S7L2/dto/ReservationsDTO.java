package mirkoabozzi.U5S7L2.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservationsDTO(
        @NotNull(message = "Employee required. ")
        UUID employeeId,
        @NotNull(message = "Trip is required. ")
        UUID tripId,
        String note
) {
}
