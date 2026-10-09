package springboot.infrastructure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import springboot.application.user.command.RegisterUserCommand;
import springboot.application.user.usecase.RegisterUserUseCase;
import springboot.domain.user.port.repository.UserRepository;

/**
 * Crea el primer administrador al arrancar, solo si ADMIN_USERNAME y ADMIN_PASSWORD están definidos
 * y ese usuario aún no existe. Sin esto nadie podría crear administradores (el registro público
 * solo entrega ROLE_USER) y las credenciales no quedan escritas ni en el código ni en las migraciones.
 */
@Component
public class AdminBootstrapRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(AdminBootstrapRunner.class);

    private final UserRepository userRepository;
    private final RegisterUserUseCase registerUserUseCase;
    private final String username;
    private final String password;

    public AdminBootstrapRunner(
            UserRepository userRepository,
            RegisterUserUseCase registerUserUseCase,
            @Value("${security.bootstrap-admin.username:}") String username,
            @Value("${security.bootstrap-admin.password:}") String password) {
        this.userRepository = userRepository;
        this.registerUserUseCase = registerUserUseCase;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (username.isBlank() || password.isBlank()) {
            return;
        }
        if (userRepository.existsByUsername(username)) {
            return;
        }
        registerUserUseCase.execute(new RegisterUserCommand(username, password, true));
        log.info("Initial administrator '{}' created", username);
    }
}
