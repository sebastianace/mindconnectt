package springboot.domain.citymunicipality.event;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import springboot.domain.common.event.DomainEvent;

public record CityMunicipalityDeletedEvent(
        CityMunicipalityId id,
        LocalDateTime occurredOn
) implements DomainEvent {
    public CityMunicipalityDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}
