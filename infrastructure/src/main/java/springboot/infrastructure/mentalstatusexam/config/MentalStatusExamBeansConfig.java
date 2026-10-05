package springboot.infrastructure.mentalstatusexam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.mentalstatusexam.usecase.DeleteMentalStatusExamUseCase;
import springboot.application.mentalstatusexam.usecase.GetMentalStatusExamByIdUseCase;
import springboot.application.mentalstatusexam.usecase.ListMentalStatusExamUseCase;
import springboot.application.mentalstatusexam.usecase.RegisterMentalStatusExamUseCase;
import springboot.application.mentalstatusexam.usecase.UpdateMentalStatusExamUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounter.port.repository.EncounterRepository;
import springboot.domain.mentalstatusexam.port.repository.MentalStatusExamRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.infrastructure.mentalstatusexam.adapters.out.persistence.mappers.MentalStatusExamPersistenceMapper;
import springboot.infrastructure.mentalstatusexam.adapters.out.persistence.repositories.MentalStatusExamJpaRepository;
import springboot.infrastructure.mentalstatusexam.adapters.out.persistence.repositories.MentalStatusExamRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context MentalStatusExam: adaptador de persistencia y casos de uso.
 */
@Configuration
public class MentalStatusExamBeansConfig {

    @Bean
    public MentalStatusExamPersistenceMapper mentalStatusExamPersistenceMapper() {
        return new MentalStatusExamPersistenceMapper();
    }

    @Bean
    public MentalStatusExamRepository mentalStatusExamRepository(MentalStatusExamJpaRepository jpaRepository, MentalStatusExamPersistenceMapper mapper) {
        return new MentalStatusExamRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterMentalStatusExamUseCase registerMentalStatusExamUseCase(
            MentalStatusExamRepository repository, EncounterRepository encounterRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new RegisterMentalStatusExamUseCase(repository, encounterRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public GetMentalStatusExamByIdUseCase getMentalStatusExamByIdUseCase(MentalStatusExamRepository repository) {
        return new GetMentalStatusExamByIdUseCase(repository);
    }

    @Bean
    public ListMentalStatusExamUseCase listMentalStatusExamUseCase(MentalStatusExamRepository repository) {
        return new ListMentalStatusExamUseCase(repository);
    }

    @Bean
    public UpdateMentalStatusExamUseCase updateMentalStatusExamUseCase(
            MentalStatusExamRepository repository, EncounterRepository encounterRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new UpdateMentalStatusExamUseCase(repository, encounterRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public DeleteMentalStatusExamUseCase deleteMentalStatusExamUseCase(MentalStatusExamRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteMentalStatusExamUseCase(repository, eventPublisher);
    }
}
