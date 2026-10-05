package springboot.application.country.usecase;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.country.command.RegisterCountryCommand;
import springboot.application.country.dto.CountryResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.country.model.aggregate.Country;
import springboot.domain.country.port.repository.CountryRepository;

public class RegisterCountryUseCase {
    private final CountryRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterCountryUseCase(
            CountryRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public CountryResponse execute(RegisterCountryCommand command) {
        if (repository.existsByCode(command.codeCountry())) {
            throw new DuplicateResourceApplicationException("Country", "codeCountry", command.codeCountry());
        }
        Country aggregate = Country.register(
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
