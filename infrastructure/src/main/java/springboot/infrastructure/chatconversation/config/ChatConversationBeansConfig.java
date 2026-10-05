package springboot.infrastructure.chatconversation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.chatconversation.usecase.DeleteChatConversationUseCase;
import springboot.application.chatconversation.usecase.GetChatConversationByIdUseCase;
import springboot.application.chatconversation.usecase.ListChatConversationUseCase;
import springboot.application.chatconversation.usecase.RegisterChatConversationUseCase;
import springboot.application.chatconversation.usecase.UpdateChatConversationUseCase;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.conversationstatus.port.repository.ConversationStatusRepository;
import springboot.domain.priority.port.repository.PriorityRepository;
import springboot.infrastructure.chatconversation.adapters.out.persistence.mappers.ChatConversationPersistenceMapper;
import springboot.infrastructure.chatconversation.adapters.out.persistence.repositories.ChatConversationJpaRepository;
import springboot.infrastructure.chatconversation.adapters.out.persistence.repositories.ChatConversationRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ChatConversation: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ChatConversationBeansConfig {

    @Bean
    public ChatConversationPersistenceMapper chatConversationPersistenceMapper() {
        return new ChatConversationPersistenceMapper();
    }

    @Bean
    public ChatConversationRepository chatConversationRepository(ChatConversationJpaRepository jpaRepository, ChatConversationPersistenceMapper mapper) {
        return new ChatConversationRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatConversationUseCase registerChatConversationUseCase(
            ChatConversationRepository repository, ConversationStatusRepository conversationStatusRepository, PriorityRepository priorityRepository, DomainEventPublisher eventPublisher) {
        return new RegisterChatConversationUseCase(repository, conversationStatusRepository, priorityRepository, eventPublisher);
    }

    @Bean
    public GetChatConversationByIdUseCase getChatConversationByIdUseCase(ChatConversationRepository repository) {
        return new GetChatConversationByIdUseCase(repository);
    }

    @Bean
    public ListChatConversationUseCase listChatConversationUseCase(ChatConversationRepository repository) {
        return new ListChatConversationUseCase(repository);
    }

    @Bean
    public UpdateChatConversationUseCase updateChatConversationUseCase(
            ChatConversationRepository repository, ConversationStatusRepository conversationStatusRepository, PriorityRepository priorityRepository, DomainEventPublisher eventPublisher) {
        return new UpdateChatConversationUseCase(repository, conversationStatusRepository, priorityRepository, eventPublisher);
    }

    @Bean
    public DeleteChatConversationUseCase deleteChatConversationUseCase(ChatConversationRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteChatConversationUseCase(repository, eventPublisher);
    }
}
