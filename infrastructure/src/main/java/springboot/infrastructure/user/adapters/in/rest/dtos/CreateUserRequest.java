package springboot.infrastructure.user.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Alta de usuarios por un administrador: puede crear otros administradores. */
public record CreateUserRequest(
        @NotBlank(message = "username is required")
        @Pattern(regexp = "^[A-Za-z0-9._-]{3,50}$",
                message = "username must have 3 to 50 characters (letters, numbers, '.', '_' or '-')")
        String username,

        @NotBlank(message = "password is required")
        @Size(min = 8, max = 72, message = "password must have between 8 and 72 characters")
        String password,

        boolean admin
) {
    @Override
    public String toString() {
        return "CreateUserRequest[username=" + username + ", admin=" + admin + "]";
    }
}
