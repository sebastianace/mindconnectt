package springboot.infrastructure.clinicalrecord.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.clinicalrecord.usecase.DeleteClinicalRecordUseCase;
import springboot.application.clinicalrecord.usecase.GetClinicalRecordByIdUseCase;
import springboot.application.clinicalrecord.usecase.ListClinicalRecordUseCase;
import springboot.application.clinicalrecord.usecase.RegisterClinicalRecordUseCase;
import springboot.application.clinicalrecord.usecase.UpdateClinicalRecordUseCase;
import springboot.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import springboot.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.patient.port.repository.PatientRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.infrastructure.clinicalrecord.adapters.out.persistence.mappers.ClinicalRecordPersistenceMapper;
import springboot.infrastructure.clinicalrecord.adapters.out.persistence.repositories.ClinicalRecordJpaRepository;
import springboot.infrastructure.clinicalrecord.adapters.out.persistence.repositories.ClinicalRecordRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ClinicalRecord: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ClinicalRecordBeansConfig {

    @Bean
    public ClinicalRecordPersistenceMapper clinicalRecordPersistenceMapper() {
        return new ClinicalRecordPersistenceMapper();
    }

    @Bean
    public ClinicalRecordRepository clinicalRecordRepository(ClinicalRecordJpaRepository jpaRepository, ClinicalRecordPersistenceMapper mapper) {
        return new ClinicalRecordRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterClinicalRecordUseCase registerClinicalRecordUseCase(
            ClinicalRecordRepository repository, PatientRepository patientRepository, ClinicalRecordStatusRepository clinicalRecordStatusRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new RegisterClinicalRecordUseCase(repository, patientRepository, clinicalRecordStatusRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public GetClinicalRecordByIdUseCase getClinicalRecordByIdUseCase(ClinicalRecordRepository repository) {
        return new GetClinicalRecordByIdUseCase(repository);
    }

    @Bean
    public ListClinicalRecordUseCase listClinicalRecordUseCase(ClinicalRecordRepository repository) {
        return new ListClinicalRecordUseCase(repository);
    }

    @Bean
    public UpdateClinicalRecordUseCase updateClinicalRecordUseCase(
            ClinicalRecordRepository repository, PatientRepository patientRepository, ClinicalRecordStatusRepository clinicalRecordStatusRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new UpdateClinicalRecordUseCase(repository, patientRepository, clinicalRecordStatusRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public DeleteClinicalRecordUseCase deleteClinicalRecordUseCase(ClinicalRecordRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteClinicalRecordUseCase(repository, eventPublisher);
    }
}
