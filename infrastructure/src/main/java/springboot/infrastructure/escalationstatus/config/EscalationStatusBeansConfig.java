package springboot.infrastructure.escalationstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.escalationstatus.usecase.DeleteEscalationStatusUseCase;
import springboot.application.escalationstatus.usecase.GetEscalationStatusByIdUseCase;
import springboot.application.escalationstatus.usecase.ListEscalationStatusUseCase;
import springboot.application.escalationstatus.usecase.RegisterEscalationStatusUseCase;
import springboot.application.escalationstatus.usecase.UpdateEscalationStatusUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.escalationstatus.port.repository.EscalationStatusRepository;
import springboot.infrastructure.escalationstatus.adapters.out.persistence.mappers.EscalationStatusPersistenceMapper;
import springboot.infrastructure.escalationstatus.adapters.out.persistence.repositories.EscalationStatusJpaRepository;
import springboot.infrastructure.escalationstatus.adapters.out.persistence.repositories.EscalationStatusRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context EscalationStatus: adaptador de persistencia y casos de uso.
 */
@Configuration
public class EscalationStatusBeansConfig {

    @Bean
    public EscalationStatusPersistenceMapper escalationStatusPersistenceMapper() {
        return new EscalationStatusPersistenceMapper();
    }

    @Bean
    public EscalationStatusRepository escalationStatusRepository(EscalationStatusJpaRepository jpaRepository, EscalationStatusPersistenceMapper mapper) {
        return new EscalationStatusRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterEscalationStatusUseCase registerEscalationStatusUseCase(
            EscalationStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterEscalationStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public GetEscalationStatusByIdUseCase getEscalationStatusByIdUseCase(EscalationStatusRepository repository) {
        return new GetEscalationStatusByIdUseCase(repository);
    }

    @Bean
    public ListEscalationStatusUseCase listEscalationStatusUseCase(EscalationStatusRepository repository) {
        return new ListEscalationStatusUseCase(repository);
    }

    @Bean
    public UpdateEscalationStatusUseCase updateEscalationStatusUseCase(
            EscalationStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateEscalationStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteEscalationStatusUseCase deleteEscalationStatusUseCase(EscalationStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteEscalationStatusUseCase(repository, eventPublisher);
    }
}
