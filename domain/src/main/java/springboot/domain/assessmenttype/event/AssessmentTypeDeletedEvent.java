package springboot.domain.assessmenttype.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import springboot.domain.common.event.DomainEvent;

public record AssessmentTypeDeletedEvent(
        AssessmentTypeId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public AssessmentTypeDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}
