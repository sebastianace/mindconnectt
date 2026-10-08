package springboot.infrastructure.user.adapters.out.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import springboot.domain.user.port.security.PasswordHasher;

/** Adaptador del puerto PasswordHasher: delega en el PasswordEncoder (BCrypt) de Spring Security. */
public class BcryptPasswordHasher implements PasswordHasher {
    private final PasswordEncoder passwordEncoder;

    public BcryptPasswordHasher(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
