package springboot.infrastructure.patient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.patient.usecase.DeletePatientUseCase;
import springboot.application.patient.usecase.GetPatientByIdUseCase;
import springboot.application.patient.usecase.ListPatientUseCase;
import springboot.application.patient.usecase.RegisterPatientUseCase;
import springboot.application.patient.usecase.UpdatePatientUseCase;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.documenttype.port.repository.DocumentTypeRepository;
import springboot.domain.gender.port.repository.GenderRepository;
import springboot.domain.patient.port.repository.PatientRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.infrastructure.patient.adapters.out.persistence.mappers.PatientPersistenceMapper;
import springboot.infrastructure.patient.adapters.out.persistence.repositories.PatientJpaRepository;
import springboot.infrastructure.patient.adapters.out.persistence.repositories.PatientRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context Patient: adaptador de persistencia y casos de uso.
 */
@Configuration
public class PatientBeansConfig {

    @Bean
    public PatientPersistenceMapper patientPersistenceMapper() {
        return new PatientPersistenceMapper();
    }

    @Bean
    public PatientRepository patientRepository(PatientJpaRepository jpaRepository, PatientPersistenceMapper mapper) {
        return new PatientRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterPatientUseCase registerPatientUseCase(
            PatientRepository repository, DocumentTypeRepository documentTypeRepository, GenderRepository genderRepository, ProfessionalRepository professionalRepository, CityMunicipalityRepository cityMunicipalityRepository, DomainEventPublisher eventPublisher) {
        return new RegisterPatientUseCase(repository, documentTypeRepository, genderRepository, professionalRepository, cityMunicipalityRepository, eventPublisher);
    }

    @Bean
    public GetPatientByIdUseCase getPatientByIdUseCase(PatientRepository repository) {
        return new GetPatientByIdUseCase(repository);
    }

    @Bean
    public ListPatientUseCase listPatientUseCase(PatientRepository repository) {
        return new ListPatientUseCase(repository);
    }

    @Bean
    public UpdatePatientUseCase updatePatientUseCase(
            PatientRepository repository, DocumentTypeRepository documentTypeRepository, GenderRepository genderRepository, ProfessionalRepository professionalRepository, CityMunicipalityRepository cityMunicipalityRepository, DomainEventPublisher eventPublisher) {
        return new UpdatePatientUseCase(repository, documentTypeRepository, genderRepository, professionalRepository, cityMunicipalityRepository, eventPublisher);
    }

    @Bean
    public DeletePatientUseCase deletePatientUseCase(PatientRepository repository, DomainEventPublisher eventPublisher) {
        return new DeletePatientUseCase(repository, eventPublisher);
    }
}
