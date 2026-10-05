package springboot.infrastructure.chatmessage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.chatmessage.usecase.DeleteChatMessageUseCase;
import springboot.application.chatmessage.usecase.GetChatMessageByIdUseCase;
import springboot.application.chatmessage.usecase.ListChatMessageUseCase;
import springboot.application.chatmessage.usecase.RegisterChatMessageUseCase;
import springboot.application.chatmessage.usecase.UpdateChatMessageUseCase;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;
import springboot.domain.chatmessage.port.repository.ChatMessageRepository;
import springboot.domain.chatparticipant.port.repository.ChatParticipantRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.messagetype.port.repository.MessageTypeRepository;
import springboot.infrastructure.chatmessage.adapters.out.persistence.mappers.ChatMessagePersistenceMapper;
import springboot.infrastructure.chatmessage.adapters.out.persistence.repositories.ChatMessageJpaRepository;
import springboot.infrastructure.chatmessage.adapters.out.persistence.repositories.ChatMessageRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ChatMessage: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ChatMessageBeansConfig {

    @Bean
    public ChatMessagePersistenceMapper chatMessagePersistenceMapper() {
        return new ChatMessagePersistenceMapper();
    }

    @Bean
    public ChatMessageRepository chatMessageRepository(ChatMessageJpaRepository jpaRepository, ChatMessagePersistenceMapper mapper) {
        return new ChatMessageRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatMessageUseCase registerChatMessageUseCase(
            ChatMessageRepository repository, ChatConversationRepository chatConversationRepository, MessageTypeRepository messageTypeRepository, ChatParticipantRepository chatParticipantRepository, DomainEventPublisher eventPublisher) {
        return new RegisterChatMessageUseCase(repository, chatConversationRepository, messageTypeRepository, chatParticipantRepository, eventPublisher);
    }

    @Bean
    public GetChatMessageByIdUseCase getChatMessageByIdUseCase(ChatMessageRepository repository) {
        return new GetChatMessageByIdUseCase(repository);
    }

    @Bean
    public ListChatMessageUseCase listChatMessageUseCase(ChatMessageRepository repository) {
        return new ListChatMessageUseCase(repository);
    }

    @Bean
    public UpdateChatMessageUseCase updateChatMessageUseCase(
            ChatMessageRepository repository, ChatConversationRepository chatConversationRepository, MessageTypeRepository messageTypeRepository, ChatParticipantRepository chatParticipantRepository, DomainEventPublisher eventPublisher) {
        return new UpdateChatMessageUseCase(repository, chatConversationRepository, messageTypeRepository, chatParticipantRepository, eventPublisher);
    }

    @Bean
    public DeleteChatMessageUseCase deleteChatMessageUseCase(ChatMessageRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteChatMessageUseCase(repository, eventPublisher);
    }
}
