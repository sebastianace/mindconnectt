package springboot.application.assessmenttype.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import springboot.domain.assessmenttype.event.AssessmentTypeDeletedEvent;
import springboot.domain.assessmenttype.model.aggregate.AssessmentType;
import springboot.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import springboot.domain.assessmenttype.port.repository.AssessmentTypeRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteAssessmentTypeUseCase {
    private final AssessmentTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteAssessmentTypeUseCase(AssessmentTypeRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public AssessmentTypeDeletedEvent execute(AssessmentTypeId id) {
        AssessmentType aggregate = repository.findById(id)
                .orElseThrow(() -> new AssessmentTypeNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        AssessmentTypeDeletedEvent event = new AssessmentTypeDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
