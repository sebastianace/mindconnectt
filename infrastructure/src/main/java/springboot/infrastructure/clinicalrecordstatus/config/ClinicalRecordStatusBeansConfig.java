package springboot.infrastructure.clinicalrecordstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.clinicalrecordstatus.usecase.DeleteClinicalRecordStatusUseCase;
import springboot.application.clinicalrecordstatus.usecase.GetClinicalRecordStatusByIdUseCase;
import springboot.application.clinicalrecordstatus.usecase.ListClinicalRecordStatusUseCase;
import springboot.application.clinicalrecordstatus.usecase.RegisterClinicalRecordStatusUseCase;
import springboot.application.clinicalrecordstatus.usecase.UpdateClinicalRecordStatusUseCase;
import springboot.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.infrastructure.clinicalrecordstatus.adapters.out.persistence.mappers.ClinicalRecordStatusPersistenceMapper;
import springboot.infrastructure.clinicalrecordstatus.adapters.out.persistence.repositories.ClinicalRecordStatusJpaRepository;
import springboot.infrastructure.clinicalrecordstatus.adapters.out.persistence.repositories.ClinicalRecordStatusRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ClinicalRecordStatus: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ClinicalRecordStatusBeansConfig {

    @Bean
    public ClinicalRecordStatusPersistenceMapper clinicalRecordStatusPersistenceMapper() {
        return new ClinicalRecordStatusPersistenceMapper();
    }

    @Bean
    public ClinicalRecordStatusRepository clinicalRecordStatusRepository(ClinicalRecordStatusJpaRepository jpaRepository, ClinicalRecordStatusPersistenceMapper mapper) {
        return new ClinicalRecordStatusRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterClinicalRecordStatusUseCase registerClinicalRecordStatusUseCase(
            ClinicalRecordStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterClinicalRecordStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public GetClinicalRecordStatusByIdUseCase getClinicalRecordStatusByIdUseCase(ClinicalRecordStatusRepository repository) {
        return new GetClinicalRecordStatusByIdUseCase(repository);
    }

    @Bean
    public ListClinicalRecordStatusUseCase listClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        return new ListClinicalRecordStatusUseCase(repository);
    }

    @Bean
    public UpdateClinicalRecordStatusUseCase updateClinicalRecordStatusUseCase(
            ClinicalRecordStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateClinicalRecordStatusUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteClinicalRecordStatusUseCase deleteClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteClinicalRecordStatusUseCase(repository, eventPublisher);
    }
}
