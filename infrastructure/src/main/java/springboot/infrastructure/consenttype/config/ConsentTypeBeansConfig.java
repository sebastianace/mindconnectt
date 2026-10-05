package springboot.infrastructure.consenttype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.consenttype.usecase.DeleteConsentTypeUseCase;
import springboot.application.consenttype.usecase.GetConsentTypeByIdUseCase;
import springboot.application.consenttype.usecase.ListConsentTypeUseCase;
import springboot.application.consenttype.usecase.RegisterConsentTypeUseCase;
import springboot.application.consenttype.usecase.UpdateConsentTypeUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.consenttype.port.repository.ConsentTypeRepository;
import springboot.infrastructure.consenttype.adapters.out.persistence.mappers.ConsentTypePersistenceMapper;
import springboot.infrastructure.consenttype.adapters.out.persistence.repositories.ConsentTypeJpaRepository;
import springboot.infrastructure.consenttype.adapters.out.persistence.repositories.ConsentTypeRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ConsentType: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ConsentTypeBeansConfig {

    @Bean
    public ConsentTypePersistenceMapper consentTypePersistenceMapper() {
        return new ConsentTypePersistenceMapper();
    }

    @Bean
    public ConsentTypeRepository consentTypeRepository(ConsentTypeJpaRepository jpaRepository, ConsentTypePersistenceMapper mapper) {
        return new ConsentTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterConsentTypeUseCase registerConsentTypeUseCase(
            ConsentTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterConsentTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public GetConsentTypeByIdUseCase getConsentTypeByIdUseCase(ConsentTypeRepository repository) {
        return new GetConsentTypeByIdUseCase(repository);
    }

    @Bean
    public ListConsentTypeUseCase listConsentTypeUseCase(ConsentTypeRepository repository) {
        return new ListConsentTypeUseCase(repository);
    }

    @Bean
    public UpdateConsentTypeUseCase updateConsentTypeUseCase(
            ConsentTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateConsentTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteConsentTypeUseCase deleteConsentTypeUseCase(ConsentTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteConsentTypeUseCase(repository, eventPublisher);
    }
}
