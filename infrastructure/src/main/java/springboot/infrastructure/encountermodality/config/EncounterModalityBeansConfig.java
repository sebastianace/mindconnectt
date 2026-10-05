package springboot.infrastructure.encountermodality.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.encountermodality.usecase.DeleteEncounterModalityUseCase;
import springboot.application.encountermodality.usecase.GetEncounterModalityByIdUseCase;
import springboot.application.encountermodality.usecase.ListEncounterModalityUseCase;
import springboot.application.encountermodality.usecase.RegisterEncounterModalityUseCase;
import springboot.application.encountermodality.usecase.UpdateEncounterModalityUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encountermodality.port.repository.EncounterModalityRepository;
import springboot.infrastructure.encountermodality.adapters.out.persistence.mappers.EncounterModalityPersistenceMapper;
import springboot.infrastructure.encountermodality.adapters.out.persistence.repositories.EncounterModalityJpaRepository;
import springboot.infrastructure.encountermodality.adapters.out.persistence.repositories.EncounterModalityRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context EncounterModality: adaptador de persistencia y casos de uso.
 */
@Configuration
public class EncounterModalityBeansConfig {

    @Bean
    public EncounterModalityPersistenceMapper encounterModalityPersistenceMapper() {
        return new EncounterModalityPersistenceMapper();
    }

    @Bean
    public EncounterModalityRepository encounterModalityRepository(EncounterModalityJpaRepository jpaRepository, EncounterModalityPersistenceMapper mapper) {
        return new EncounterModalityRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterEncounterModalityUseCase registerEncounterModalityUseCase(
            EncounterModalityRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterEncounterModalityUseCase(repository, eventPublisher);
    }

    @Bean
    public GetEncounterModalityByIdUseCase getEncounterModalityByIdUseCase(EncounterModalityRepository repository) {
        return new GetEncounterModalityByIdUseCase(repository);
    }

    @Bean
    public ListEncounterModalityUseCase listEncounterModalityUseCase(EncounterModalityRepository repository) {
        return new ListEncounterModalityUseCase(repository);
    }

    @Bean
    public UpdateEncounterModalityUseCase updateEncounterModalityUseCase(
            EncounterModalityRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateEncounterModalityUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteEncounterModalityUseCase deleteEncounterModalityUseCase(EncounterModalityRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteEncounterModalityUseCase(repository, eventPublisher);
    }
}
