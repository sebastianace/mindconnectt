package springboot.application.assessmenttype.usecase;

import springboot.application.assessmenttype.command.UpdateAssessmentTypeCommand;
import springboot.application.assessmenttype.dto.AssessmentTypeResponse;
import springboot.application.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import springboot.domain.assessmenttype.model.aggregate.AssessmentType;
import springboot.domain.assessmenttype.port.repository.AssessmentTypeRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class UpdateAssessmentTypeUseCase {
    private final AssessmentTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateAssessmentTypeUseCase(
            AssessmentTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public AssessmentTypeResponse execute(UpdateAssessmentTypeCommand command) {
        AssessmentType aggregate = repository.findById(command.id())
                .orElseThrow(() -> new AssessmentTypeNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.code(),
                command.name(),
                command.active(),
                command.description());
        AssessmentType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return AssessmentTypeResponse.from(saved);
    }
}
