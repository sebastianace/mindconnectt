package springboot.infrastructure.encounter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.encounter.usecase.DeleteEncounterUseCase;
import springboot.application.encounter.usecase.GetEncounterByIdUseCase;
import springboot.application.encounter.usecase.ListEncounterUseCase;
import springboot.application.encounter.usecase.RegisterEncounterUseCase;
import springboot.application.encounter.usecase.UpdateEncounterUseCase;
import springboot.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounter.port.repository.EncounterRepository;
import springboot.domain.encountermodality.port.repository.EncounterModalityRepository;
import springboot.domain.encounterstatus.port.repository.EncounterStatusRepository;
import springboot.domain.encountertype.port.repository.EncounterTypeRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.infrastructure.encounter.adapters.out.persistence.mappers.EncounterPersistenceMapper;
import springboot.infrastructure.encounter.adapters.out.persistence.repositories.EncounterJpaRepository;
import springboot.infrastructure.encounter.adapters.out.persistence.repositories.EncounterRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context Encounter: adaptador de persistencia y casos de uso.
 */
@Configuration
public class EncounterBeansConfig {

    @Bean
    public EncounterPersistenceMapper encounterPersistenceMapper() {
        return new EncounterPersistenceMapper();
    }

    @Bean
    public EncounterRepository encounterRepository(EncounterJpaRepository jpaRepository, EncounterPersistenceMapper mapper) {
        return new EncounterRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterEncounterUseCase registerEncounterUseCase(
            EncounterRepository repository, ClinicalRecordRepository clinicalRecordRepository, ProfessionalRepository professionalRepository, EncounterTypeRepository encounterTypeRepository, EncounterModalityRepository encounterModalityRepository, EncounterStatusRepository encounterStatusRepository, DomainEventPublisher eventPublisher) {
        return new RegisterEncounterUseCase(repository, clinicalRecordRepository, professionalRepository, encounterTypeRepository, encounterModalityRepository, encounterStatusRepository, eventPublisher);
    }

    @Bean
    public GetEncounterByIdUseCase getEncounterByIdUseCase(EncounterRepository repository) {
        return new GetEncounterByIdUseCase(repository);
    }

    @Bean
    public ListEncounterUseCase listEncounterUseCase(EncounterRepository repository) {
        return new ListEncounterUseCase(repository);
    }

    @Bean
    public UpdateEncounterUseCase updateEncounterUseCase(
            EncounterRepository repository, ClinicalRecordRepository clinicalRecordRepository, ProfessionalRepository professionalRepository, EncounterTypeRepository encounterTypeRepository, EncounterModalityRepository encounterModalityRepository, EncounterStatusRepository encounterStatusRepository, DomainEventPublisher eventPublisher) {
        return new UpdateEncounterUseCase(repository, clinicalRecordRepository, professionalRepository, encounterTypeRepository, encounterModalityRepository, encounterStatusRepository, eventPublisher);
    }

    @Bean
    public DeleteEncounterUseCase deleteEncounterUseCase(EncounterRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteEncounterUseCase(repository, eventPublisher);
    }
}
