package springboot.infrastructure.conversationstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.conversationstatus.usecase.DeleteConversationStatusUseCase;
import springboot.application.conversationstatus.usecase.GetConversationStatusByIdUseCase;
import springboot.application.conversationstatus.usecase.ListConversationStatusUseCase;
import springboot.application.conversationstatus.usecase.RegisterConversationStatusUseCase;
import springboot.application.conversationstatus.usecase.UpdateConversationStatusUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.conversationstatus.port.repository.ConversationStatusRepository;
import springboot.infrastructure.conversationstatus.adapters.out.persistence.mappers.ConversationStatusPersistenceMapper;
import springboot.infrastructure.conversationstatus.adapters.out.persistence.repositories.ConversationStatusJpaRepository;
import springboot.infrastructure.conversationstatus.adapters.out.persistence.repositories.ConversationStatusRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ConversationStatus: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ConversationStatusBeansConfig {

    @Bean
    public ConversationStatusPersistenceMapper conversationStatusPersistenceMapper() {
        return new ConversationStatusPersistenceMapper();
    }

    @Bean
    public ConversationStatusRepository conversationStatusRepository(ConversationStatusJpaRepository jpaRepository, ConversationStatusPersistenceMapper mapper) {
        return new ConversationStatusRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterConversationStatusUseCase registerConversationStatusUseCase(
            ConversationStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterConversationStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public GetConversationStatusByIdUseCase getConversationStatusByIdUseCase(ConversationStatusRepository repository) {
        return new GetConversationStatusByIdUseCase(repository);
    }

    @Bean
    public ListConversationStatusUseCase listConversationStatusUseCase(ConversationStatusRepository repository) {
        return new ListConversationStatusUseCase(repository);
    }

    @Bean
    public UpdateConversationStatusUseCase updateConversationStatusUseCase(
            ConversationStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateConversationStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteConversationStatusUseCase deleteConversationStatusUseCase(ConversationStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteConversationStatusUseCase(repository, eventPublisher);
    }
}
