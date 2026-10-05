package springboot.infrastructure.encountertype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.encountertype.usecase.DeleteEncounterTypeUseCase;
import springboot.application.encountertype.usecase.GetEncounterTypeByIdUseCase;
import springboot.application.encountertype.usecase.ListEncounterTypeUseCase;
import springboot.application.encountertype.usecase.RegisterEncounterTypeUseCase;
import springboot.application.encountertype.usecase.UpdateEncounterTypeUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encountertype.port.repository.EncounterTypeRepository;
import springboot.infrastructure.encountertype.adapters.out.persistence.mappers.EncounterTypePersistenceMapper;
import springboot.infrastructure.encountertype.adapters.out.persistence.repositories.EncounterTypeJpaRepository;
import springboot.infrastructure.encountertype.adapters.out.persistence.repositories.EncounterTypeRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context EncounterType: adaptador de persistencia y casos de uso.
 */
@Configuration
public class EncounterTypeBeansConfig {

    @Bean
    public EncounterTypePersistenceMapper encounterTypePersistenceMapper() {
        return new EncounterTypePersistenceMapper();
    }

    @Bean
    public EncounterTypeRepository encounterTypeRepository(EncounterTypeJpaRepository jpaRepository, EncounterTypePersistenceMapper mapper) {
        return new EncounterTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterEncounterTypeUseCase registerEncounterTypeUseCase(
            EncounterTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterEncounterTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public GetEncounterTypeByIdUseCase getEncounterTypeByIdUseCase(EncounterTypeRepository repository) {
        return new GetEncounterTypeByIdUseCase(repository);
    }

    @Bean
    public ListEncounterTypeUseCase listEncounterTypeUseCase(EncounterTypeRepository repository) {
        return new ListEncounterTypeUseCase(repository);
    }

    @Bean
    public UpdateEncounterTypeUseCase updateEncounterTypeUseCase(
            EncounterTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateEncounterTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteEncounterTypeUseCase deleteEncounterTypeUseCase(EncounterTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteEncounterTypeUseCase(repository, eventPublisher);
    }
}
