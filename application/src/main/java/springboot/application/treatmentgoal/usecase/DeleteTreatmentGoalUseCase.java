package springboot.application.treatmentgoal.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.treatmentgoal.exception.TreatmentGoalNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.treatmentgoal.event.TreatmentGoalDeletedEvent;
import springboot.domain.treatmentgoal.model.aggregate.TreatmentGoal;
import springboot.domain.treatmentgoal.model.valueobject.TreatmentGoalId;
import springboot.domain.treatmentgoal.port.repository.TreatmentGoalRepository;

public class DeleteTreatmentGoalUseCase {
    private final TreatmentGoalRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteTreatmentGoalUseCase(TreatmentGoalRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public TreatmentGoalDeletedEvent execute(TreatmentGoalId id) {
        TreatmentGoal aggregate = repository.findById(id)
                .orElseThrow(() -> new TreatmentGoalNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        TreatmentGoalDeletedEvent event = new TreatmentGoalDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
