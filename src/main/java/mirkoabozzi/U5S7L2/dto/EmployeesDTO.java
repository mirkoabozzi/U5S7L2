package mirkoabozzi.U5S7L2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmployeesDTO(
        @NotNull(message = "Username is required. ")
        @Size(min = 3, max = 20, message = "Size must be from 3 to 20 character. ")
        String username,
        @NotNull(message = "Name is required. ")
        @Size(min = 3, max = 20, message = "Size must be from 3 to 20 character. ")
        String name,
        @NotNull(message = "Surname is required. ")
        @Size(min = 3, max = 20, message = "Size must be from 3 to 20 character. ")
        String surname,
        @Email(message = "Email is required. ")
        String email,
        @NotNull(message = "Password is required. ")
        String password
) {
}
