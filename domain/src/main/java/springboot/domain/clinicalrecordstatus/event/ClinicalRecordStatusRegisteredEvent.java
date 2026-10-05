package springboot.domain.clinicalrecordstatus.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import springboot.domain.common.event.DomainEvent;

public record ClinicalRecordStatusRegisteredEvent(
        ClinicalRecordStatusId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public ClinicalRecordStatusRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}
