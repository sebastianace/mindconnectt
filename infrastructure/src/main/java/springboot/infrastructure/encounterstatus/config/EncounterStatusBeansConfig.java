package springboot.infrastructure.encounterstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.encounterstatus.usecase.DeleteEncounterStatusUseCase;
import springboot.application.encounterstatus.usecase.GetEncounterStatusByIdUseCase;
import springboot.application.encounterstatus.usecase.ListEncounterStatusUseCase;
import springboot.application.encounterstatus.usecase.RegisterEncounterStatusUseCase;
import springboot.application.encounterstatus.usecase.UpdateEncounterStatusUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounterstatus.port.repository.EncounterStatusRepository;
import springboot.infrastructure.encounterstatus.adapters.out.persistence.mappers.EncounterStatusPersistenceMapper;
import springboot.infrastructure.encounterstatus.adapters.out.persistence.repositories.EncounterStatusJpaRepository;
import springboot.infrastructure.encounterstatus.adapters.out.persistence.repositories.EncounterStatusRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context EncounterStatus: adaptador de persistencia y casos de uso.
 */
@Configuration
public class EncounterStatusBeansConfig {

    @Bean
    public EncounterStatusPersistenceMapper encounterStatusPersistenceMapper() {
        return new EncounterStatusPersistenceMapper();
    }

    @Bean
    public EncounterStatusRepository encounterStatusRepository(EncounterStatusJpaRepository jpaRepository, EncounterStatusPersistenceMapper mapper) {
        return new EncounterStatusRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterEncounterStatusUseCase registerEncounterStatusUseCase(
            EncounterStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterEncounterStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public GetEncounterStatusByIdUseCase getEncounterStatusByIdUseCase(EncounterStatusRepository repository) {
        return new GetEncounterStatusByIdUseCase(repository);
    }

    @Bean
    public ListEncounterStatusUseCase listEncounterStatusUseCase(EncounterStatusRepository repository) {
        return new ListEncounterStatusUseCase(repository);
    }

    @Bean
    public UpdateEncounterStatusUseCase updateEncounterStatusUseCase(
            EncounterStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateEncounterStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteEncounterStatusUseCase deleteEncounterStatusUseCase(EncounterStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteEncounterStatusUseCase(repository, eventPublisher);
    }
}
