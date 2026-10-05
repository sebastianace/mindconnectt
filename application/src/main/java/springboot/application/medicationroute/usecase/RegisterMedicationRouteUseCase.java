package springboot.application.medicationroute.usecase;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.medicationroute.command.RegisterMedicationRouteCommand;
import springboot.application.medicationroute.dto.MedicationRouteResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.medicationroute.model.aggregate.MedicationRoute;
import springboot.domain.medicationroute.port.repository.MedicationRouteRepository;

public class RegisterMedicationRouteUseCase {
    private final MedicationRouteRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterMedicationRouteUseCase(
            MedicationRouteRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public MedicationRouteResponse execute(RegisterMedicationRouteCommand command) {
        if (repository.existsByCode(command.code())) {
            throw new DuplicateResourceApplicationException("MedicationRoute", "code", command.code());
        }
        MedicationRoute aggregate = MedicationRoute.register(
                command.code(),
                command.name(),
                command.active());
        MedicationRoute saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return MedicationRouteResponse.from(saved);
    }
}
