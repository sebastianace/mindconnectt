package springboot.infrastructure.patientcontact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.patientcontact.usecase.DeletePatientContactUseCase;
import springboot.application.patientcontact.usecase.GetPatientContactByIdUseCase;
import springboot.application.patientcontact.usecase.ListPatientContactUseCase;
import springboot.application.patientcontact.usecase.RegisterPatientContactUseCase;
import springboot.application.patientcontact.usecase.UpdatePatientContactUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.contact.port.repository.ContactRepository;
import springboot.domain.patient.port.repository.PatientRepository;
import springboot.domain.patientcontact.port.repository.PatientContactRepository;
import springboot.domain.relationshiptype.port.repository.RelationshipTypeRepository;
import springboot.infrastructure.patientcontact.adapters.out.persistence.mappers.PatientContactPersistenceMapper;
import springboot.infrastructure.patientcontact.adapters.out.persistence.repositories.PatientContactJpaRepository;
import springboot.infrastructure.patientcontact.adapters.out.persistence.repositories.PatientContactRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context PatientContact: adaptador de persistencia y casos de uso.
 */
@Configuration
public class PatientContactBeansConfig {

    @Bean
    public PatientContactPersistenceMapper patientContactPersistenceMapper() {
        return new PatientContactPersistenceMapper();
    }

    @Bean
    public PatientContactRepository patientContactRepository(PatientContactJpaRepository jpaRepository, PatientContactPersistenceMapper mapper) {
        return new PatientContactRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterPatientContactUseCase registerPatientContactUseCase(
            PatientContactRepository repository, ContactRepository contactRepository, PatientRepository patientRepository, RelationshipTypeRepository relationshipTypeRepository, DomainEventPublisher eventPublisher) {
        return new RegisterPatientContactUseCase(repository, contactRepository, patientRepository, relationshipTypeRepository, eventPublisher);
    }

    @Bean
    public GetPatientContactByIdUseCase getPatientContactByIdUseCase(PatientContactRepository repository) {
        return new GetPatientContactByIdUseCase(repository);
    }

    @Bean
    public ListPatientContactUseCase listPatientContactUseCase(PatientContactRepository repository) {
        return new ListPatientContactUseCase(repository);
    }

    @Bean
    public UpdatePatientContactUseCase updatePatientContactUseCase(
            PatientContactRepository repository, ContactRepository contactRepository, PatientRepository patientRepository, RelationshipTypeRepository relationshipTypeRepository, DomainEventPublisher eventPublisher) {
        return new UpdatePatientContactUseCase(repository, contactRepository, patientRepository, relationshipTypeRepository, eventPublisher);
    }

    @Bean
    public DeletePatientContactUseCase deletePatientContactUseCase(PatientContactRepository repository, DomainEventPublisher eventPublisher) {
        return new DeletePatientContactUseCase(repository, eventPublisher);
    }
}
