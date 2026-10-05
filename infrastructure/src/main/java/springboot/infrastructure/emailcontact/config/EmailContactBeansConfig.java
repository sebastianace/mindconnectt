package springboot.infrastructure.emailcontact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.emailcontact.usecase.DeleteEmailContactUseCase;
import springboot.application.emailcontact.usecase.GetEmailContactByIdUseCase;
import springboot.application.emailcontact.usecase.ListEmailContactUseCase;
import springboot.application.emailcontact.usecase.RegisterEmailContactUseCase;
import springboot.application.emailcontact.usecase.UpdateEmailContactUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.contact.port.repository.ContactRepository;
import springboot.domain.emailcontact.port.repository.EmailContactRepository;
import springboot.infrastructure.emailcontact.adapters.out.persistence.mappers.EmailContactPersistenceMapper;
import springboot.infrastructure.emailcontact.adapters.out.persistence.repositories.EmailContactJpaRepository;
import springboot.infrastructure.emailcontact.adapters.out.persistence.repositories.EmailContactRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context EmailContact: adaptador de persistencia y casos de uso.
 */
@Configuration
public class EmailContactBeansConfig {

    @Bean
    public EmailContactPersistenceMapper emailContactPersistenceMapper() {
        return new EmailContactPersistenceMapper();
    }

    @Bean
    public EmailContactRepository emailContactRepository(EmailContactJpaRepository jpaRepository, EmailContactPersistenceMapper mapper) {
        return new EmailContactRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterEmailContactUseCase registerEmailContactUseCase(
            EmailContactRepository repository, ContactRepository contactRepository, DomainEventPublisher eventPublisher) {
        return new RegisterEmailContactUseCase(repository, contactRepository, eventPublisher);
    }

    @Bean
    public GetEmailContactByIdUseCase getEmailContactByIdUseCase(EmailContactRepository repository) {
        return new GetEmailContactByIdUseCase(repository);
    }

    @Bean
    public ListEmailContactUseCase listEmailContactUseCase(EmailContactRepository repository) {
        return new ListEmailContactUseCase(repository);
    }

    @Bean
    public UpdateEmailContactUseCase updateEmailContactUseCase(
            EmailContactRepository repository, ContactRepository contactRepository, DomainEventPublisher eventPublisher) {
        return new UpdateEmailContactUseCase(repository, contactRepository, eventPublisher);
    }

    @Bean
    public DeleteEmailContactUseCase deleteEmailContactUseCase(EmailContactRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteEmailContactUseCase(repository, eventPublisher);
    }
}
