package springboot.infrastructure.user.adapters.in.rest.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import springboot.application.user.command.RegisterUserCommand;
import springboot.application.user.dto.UserResponse;
import springboot.application.user.usecase.ListUserUseCase;
import springboot.application.user.usecase.RegisterUserUseCase;
import springboot.infrastructure.user.adapters.in.rest.dtos.CreateUserRequest;
import springboot.infrastructure.user.adapters.in.rest.dtos.RegisterUserRequest;

/**
 * - POST /api/users/register  público: crea siempre un usuario con ROLE_USER.
 * - POST /api/users           solo ROLE_ADMIN: crea usuarios y, opcionalmente, administradores.
 * - GET  /api/users           solo ROLE_ADMIN: lista usuarios (sin contraseñas).
 * Las reglas de acceso viven en SpringSecurityConfig.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final RegisterUserUseCase registerUseCase;
    private final ListUserUseCase listUseCase;

    public UserController(RegisterUserUseCase registerUseCase, ListUserUseCase listUseCase) {
        this.registerUseCase = registerUseCase;
        this.listUseCase = listUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        var command = new RegisterUserCommand(request.username(), request.password(), false);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        var command = new RegisterUserCommand(request.username(), request.password(), request.admin());
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(listUseCase.execute());
    }
}
