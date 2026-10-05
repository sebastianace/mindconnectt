package springboot.infrastructure.providermodelai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.providermodelai.usecase.DeleteProviderModelAiUseCase;
import springboot.application.providermodelai.usecase.GetProviderModelAiByIdUseCase;
import springboot.application.providermodelai.usecase.ListProviderModelAiUseCase;
import springboot.application.providermodelai.usecase.RegisterProviderModelAiUseCase;
import springboot.application.providermodelai.usecase.UpdateProviderModelAiUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.providermodelai.port.repository.ProviderModelAiRepository;
import springboot.infrastructure.providermodelai.adapters.out.persistence.mappers.ProviderModelAiPersistenceMapper;
import springboot.infrastructure.providermodelai.adapters.out.persistence.repositories.ProviderModelAiJpaRepository;
import springboot.infrastructure.providermodelai.adapters.out.persistence.repositories.ProviderModelAiRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ProviderModelAi: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ProviderModelAiBeansConfig {

    @Bean
    public ProviderModelAiPersistenceMapper providerModelAiPersistenceMapper() {
        return new ProviderModelAiPersistenceMapper();
    }

    @Bean
    public ProviderModelAiRepository providerModelAiRepository(ProviderModelAiJpaRepository jpaRepository, ProviderModelAiPersistenceMapper mapper) {
        return new ProviderModelAiRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterProviderModelAiUseCase registerProviderModelAiUseCase(
            ProviderModelAiRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterProviderModelAiUseCase(repository, eventPublisher);
    }

    @Bean
    public GetProviderModelAiByIdUseCase getProviderModelAiByIdUseCase(ProviderModelAiRepository repository) {
        return new GetProviderModelAiByIdUseCase(repository);
    }

    @Bean
    public ListProviderModelAiUseCase listProviderModelAiUseCase(ProviderModelAiRepository repository) {
        return new ListProviderModelAiUseCase(repository);
    }

    @Bean
    public UpdateProviderModelAiUseCase updateProviderModelAiUseCase(
            ProviderModelAiRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateProviderModelAiUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteProviderModelAiUseCase deleteProviderModelAiUseCase(ProviderModelAiRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteProviderModelAiUseCase(repository, eventPublisher);
    }
}
