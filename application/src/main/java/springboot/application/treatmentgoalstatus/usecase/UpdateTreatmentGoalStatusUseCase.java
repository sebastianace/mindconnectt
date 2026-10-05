package springboot.application.treatmentgoalstatus.usecase;

import springboot.application.treatmentgoalstatus.command.UpdateTreatmentGoalStatusCommand;
import springboot.application.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import springboot.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import springboot.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;

public class UpdateTreatmentGoalStatusUseCase {
    private final TreatmentGoalStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateTreatmentGoalStatusUseCase(
            TreatmentGoalStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public TreatmentGoalStatusResponse execute(UpdateTreatmentGoalStatusCommand command) {
        TreatmentGoalStatus aggregate = repository.findById(command.id())
                .orElseThrow(() -> new TreatmentGoalStatusNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.code(),
                command.name(),
                command.active());
        TreatmentGoalStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return TreatmentGoalStatusResponse.from(saved);
    }
}
