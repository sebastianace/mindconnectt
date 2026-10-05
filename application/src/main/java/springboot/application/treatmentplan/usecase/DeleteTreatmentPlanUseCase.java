package springboot.application.treatmentplan.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.treatmentplan.exception.TreatmentPlanNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.treatmentplan.event.TreatmentPlanDeletedEvent;
import springboot.domain.treatmentplan.model.aggregate.TreatmentPlan;
import springboot.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import springboot.domain.treatmentplan.port.repository.TreatmentPlanRepository;

public class DeleteTreatmentPlanUseCase {
    private final TreatmentPlanRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteTreatmentPlanUseCase(TreatmentPlanRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public TreatmentPlanDeletedEvent execute(TreatmentPlanId id) {
        TreatmentPlan aggregate = repository.findById(id)
                .orElseThrow(() -> new TreatmentPlanNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        TreatmentPlanDeletedEvent event = new TreatmentPlanDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
