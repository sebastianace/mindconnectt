package springboot.domain.clinicalrecordstatus.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import springboot.domain.common.event.DomainEvent;

public record ClinicalRecordStatusUpdatedEvent(
        ClinicalRecordStatusId id,
        String code,
        String name,
        LocalDateTime occurredOn
) implements DomainEvent {
    public ClinicalRecordStatusUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}
