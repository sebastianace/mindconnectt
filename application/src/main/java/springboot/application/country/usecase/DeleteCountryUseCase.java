package springboot.application.country.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.country.exception.CountryNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.country.event.CountryDeletedEvent;
import springboot.domain.country.model.aggregate.Country;
import springboot.domain.country.model.valueobject.CountryId;
import springboot.domain.country.port.repository.CountryRepository;

public class DeleteCountryUseCase {
    private final CountryRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteCountryUseCase(CountryRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public CountryDeletedEvent execute(CountryId id) {
        Country aggregate = repository.findById(id)
                .orElseThrow(() -> new CountryNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        CountryDeletedEvent event = new CountryDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
