package springboot.infrastructure.contact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.contact.usecase.DeleteContactUseCase;
import springboot.application.contact.usecase.GetContactByIdUseCase;
import springboot.application.contact.usecase.ListContactUseCase;
import springboot.application.contact.usecase.RegisterContactUseCase;
import springboot.application.contact.usecase.UpdateContactUseCase;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.contact.port.repository.ContactRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.infrastructure.contact.adapters.out.persistence.mappers.ContactPersistenceMapper;
import springboot.infrastructure.contact.adapters.out.persistence.repositories.ContactJpaRepository;
import springboot.infrastructure.contact.adapters.out.persistence.repositories.ContactRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context Contact: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ContactBeansConfig {

    @Bean
    public ContactPersistenceMapper contactPersistenceMapper() {
        return new ContactPersistenceMapper();
    }

    @Bean
    public ContactRepository contactRepository(ContactJpaRepository jpaRepository, ContactPersistenceMapper mapper) {
        return new ContactRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterContactUseCase registerContactUseCase(
            ContactRepository repository, CityMunicipalityRepository cityMunicipalityRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new RegisterContactUseCase(repository, cityMunicipalityRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public GetContactByIdUseCase getContactByIdUseCase(ContactRepository repository) {
        return new GetContactByIdUseCase(repository);
    }

    @Bean
    public ListContactUseCase listContactUseCase(ContactRepository repository) {
        return new ListContactUseCase(repository);
    }

    @Bean
    public UpdateContactUseCase updateContactUseCase(
            ContactRepository repository, CityMunicipalityRepository cityMunicipalityRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new UpdateContactUseCase(repository, cityMunicipalityRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public DeleteContactUseCase deleteContactUseCase(ContactRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteContactUseCase(repository, eventPublisher);
    }
}
