package springboot.application.treatmentstatus.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.treatmentstatus.event.TreatmentStatusDeletedEvent;
import springboot.domain.treatmentstatus.model.aggregate.TreatmentStatus;
import springboot.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import springboot.domain.treatmentstatus.port.repository.TreatmentStatusRepository;

public class DeleteTreatmentStatusUseCase {
    private final TreatmentStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteTreatmentStatusUseCase(TreatmentStatusRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public TreatmentStatusDeletedEvent execute(TreatmentStatusId id) {
        TreatmentStatus aggregate = repository.findById(id)
                .orElseThrow(() -> new TreatmentStatusNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        TreatmentStatusDeletedEvent event = new TreatmentStatusDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
