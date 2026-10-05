package springboot.infrastructure.chatescalationstatushistory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.chatescalationstatushistory.usecase.DeleteChatEscalationStatusHistoryUseCase;
import springboot.application.chatescalationstatushistory.usecase.GetChatEscalationStatusHistoryByIdUseCase;
import springboot.application.chatescalationstatushistory.usecase.ListChatEscalationStatusHistoryUseCase;
import springboot.application.chatescalationstatushistory.usecase.RegisterChatEscalationStatusHistoryUseCase;
import springboot.application.chatescalationstatushistory.usecase.UpdateChatEscalationStatusHistoryUseCase;
import springboot.domain.chatescalation.port.repository.ChatEscalationRepository;
import springboot.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.escalationstatus.port.repository.EscalationStatusRepository;
import springboot.infrastructure.chatescalationstatushistory.adapters.out.persistence.mappers.ChatEscalationStatusHistoryPersistenceMapper;
import springboot.infrastructure.chatescalationstatushistory.adapters.out.persistence.repositories.ChatEscalationStatusHistoryJpaRepository;
import springboot.infrastructure.chatescalationstatushistory.adapters.out.persistence.repositories.ChatEscalationStatusHistoryRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ChatEscalationStatusHistory: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ChatEscalationStatusHistoryBeansConfig {

    @Bean
    public ChatEscalationStatusHistoryPersistenceMapper chatEscalationStatusHistoryPersistenceMapper() {
        return new ChatEscalationStatusHistoryPersistenceMapper();
    }

    @Bean
    public ChatEscalationStatusHistoryRepository chatEscalationStatusHistoryRepository(ChatEscalationStatusHistoryJpaRepository jpaRepository, ChatEscalationStatusHistoryPersistenceMapper mapper) {
        return new ChatEscalationStatusHistoryRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatEscalationStatusHistoryUseCase registerChatEscalationStatusHistoryUseCase(
            ChatEscalationStatusHistoryRepository repository, ChatEscalationRepository chatEscalationRepository, EscalationStatusRepository escalationStatusRepository, DomainEventPublisher eventPublisher) {
        return new RegisterChatEscalationStatusHistoryUseCase(repository, chatEscalationRepository, escalationStatusRepository, eventPublisher);
    }

    @Bean
    public GetChatEscalationStatusHistoryByIdUseCase getChatEscalationStatusHistoryByIdUseCase(ChatEscalationStatusHistoryRepository repository) {
        return new GetChatEscalationStatusHistoryByIdUseCase(repository);
    }

    @Bean
    public ListChatEscalationStatusHistoryUseCase listChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository) {
        return new ListChatEscalationStatusHistoryUseCase(repository);
    }

    @Bean
    public UpdateChatEscalationStatusHistoryUseCase updateChatEscalationStatusHistoryUseCase(
            ChatEscalationStatusHistoryRepository repository, ChatEscalationRepository chatEscalationRepository, EscalationStatusRepository escalationStatusRepository, DomainEventPublisher eventPublisher) {
        return new UpdateChatEscalationStatusHistoryUseCase(repository, chatEscalationRepository, escalationStatusRepository, eventPublisher);
    }

    @Bean
    public DeleteChatEscalationStatusHistoryUseCase deleteChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteChatEscalationStatusHistoryUseCase(repository, eventPublisher);
    }
}
