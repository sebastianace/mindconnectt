package springboot.infrastructure.sendertype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.sendertype.usecase.DeleteSenderTypeUseCase;
import springboot.application.sendertype.usecase.GetSenderTypeByIdUseCase;
import springboot.application.sendertype.usecase.ListSenderTypeUseCase;
import springboot.application.sendertype.usecase.RegisterSenderTypeUseCase;
import springboot.application.sendertype.usecase.UpdateSenderTypeUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.sendertype.port.repository.SenderTypeRepository;
import springboot.infrastructure.sendertype.adapters.out.persistence.mappers.SenderTypePersistenceMapper;
import springboot.infrastructure.sendertype.adapters.out.persistence.repositories.SenderTypeJpaRepository;
import springboot.infrastructure.sendertype.adapters.out.persistence.repositories.SenderTypeRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context SenderType: adaptador de persistencia y casos de uso.
 */
@Configuration
public class SenderTypeBeansConfig {

    @Bean
    public SenderTypePersistenceMapper senderTypePersistenceMapper() {
        return new SenderTypePersistenceMapper();
    }

    @Bean
    public SenderTypeRepository senderTypeRepository(SenderTypeJpaRepository jpaRepository, SenderTypePersistenceMapper mapper) {
        return new SenderTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterSenderTypeUseCase registerSenderTypeUseCase(
            SenderTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterSenderTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public GetSenderTypeByIdUseCase getSenderTypeByIdUseCase(SenderTypeRepository repository) {
        return new GetSenderTypeByIdUseCase(repository);
    }

    @Bean
    public ListSenderTypeUseCase listSenderTypeUseCase(SenderTypeRepository repository) {
        return new ListSenderTypeUseCase(repository);
    }

    @Bean
    public UpdateSenderTypeUseCase updateSenderTypeUseCase(
            SenderTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateSenderTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteSenderTypeUseCase deleteSenderTypeUseCase(SenderTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteSenderTypeUseCase(repository, eventPublisher);
    }
}
