package springboot.application.medicationroute.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.medicationroute.exception.MedicationRouteNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.medicationroute.event.MedicationRouteDeletedEvent;
import springboot.domain.medicationroute.model.aggregate.MedicationRoute;
import springboot.domain.medicationroute.model.valueobject.MedicationRouteId;
import springboot.domain.medicationroute.port.repository.MedicationRouteRepository;

public class DeleteMedicationRouteUseCase {
    private final MedicationRouteRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteMedicationRouteUseCase(MedicationRouteRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public MedicationRouteDeletedEvent execute(MedicationRouteId id) {
        MedicationRoute aggregate = repository.findById(id)
                .orElseThrow(() -> new MedicationRouteNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        MedicationRouteDeletedEvent event = new MedicationRouteDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
