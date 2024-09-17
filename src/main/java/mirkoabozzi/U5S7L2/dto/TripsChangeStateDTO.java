package mirkoabozzi.U5S7L2.dto;

import jakarta.validation.constraints.NotNull;

public record TripsChangeStateDTO(
        @NotNull(message = "State is required. ")
        String state
) {
}
