package springboot.infrastructure.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import springboot.application.user.usecase.ListUserUseCase;
import springboot.application.user.usecase.RegisterUserUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.role.port.repository.RoleRepository;
import springboot.domain.user.port.repository.UserRepository;
import springboot.domain.user.port.security.PasswordHasher;
import springboot.infrastructure.user.adapters.out.persistence.mappers.UserPersistenceMapper;
import springboot.infrastructure.user.adapters.out.persistence.repositories.UserJpaRepository;
import springboot.infrastructure.user.adapters.out.persistence.repositories.UserRepositoryAdapter;
import springboot.infrastructure.user.adapters.out.security.BcryptPasswordHasher;

/** Ensambla explícitamente el bounded context User: adaptadores y casos de uso. */
@Configuration
public class UserBeansConfig {

    @Bean
    public UserPersistenceMapper userPersistenceMapper() {
        return new UserPersistenceMapper();
    }

    @Bean
    public UserRepository userRepository(UserJpaRepository jpaRepository, UserPersistenceMapper mapper) {
        return new UserRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public PasswordHasher passwordHasher(PasswordEncoder passwordEncoder) {
        return new BcryptPasswordHasher(passwordEncoder);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordHasher passwordHasher,
            DomainEventPublisher eventPublisher) {
        return new RegisterUserUseCase(userRepository, roleRepository, passwordHasher, eventPublisher);
    }

    @Bean
    public ListUserUseCase listUserUseCase(UserRepository userRepository, RoleRepository roleRepository) {
        return new ListUserUseCase(userRepository, roleRepository);
    }
}
