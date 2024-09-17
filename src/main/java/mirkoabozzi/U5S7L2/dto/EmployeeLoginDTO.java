package mirkoabozzi.U5S7L2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmployeeLoginDTO(
        @Email(message = "Email is required. ")
        String email,
        @NotNull(message = "Password is required. ")
        String password
) {
}
