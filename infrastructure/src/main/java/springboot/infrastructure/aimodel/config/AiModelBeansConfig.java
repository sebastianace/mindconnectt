package springboot.infrastructure.aimodel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.aimodel.usecase.DeleteAiModelUseCase;
import springboot.application.aimodel.usecase.GetAiModelByIdUseCase;
import springboot.application.aimodel.usecase.ListAiModelUseCase;
import springboot.application.aimodel.usecase.RegisterAiModelUseCase;
import springboot.application.aimodel.usecase.UpdateAiModelUseCase;
import springboot.domain.aimodel.port.repository.AiModelRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.providermodelai.port.repository.ProviderModelAiRepository;
import springboot.infrastructure.aimodel.adapters.out.persistence.mappers.AiModelPersistenceMapper;
import springboot.infrastructure.aimodel.adapters.out.persistence.repositories.AiModelJpaRepository;
import springboot.infrastructure.aimodel.adapters.out.persistence.repositories.AiModelRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context AiModel: adaptador de persistencia y casos de uso.
 */
@Configuration
public class AiModelBeansConfig {

    @Bean
    public AiModelPersistenceMapper aiModelPersistenceMapper() {
        return new AiModelPersistenceMapper();
    }

    @Bean
    public AiModelRepository aiModelRepository(AiModelJpaRepository jpaRepository, AiModelPersistenceMapper mapper) {
        return new AiModelRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterAiModelUseCase registerAiModelUseCase(
            AiModelRepository repository, ProviderModelAiRepository providerModelAiRepository, DomainEventPublisher eventPublisher) {
        return new RegisterAiModelUseCase(repository, providerModelAiRepository, eventPublisher);
    }

    @Bean
    public GetAiModelByIdUseCase getAiModelByIdUseCase(AiModelRepository repository) {
        return new GetAiModelByIdUseCase(repository);
    }

    @Bean
    public ListAiModelUseCase listAiModelUseCase(AiModelRepository repository) {
        return new ListAiModelUseCase(repository);
    }

    @Bean
    public UpdateAiModelUseCase updateAiModelUseCase(
            AiModelRepository repository, ProviderModelAiRepository providerModelAiRepository, DomainEventPublisher eventPublisher) {
        return new UpdateAiModelUseCase(repository, providerModelAiRepository, eventPublisher);
    }

    @Bean
    public DeleteAiModelUseCase deleteAiModelUseCase(AiModelRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteAiModelUseCase(repository, eventPublisher);
    }
}
