package springboot.infrastructure.clinicalnote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.clinicalnote.usecase.DeleteClinicalNoteUseCase;
import springboot.application.clinicalnote.usecase.GetClinicalNoteByIdUseCase;
import springboot.application.clinicalnote.usecase.ListClinicalNoteUseCase;
import springboot.application.clinicalnote.usecase.RegisterClinicalNoteUseCase;
import springboot.application.clinicalnote.usecase.UpdateClinicalNoteUseCase;
import springboot.domain.clinicalnote.port.repository.ClinicalNoteRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounter.port.repository.EncounterRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.infrastructure.clinicalnote.adapters.out.persistence.mappers.ClinicalNotePersistenceMapper;
import springboot.infrastructure.clinicalnote.adapters.out.persistence.repositories.ClinicalNoteJpaRepository;
import springboot.infrastructure.clinicalnote.adapters.out.persistence.repositories.ClinicalNoteRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ClinicalNote: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ClinicalNoteBeansConfig {

    @Bean
    public ClinicalNotePersistenceMapper clinicalNotePersistenceMapper() {
        return new ClinicalNotePersistenceMapper();
    }

    @Bean
    public ClinicalNoteRepository clinicalNoteRepository(ClinicalNoteJpaRepository jpaRepository, ClinicalNotePersistenceMapper mapper) {
        return new ClinicalNoteRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterClinicalNoteUseCase registerClinicalNoteUseCase(
            ClinicalNoteRepository repository, EncounterRepository encounterRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new RegisterClinicalNoteUseCase(repository, encounterRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public GetClinicalNoteByIdUseCase getClinicalNoteByIdUseCase(ClinicalNoteRepository repository) {
        return new GetClinicalNoteByIdUseCase(repository);
    }

    @Bean
    public ListClinicalNoteUseCase listClinicalNoteUseCase(ClinicalNoteRepository repository) {
        return new ListClinicalNoteUseCase(repository);
    }

    @Bean
    public UpdateClinicalNoteUseCase updateClinicalNoteUseCase(
            ClinicalNoteRepository repository, EncounterRepository encounterRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new UpdateClinicalNoteUseCase(repository, encounterRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public DeleteClinicalNoteUseCase deleteClinicalNoteUseCase(ClinicalNoteRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteClinicalNoteUseCase(repository, eventPublisher);
    }
}
