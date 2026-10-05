package springboot.infrastructure.chatconversationaisetting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.chatconversationaisetting.usecase.DeleteChatConversationAiSettingUseCase;
import springboot.application.chatconversationaisetting.usecase.GetChatConversationAiSettingByIdUseCase;
import springboot.application.chatconversationaisetting.usecase.ListChatConversationAiSettingUseCase;
import springboot.application.chatconversationaisetting.usecase.RegisterChatConversationAiSettingUseCase;
import springboot.application.chatconversationaisetting.usecase.UpdateChatConversationAiSettingUseCase;
import springboot.domain.aimodel.port.repository.AiModelRepository;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;
import springboot.domain.chatconversationaisetting.port.repository.ChatConversationAiSettingRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.infrastructure.chatconversationaisetting.adapters.out.persistence.mappers.ChatConversationAiSettingPersistenceMapper;
import springboot.infrastructure.chatconversationaisetting.adapters.out.persistence.repositories.ChatConversationAiSettingJpaRepository;
import springboot.infrastructure.chatconversationaisetting.adapters.out.persistence.repositories.ChatConversationAiSettingRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ChatConversationAiSetting: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ChatConversationAiSettingBeansConfig {

    @Bean
    public ChatConversationAiSettingPersistenceMapper chatConversationAiSettingPersistenceMapper() {
        return new ChatConversationAiSettingPersistenceMapper();
    }

    @Bean
    public ChatConversationAiSettingRepository chatConversationAiSettingRepository(ChatConversationAiSettingJpaRepository jpaRepository, ChatConversationAiSettingPersistenceMapper mapper) {
        return new ChatConversationAiSettingRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatConversationAiSettingUseCase registerChatConversationAiSettingUseCase(
            ChatConversationAiSettingRepository repository, ChatConversationRepository chatConversationRepository, AiModelRepository aiModelRepository, DomainEventPublisher eventPublisher) {
        return new RegisterChatConversationAiSettingUseCase(repository, chatConversationRepository, aiModelRepository, eventPublisher);
    }

    @Bean
    public GetChatConversationAiSettingByIdUseCase getChatConversationAiSettingByIdUseCase(ChatConversationAiSettingRepository repository) {
        return new GetChatConversationAiSettingByIdUseCase(repository);
    }

    @Bean
    public ListChatConversationAiSettingUseCase listChatConversationAiSettingUseCase(ChatConversationAiSettingRepository repository) {
        return new ListChatConversationAiSettingUseCase(repository);
    }

    @Bean
    public UpdateChatConversationAiSettingUseCase updateChatConversationAiSettingUseCase(
            ChatConversationAiSettingRepository repository, ChatConversationRepository chatConversationRepository, AiModelRepository aiModelRepository, DomainEventPublisher eventPublisher) {
        return new UpdateChatConversationAiSettingUseCase(repository, chatConversationRepository, aiModelRepository, eventPublisher);
    }

    @Bean
    public DeleteChatConversationAiSettingUseCase deleteChatConversationAiSettingUseCase(ChatConversationAiSettingRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteChatConversationAiSettingUseCase(repository, eventPublisher);
    }
}
