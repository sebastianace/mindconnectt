package springboot.application.treatmentgoal.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.treatmentgoal.command.UpdateTreatmentGoalCommand;
import springboot.application.treatmentgoal.dto.TreatmentGoalResponse;
import springboot.application.treatmentgoal.exception.TreatmentGoalNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.treatmentgoal.model.aggregate.TreatmentGoal;
import springboot.domain.treatmentgoal.port.repository.TreatmentGoalRepository;
import springboot.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import springboot.domain.treatmentplan.port.repository.TreatmentPlanRepository;

public class UpdateTreatmentGoalUseCase {
    private final TreatmentGoalRepository repository;
    private final TreatmentPlanRepository treatmentPlanRepository;
    private final TreatmentGoalStatusRepository treatmentGoalStatusRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateTreatmentGoalUseCase(
            TreatmentGoalRepository repository,
            TreatmentPlanRepository treatmentPlanRepository,
            TreatmentGoalStatusRepository treatmentGoalStatusRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.treatmentGoalStatusRepository = treatmentGoalStatusRepository;
        this.eventPublisher = eventPublisher;
    }

    public TreatmentGoalResponse execute(UpdateTreatmentGoalCommand command) {
        TreatmentGoal aggregate = repository.findById(command.id())
                .orElseThrow(() -> new TreatmentGoalNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.treatmentPlanId(),
                command.description(),
                command.targetDate(),
                command.completedAt(),
                command.notes(),
                command.treatmentGoalStatusId());
        TreatmentGoal saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return TreatmentGoalResponse.from(saved);
    }

    private void validateReferences(UpdateTreatmentGoalCommand command) {
        if (!treatmentPlanRepository.existsById(command.treatmentPlanId())) {
            throw new ReferenceNotFoundApplicationException("TreatmentPlan", command.treatmentPlanId().value());
        }
        if (!treatmentGoalStatusRepository.existsById(command.treatmentGoalStatusId())) {
            throw new ReferenceNotFoundApplicationException("TreatmentGoalStatus", command.treatmentGoalStatusId().value());
        }
    }
}
