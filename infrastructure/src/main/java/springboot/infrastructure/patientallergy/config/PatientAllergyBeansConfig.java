package springboot.infrastructure.patientallergy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.patientallergy.usecase.DeletePatientAllergyUseCase;
import springboot.application.patientallergy.usecase.GetPatientAllergyByIdUseCase;
import springboot.application.patientallergy.usecase.ListPatientAllergyUseCase;
import springboot.application.patientallergy.usecase.RegisterPatientAllergyUseCase;
import springboot.application.patientallergy.usecase.UpdatePatientAllergyUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.patient.port.repository.PatientRepository;
import springboot.domain.patientallergy.port.repository.PatientAllergyRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.infrastructure.patientallergy.adapters.out.persistence.mappers.PatientAllergyPersistenceMapper;
import springboot.infrastructure.patientallergy.adapters.out.persistence.repositories.PatientAllergyJpaRepository;
import springboot.infrastructure.patientallergy.adapters.out.persistence.repositories.PatientAllergyRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context PatientAllergy: adaptador de persistencia y casos de uso.
 */
@Configuration
public class PatientAllergyBeansConfig {

    @Bean
    public PatientAllergyPersistenceMapper patientAllergyPersistenceMapper() {
        return new PatientAllergyPersistenceMapper();
    }

    @Bean
    public PatientAllergyRepository patientAllergyRepository(PatientAllergyJpaRepository jpaRepository, PatientAllergyPersistenceMapper mapper) {
        return new PatientAllergyRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterPatientAllergyUseCase registerPatientAllergyUseCase(
            PatientAllergyRepository repository, PatientRepository patientRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new RegisterPatientAllergyUseCase(repository, patientRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public GetPatientAllergyByIdUseCase getPatientAllergyByIdUseCase(PatientAllergyRepository repository) {
        return new GetPatientAllergyByIdUseCase(repository);
    }

    @Bean
    public ListPatientAllergyUseCase listPatientAllergyUseCase(PatientAllergyRepository repository) {
        return new ListPatientAllergyUseCase(repository);
    }

    @Bean
    public UpdatePatientAllergyUseCase updatePatientAllergyUseCase(
            PatientAllergyRepository repository, PatientRepository patientRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new UpdatePatientAllergyUseCase(repository, patientRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public DeletePatientAllergyUseCase deletePatientAllergyUseCase(PatientAllergyRepository repository, DomainEventPublisher eventPublisher) {
        return new DeletePatientAllergyUseCase(repository, eventPublisher);
    }
}
