package springboot.application.user.command;

import java.util.Objects;

/**
 * @param rawPassword contraseña en texto plano; solo vive hasta que el caso de uso la convierte en hash.
 * @param admin       true agrega ROLE_ADMIN además de ROLE_USER.
 */
public record RegisterUserCommand(
        String username,
        String rawPassword,
        boolean admin
) {
    public RegisterUserCommand {
        Objects.requireNonNull(username, "username must not be null");
        Objects.requireNonNull(rawPassword, "rawPassword must not be null");
    }

    /** La contraseña nunca debe aparecer en logs ni en el toString del record. */
    @Override
    public String toString() {
        return "RegisterUserCommand[username=" + username + ", admin=" + admin + "]";
    }
}
