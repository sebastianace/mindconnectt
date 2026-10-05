package springboot.application.country.usecase;

import springboot.application.country.command.UpdateCountryCommand;
import springboot.application.country.dto.CountryResponse;
import springboot.application.country.exception.CountryNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.country.model.aggregate.Country;
import springboot.domain.country.port.repository.CountryRepository;

public class UpdateCountryUseCase {
    private final CountryRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateCountryUseCase(
            CountryRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public CountryResponse execute(UpdateCountryCommand command) {
        Country aggregate = repository.findById(command.id())
                .orElseThrow(() -> new CountryNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.nameCountry(),
                command.codeCountry(),
                command.description(),
                command.active(),
                command.telephonePrefix());
        Country saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return CountryResponse.from(saved);
    }
}
