package springboot.infrastructure.gender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.gender.usecase.DeleteGenderUseCase;
import springboot.application.gender.usecase.GetGenderByIdUseCase;
import springboot.application.gender.usecase.ListGenderUseCase;
import springboot.application.gender.usecase.RegisterGenderUseCase;
import springboot.application.gender.usecase.UpdateGenderUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.gender.port.repository.GenderRepository;
import springboot.infrastructure.gender.adapters.out.persistence.mappers.GenderPersistenceMapper;
import springboot.infrastructure.gender.adapters.out.persistence.repositories.GenderJpaRepository;
import springboot.infrastructure.gender.adapters.out.persistence.repositories.GenderRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context Gender: adaptador de persistencia y casos de uso.
 */
@Configuration
public class GenderBeansConfig {

    @Bean
    public GenderPersistenceMapper genderPersistenceMapper() {
        return new GenderPersistenceMapper();
    }

    @Bean
    public GenderRepository genderRepository(GenderJpaRepository jpaRepository, GenderPersistenceMapper mapper) {
        return new GenderRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterGenderUseCase registerGenderUseCase(
            GenderRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterGenderUseCase(repository, eventPublisher);
    }

    @Bean
    public GetGenderByIdUseCase getGenderByIdUseCase(GenderRepository repository) {
        return new GetGenderByIdUseCase(repository);
    }

    @Bean
    public ListGenderUseCase listGenderUseCase(GenderRepository repository) {
        return new ListGenderUseCase(repository);
    }

    @Bean
    public UpdateGenderUseCase updateGenderUseCase(
            GenderRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateGenderUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteGenderUseCase deleteGenderUseCase(GenderRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteGenderUseCase(repository, eventPublisher);
    }
}
