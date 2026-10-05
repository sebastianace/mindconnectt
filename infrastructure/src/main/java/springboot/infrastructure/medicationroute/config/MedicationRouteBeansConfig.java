package springboot.infrastructure.medicationroute.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.medicationroute.usecase.DeleteMedicationRouteUseCase;
import springboot.application.medicationroute.usecase.GetMedicationRouteByIdUseCase;
import springboot.application.medicationroute.usecase.ListMedicationRouteUseCase;
import springboot.application.medicationroute.usecase.RegisterMedicationRouteUseCase;
import springboot.application.medicationroute.usecase.UpdateMedicationRouteUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.medicationroute.port.repository.MedicationRouteRepository;
import springboot.infrastructure.medicationroute.adapters.out.persistence.mappers.MedicationRoutePersistenceMapper;
import springboot.infrastructure.medicationroute.adapters.out.persistence.repositories.MedicationRouteJpaRepository;
import springboot.infrastructure.medicationroute.adapters.out.persistence.repositories.MedicationRouteRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context MedicationRoute: adaptador de persistencia y casos de uso.
 */
@Configuration
public class MedicationRouteBeansConfig {

    @Bean
    public MedicationRoutePersistenceMapper medicationRoutePersistenceMapper() {
        return new MedicationRoutePersistenceMapper();
    }

    @Bean
    public MedicationRouteRepository medicationRouteRepository(MedicationRouteJpaRepository jpaRepository, MedicationRoutePersistenceMapper mapper) {
        return new MedicationRouteRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterMedicationRouteUseCase registerMedicationRouteUseCase(
            MedicationRouteRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterMedicationRouteUseCase(repository, eventPublisher);
    }

    @Bean
    public GetMedicationRouteByIdUseCase getMedicationRouteByIdUseCase(MedicationRouteRepository repository) {
        return new GetMedicationRouteByIdUseCase(repository);
    }

    @Bean
    public ListMedicationRouteUseCase listMedicationRouteUseCase(MedicationRouteRepository repository) {
        return new ListMedicationRouteUseCase(repository);
    }

    @Bean
    public UpdateMedicationRouteUseCase updateMedicationRouteUseCase(
            MedicationRouteRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateMedicationRouteUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteMedicationRouteUseCase deleteMedicationRouteUseCase(MedicationRouteRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteMedicationRouteUseCase(repository, eventPublisher);
    }
}
