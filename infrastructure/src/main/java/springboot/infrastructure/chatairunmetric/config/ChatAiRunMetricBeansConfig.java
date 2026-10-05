package springboot.infrastructure.chatairunmetric.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.chatairunmetric.usecase.DeleteChatAiRunMetricUseCase;
import springboot.application.chatairunmetric.usecase.GetChatAiRunMetricByIdUseCase;
import springboot.application.chatairunmetric.usecase.ListChatAiRunMetricUseCase;
import springboot.application.chatairunmetric.usecase.RegisterChatAiRunMetricUseCase;
import springboot.application.chatairunmetric.usecase.UpdateChatAiRunMetricUseCase;
import springboot.domain.chatairun.port.repository.ChatAiRunRepository;
import springboot.domain.chatairunmetric.port.repository.ChatAiRunMetricRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.infrastructure.chatairunmetric.adapters.out.persistence.mappers.ChatAiRunMetricPersistenceMapper;
import springboot.infrastructure.chatairunmetric.adapters.out.persistence.repositories.ChatAiRunMetricJpaRepository;
import springboot.infrastructure.chatairunmetric.adapters.out.persistence.repositories.ChatAiRunMetricRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ChatAiRunMetric: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ChatAiRunMetricBeansConfig {

    @Bean
    public ChatAiRunMetricPersistenceMapper chatAiRunMetricPersistenceMapper() {
        return new ChatAiRunMetricPersistenceMapper();
    }

    @Bean
    public ChatAiRunMetricRepository chatAiRunMetricRepository(ChatAiRunMetricJpaRepository jpaRepository, ChatAiRunMetricPersistenceMapper mapper) {
        return new ChatAiRunMetricRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatAiRunMetricUseCase registerChatAiRunMetricUseCase(
            ChatAiRunMetricRepository repository, ChatAiRunRepository chatAiRunRepository, DomainEventPublisher eventPublisher) {
        return new RegisterChatAiRunMetricUseCase(repository, chatAiRunRepository, eventPublisher);
    }

    @Bean
    public GetChatAiRunMetricByIdUseCase getChatAiRunMetricByIdUseCase(ChatAiRunMetricRepository repository) {
        return new GetChatAiRunMetricByIdUseCase(repository);
    }

    @Bean
    public ListChatAiRunMetricUseCase listChatAiRunMetricUseCase(ChatAiRunMetricRepository repository) {
        return new ListChatAiRunMetricUseCase(repository);
    }

    @Bean
    public UpdateChatAiRunMetricUseCase updateChatAiRunMetricUseCase(
            ChatAiRunMetricRepository repository, ChatAiRunRepository chatAiRunRepository, DomainEventPublisher eventPublisher) {
        return new UpdateChatAiRunMetricUseCase(repository, chatAiRunRepository, eventPublisher);
    }

    @Bean
    public DeleteChatAiRunMetricUseCase deleteChatAiRunMetricUseCase(ChatAiRunMetricRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteChatAiRunMetricUseCase(repository, eventPublisher);
    }
}
