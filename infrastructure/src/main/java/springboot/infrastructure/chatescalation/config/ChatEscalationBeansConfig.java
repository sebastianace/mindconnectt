package springboot.infrastructure.chatescalation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.chatescalation.usecase.DeleteChatEscalationUseCase;
import springboot.application.chatescalation.usecase.GetChatEscalationByIdUseCase;
import springboot.application.chatescalation.usecase.ListChatEscalationUseCase;
import springboot.application.chatescalation.usecase.RegisterChatEscalationUseCase;
import springboot.application.chatescalation.usecase.UpdateChatEscalationUseCase;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;
import springboot.domain.chatescalation.port.repository.ChatEscalationRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.escalationstatus.port.repository.EscalationStatusRepository;
import springboot.infrastructure.chatescalation.adapters.out.persistence.mappers.ChatEscalationPersistenceMapper;
import springboot.infrastructure.chatescalation.adapters.out.persistence.repositories.ChatEscalationJpaRepository;
import springboot.infrastructure.chatescalation.adapters.out.persistence.repositories.ChatEscalationRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ChatEscalation: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ChatEscalationBeansConfig {

    @Bean
    public ChatEscalationPersistenceMapper chatEscalationPersistenceMapper() {
        return new ChatEscalationPersistenceMapper();
    }

    @Bean
    public ChatEscalationRepository chatEscalationRepository(ChatEscalationJpaRepository jpaRepository, ChatEscalationPersistenceMapper mapper) {
        return new ChatEscalationRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatEscalationUseCase registerChatEscalationUseCase(
            ChatEscalationRepository repository, ChatConversationRepository chatConversationRepository, EscalationStatusRepository escalationStatusRepository, DomainEventPublisher eventPublisher) {
        return new RegisterChatEscalationUseCase(repository, chatConversationRepository, escalationStatusRepository, eventPublisher);
    }

    @Bean
    public GetChatEscalationByIdUseCase getChatEscalationByIdUseCase(ChatEscalationRepository repository) {
        return new GetChatEscalationByIdUseCase(repository);
    }

    @Bean
    public ListChatEscalationUseCase listChatEscalationUseCase(ChatEscalationRepository repository) {
        return new ListChatEscalationUseCase(repository);
    }

    @Bean
    public UpdateChatEscalationUseCase updateChatEscalationUseCase(
            ChatEscalationRepository repository, ChatConversationRepository chatConversationRepository, EscalationStatusRepository escalationStatusRepository, DomainEventPublisher eventPublisher) {
        return new UpdateChatEscalationUseCase(repository, chatConversationRepository, escalationStatusRepository, eventPublisher);
    }

    @Bean
    public DeleteChatEscalationUseCase deleteChatEscalationUseCase(ChatEscalationRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteChatEscalationUseCase(repository, eventPublisher);
    }
}
