package springboot.infrastructure.chatairunerror.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.chatairunerror.usecase.DeleteChatAiRunErrorUseCase;
import springboot.application.chatairunerror.usecase.GetChatAiRunErrorByIdUseCase;
import springboot.application.chatairunerror.usecase.ListChatAiRunErrorUseCase;
import springboot.application.chatairunerror.usecase.RegisterChatAiRunErrorUseCase;
import springboot.application.chatairunerror.usecase.UpdateChatAiRunErrorUseCase;
import springboot.domain.chatairun.port.repository.ChatAiRunRepository;
import springboot.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.infrastructure.chatairunerror.adapters.out.persistence.mappers.ChatAiRunErrorPersistenceMapper;
import springboot.infrastructure.chatairunerror.adapters.out.persistence.repositories.ChatAiRunErrorJpaRepository;
import springboot.infrastructure.chatairunerror.adapters.out.persistence.repositories.ChatAiRunErrorRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ChatAiRunError: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ChatAiRunErrorBeansConfig {

    @Bean
    public ChatAiRunErrorPersistenceMapper chatAiRunErrorPersistenceMapper() {
        return new ChatAiRunErrorPersistenceMapper();
    }

    @Bean
    public ChatAiRunErrorRepository chatAiRunErrorRepository(ChatAiRunErrorJpaRepository jpaRepository, ChatAiRunErrorPersistenceMapper mapper) {
        return new ChatAiRunErrorRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatAiRunErrorUseCase registerChatAiRunErrorUseCase(
            ChatAiRunErrorRepository repository, ChatAiRunRepository chatAiRunRepository, DomainEventPublisher eventPublisher) {
        return new RegisterChatAiRunErrorUseCase(repository, chatAiRunRepository, eventPublisher);
    }

    @Bean
    public GetChatAiRunErrorByIdUseCase getChatAiRunErrorByIdUseCase(ChatAiRunErrorRepository repository) {
        return new GetChatAiRunErrorByIdUseCase(repository);
    }

    @Bean
    public ListChatAiRunErrorUseCase listChatAiRunErrorUseCase(ChatAiRunErrorRepository repository) {
        return new ListChatAiRunErrorUseCase(repository);
    }

    @Bean
    public UpdateChatAiRunErrorUseCase updateChatAiRunErrorUseCase(
            ChatAiRunErrorRepository repository, ChatAiRunRepository chatAiRunRepository, DomainEventPublisher eventPublisher) {
        return new UpdateChatAiRunErrorUseCase(repository, chatAiRunRepository, eventPublisher);
    }

    @Bean
    public DeleteChatAiRunErrorUseCase deleteChatAiRunErrorUseCase(ChatAiRunErrorRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteChatAiRunErrorUseCase(repository, eventPublisher);
    }
}
