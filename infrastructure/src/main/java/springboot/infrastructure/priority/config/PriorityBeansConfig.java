package springboot.infrastructure.priority.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.priority.usecase.DeletePriorityUseCase;
import springboot.application.priority.usecase.GetPriorityByIdUseCase;
import springboot.application.priority.usecase.ListPriorityUseCase;
import springboot.application.priority.usecase.RegisterPriorityUseCase;
import springboot.application.priority.usecase.UpdatePriorityUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.priority.port.repository.PriorityRepository;
import springboot.infrastructure.priority.adapters.out.persistence.mappers.PriorityPersistenceMapper;
import springboot.infrastructure.priority.adapters.out.persistence.repositories.PriorityJpaRepository;
import springboot.infrastructure.priority.adapters.out.persistence.repositories.PriorityRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context Priority: adaptador de persistencia y casos de uso.
 */
@Configuration
public class PriorityBeansConfig {

    @Bean
    public PriorityPersistenceMapper priorityPersistenceMapper() {
        return new PriorityPersistenceMapper();
    }

    @Bean
    public PriorityRepository priorityRepository(PriorityJpaRepository jpaRepository, PriorityPersistenceMapper mapper) {
        return new PriorityRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterPriorityUseCase registerPriorityUseCase(
            PriorityRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterPriorityUseCase(repository, eventPublisher);
    }

    @Bean
    public GetPriorityByIdUseCase getPriorityByIdUseCase(PriorityRepository repository) {
        return new GetPriorityByIdUseCase(repository);
    }

    @Bean
    public ListPriorityUseCase listPriorityUseCase(PriorityRepository repository) {
        return new ListPriorityUseCase(repository);
    }

    @Bean
    public UpdatePriorityUseCase updatePriorityUseCase(
            PriorityRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdatePriorityUseCase(repository, eventPublisher);
    }

    @Bean
    public DeletePriorityUseCase deletePriorityUseCase(PriorityRepository repository, DomainEventPublisher eventPublisher) {
        return new DeletePriorityUseCase(repository, eventPublisher);
    }
}
