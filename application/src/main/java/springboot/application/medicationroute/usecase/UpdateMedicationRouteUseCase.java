package springboot.application.medicationroute.usecase;

import springboot.application.medicationroute.command.UpdateMedicationRouteCommand;
import springboot.application.medicationroute.dto.MedicationRouteResponse;
import springboot.application.medicationroute.exception.MedicationRouteNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.medicationroute.model.aggregate.MedicationRoute;
import springboot.domain.medicationroute.port.repository.MedicationRouteRepository;

public class UpdateMedicationRouteUseCase {
    private final MedicationRouteRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateMedicationRouteUseCase(
            MedicationRouteRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public MedicationRouteResponse execute(UpdateMedicationRouteCommand command) {
        MedicationRoute aggregate = repository.findById(command.id())
                .orElseThrow(() -> new MedicationRouteNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.code(),
                command.name(),
                command.active());
        MedicationRoute saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return MedicationRouteResponse.from(saved);
    }
}
