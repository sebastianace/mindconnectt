package springboot.application.treatmentgoalstatus.usecase;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.treatmentgoalstatus.command.RegisterTreatmentGoalStatusCommand;
import springboot.application.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import springboot.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;

public class RegisterTreatmentGoalStatusUseCase {
    private final TreatmentGoalStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterTreatmentGoalStatusUseCase(
            TreatmentGoalStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public TreatmentGoalStatusResponse execute(RegisterTreatmentGoalStatusCommand command) {
        if (repository.existsByCode(command.code())) {
            throw new DuplicateResourceApplicationException("TreatmentGoalStatus", "code", command.code());
        }
        TreatmentGoalStatus aggregate = TreatmentGoalStatus.register(
                command.code(),
                command.name(),
                command.active());
        TreatmentGoalStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return TreatmentGoalStatusResponse.from(saved);
    }
}
