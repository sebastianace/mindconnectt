package springboot.infrastructure.phonecontact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.phonecontact.usecase.DeletePhoneContactUseCase;
import springboot.application.phonecontact.usecase.GetPhoneContactByIdUseCase;
import springboot.application.phonecontact.usecase.ListPhoneContactUseCase;
import springboot.application.phonecontact.usecase.RegisterPhoneContactUseCase;
import springboot.application.phonecontact.usecase.UpdatePhoneContactUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.contact.port.repository.ContactRepository;
import springboot.domain.phonecontact.port.repository.PhoneContactRepository;
import springboot.infrastructure.phonecontact.adapters.out.persistence.mappers.PhoneContactPersistenceMapper;
import springboot.infrastructure.phonecontact.adapters.out.persistence.repositories.PhoneContactJpaRepository;
import springboot.infrastructure.phonecontact.adapters.out.persistence.repositories.PhoneContactRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context PhoneContact: adaptador de persistencia y casos de uso.
 */
@Configuration
public class PhoneContactBeansConfig {

    @Bean
    public PhoneContactPersistenceMapper phoneContactPersistenceMapper() {
        return new PhoneContactPersistenceMapper();
    }

    @Bean
    public PhoneContactRepository phoneContactRepository(PhoneContactJpaRepository jpaRepository, PhoneContactPersistenceMapper mapper) {
        return new PhoneContactRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterPhoneContactUseCase registerPhoneContactUseCase(
            PhoneContactRepository repository, ContactRepository contactRepository, DomainEventPublisher eventPublisher) {
        return new RegisterPhoneContactUseCase(repository, contactRepository, eventPublisher);
    }

    @Bean
    public GetPhoneContactByIdUseCase getPhoneContactByIdUseCase(PhoneContactRepository repository) {
        return new GetPhoneContactByIdUseCase(repository);
    }

    @Bean
    public ListPhoneContactUseCase listPhoneContactUseCase(PhoneContactRepository repository) {
        return new ListPhoneContactUseCase(repository);
    }

    @Bean
    public UpdatePhoneContactUseCase updatePhoneContactUseCase(
            PhoneContactRepository repository, ContactRepository contactRepository, DomainEventPublisher eventPublisher) {
        return new UpdatePhoneContactUseCase(repository, contactRepository, eventPublisher);
    }

    @Bean
    public DeletePhoneContactUseCase deletePhoneContactUseCase(PhoneContactRepository repository, DomainEventPublisher eventPublisher) {
        return new DeletePhoneContactUseCase(repository, eventPublisher);
    }
}
