package springboot.application.treatmentgoalstatus.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.treatmentgoalstatus.event.TreatmentGoalStatusDeletedEvent;
import springboot.domain.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import springboot.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import springboot.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;

public class DeleteTreatmentGoalStatusUseCase {
    private final TreatmentGoalStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public TreatmentGoalStatusDeletedEvent execute(TreatmentGoalStatusId id) {
        TreatmentGoalStatus aggregate = repository.findById(id)
                .orElseThrow(() -> new TreatmentGoalStatusNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        TreatmentGoalStatusDeletedEvent event = new TreatmentGoalStatusDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
