package springboot.infrastructure.messagetype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.messagetype.usecase.DeleteMessageTypeUseCase;
import springboot.application.messagetype.usecase.GetMessageTypeByIdUseCase;
import springboot.application.messagetype.usecase.ListMessageTypeUseCase;
import springboot.application.messagetype.usecase.RegisterMessageTypeUseCase;
import springboot.application.messagetype.usecase.UpdateMessageTypeUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.messagetype.port.repository.MessageTypeRepository;
import springboot.infrastructure.messagetype.adapters.out.persistence.mappers.MessageTypePersistenceMapper;
import springboot.infrastructure.messagetype.adapters.out.persistence.repositories.MessageTypeJpaRepository;
import springboot.infrastructure.messagetype.adapters.out.persistence.repositories.MessageTypeRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context MessageType: adaptador de persistencia y casos de uso.
 */
@Configuration
public class MessageTypeBeansConfig {

    @Bean
    public MessageTypePersistenceMapper messageTypePersistenceMapper() {
        return new MessageTypePersistenceMapper();
    }

    @Bean
    public MessageTypeRepository messageTypeRepository(MessageTypeJpaRepository jpaRepository, MessageTypePersistenceMapper mapper) {
        return new MessageTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterMessageTypeUseCase registerMessageTypeUseCase(
            MessageTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterMessageTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public GetMessageTypeByIdUseCase getMessageTypeByIdUseCase(MessageTypeRepository repository) {
        return new GetMessageTypeByIdUseCase(repository);
    }

    @Bean
    public ListMessageTypeUseCase listMessageTypeUseCase(MessageTypeRepository repository) {
        return new ListMessageTypeUseCase(repository);
    }

    @Bean
    public UpdateMessageTypeUseCase updateMessageTypeUseCase(
            MessageTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateMessageTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteMessageTypeUseCase deleteMessageTypeUseCase(MessageTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteMessageTypeUseCase(repository, eventPublisher);
    }
}
