package springboot.infrastructure.chatairun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.chatairun.usecase.DeleteChatAiRunUseCase;
import springboot.application.chatairun.usecase.GetChatAiRunByIdUseCase;
import springboot.application.chatairun.usecase.ListChatAiRunUseCase;
import springboot.application.chatairun.usecase.RegisterChatAiRunUseCase;
import springboot.application.chatairun.usecase.UpdateChatAiRunUseCase;
import springboot.domain.aimodel.port.repository.AiModelRepository;
import springboot.domain.airunstatus.port.repository.AiRunStatusRepository;
import springboot.domain.chatairun.port.repository.ChatAiRunRepository;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;
import springboot.domain.chatmessage.port.repository.ChatMessageRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.infrastructure.chatairun.adapters.out.persistence.mappers.ChatAiRunPersistenceMapper;
import springboot.infrastructure.chatairun.adapters.out.persistence.repositories.ChatAiRunJpaRepository;
import springboot.infrastructure.chatairun.adapters.out.persistence.repositories.ChatAiRunRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ChatAiRun: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ChatAiRunBeansConfig {

    @Bean
    public ChatAiRunPersistenceMapper chatAiRunPersistenceMapper() {
        return new ChatAiRunPersistenceMapper();
    }

    @Bean
    public ChatAiRunRepository chatAiRunRepository(ChatAiRunJpaRepository jpaRepository, ChatAiRunPersistenceMapper mapper) {
        return new ChatAiRunRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatAiRunUseCase registerChatAiRunUseCase(
            ChatAiRunRepository repository, ChatConversationRepository chatConversationRepository, ChatMessageRepository chatMessageRepository, AiModelRepository aiModelRepository, AiRunStatusRepository aiRunStatusRepository, DomainEventPublisher eventPublisher) {
        return new RegisterChatAiRunUseCase(repository, chatConversationRepository, chatMessageRepository, aiModelRepository, aiRunStatusRepository, eventPublisher);
    }

    @Bean
    public GetChatAiRunByIdUseCase getChatAiRunByIdUseCase(ChatAiRunRepository repository) {
        return new GetChatAiRunByIdUseCase(repository);
    }

    @Bean
    public ListChatAiRunUseCase listChatAiRunUseCase(ChatAiRunRepository repository) {
        return new ListChatAiRunUseCase(repository);
    }

    @Bean
    public UpdateChatAiRunUseCase updateChatAiRunUseCase(
            ChatAiRunRepository repository, ChatConversationRepository chatConversationRepository, ChatMessageRepository chatMessageRepository, AiModelRepository aiModelRepository, AiRunStatusRepository aiRunStatusRepository, DomainEventPublisher eventPublisher) {
        return new UpdateChatAiRunUseCase(repository, chatConversationRepository, chatMessageRepository, aiModelRepository, aiRunStatusRepository, eventPublisher);
    }

    @Bean
    public DeleteChatAiRunUseCase deleteChatAiRunUseCase(ChatAiRunRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteChatAiRunUseCase(repository, eventPublisher);
    }
}
