package springboot.infrastructure.airunstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.airunstatus.usecase.DeleteAiRunStatusUseCase;
import springboot.application.airunstatus.usecase.GetAiRunStatusByIdUseCase;
import springboot.application.airunstatus.usecase.ListAiRunStatusUseCase;
import springboot.application.airunstatus.usecase.RegisterAiRunStatusUseCase;
import springboot.application.airunstatus.usecase.UpdateAiRunStatusUseCase;
import springboot.domain.airunstatus.port.repository.AiRunStatusRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.infrastructure.airunstatus.adapters.out.persistence.mappers.AiRunStatusPersistenceMapper;
import springboot.infrastructure.airunstatus.adapters.out.persistence.repositories.AiRunStatusJpaRepository;
import springboot.infrastructure.airunstatus.adapters.out.persistence.repositories.AiRunStatusRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context AiRunStatus: adaptador de persistencia y casos de uso.
 */
@Configuration
public class AiRunStatusBeansConfig {

    @Bean
    public AiRunStatusPersistenceMapper aiRunStatusPersistenceMapper() {
        return new AiRunStatusPersistenceMapper();
    }

    @Bean
    public AiRunStatusRepository aiRunStatusRepository(AiRunStatusJpaRepository jpaRepository, AiRunStatusPersistenceMapper mapper) {
        return new AiRunStatusRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterAiRunStatusUseCase registerAiRunStatusUseCase(
            AiRunStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterAiRunStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public GetAiRunStatusByIdUseCase getAiRunStatusByIdUseCase(AiRunStatusRepository repository) {
        return new GetAiRunStatusByIdUseCase(repository);
    }

    @Bean
    public ListAiRunStatusUseCase listAiRunStatusUseCase(AiRunStatusRepository repository) {
        return new ListAiRunStatusUseCase(repository);
    }

    @Bean
    public UpdateAiRunStatusUseCase updateAiRunStatusUseCase(
            AiRunStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateAiRunStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteAiRunStatusUseCase deleteAiRunStatusUseCase(AiRunStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteAiRunStatusUseCase(repository, eventPublisher);
    }
}
