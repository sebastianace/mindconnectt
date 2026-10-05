package springboot.domain.clinicalnote.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.clinicalnote.model.valueobject.ClinicalNoteId;
import springboot.domain.common.event.DomainEvent;

public record ClinicalNoteRegisteredEvent(
        ClinicalNoteId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public ClinicalNoteRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}
