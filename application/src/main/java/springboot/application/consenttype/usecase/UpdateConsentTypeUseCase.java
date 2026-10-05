package springboot.application.consenttype.usecase;

import springboot.application.consenttype.command.UpdateConsentTypeCommand;
import springboot.application.consenttype.dto.ConsentTypeResponse;
import springboot.application.consenttype.exception.ConsentTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.consenttype.model.aggregate.ConsentType;
import springboot.domain.consenttype.port.repository.ConsentTypeRepository;

public class UpdateConsentTypeUseCase {
    private final ConsentTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateConsentTypeUseCase(
            ConsentTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ConsentTypeResponse execute(UpdateConsentTypeCommand command) {
        ConsentType aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ConsentTypeNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.code(),
                command.name(),
                command.active(),
                command.description());
        ConsentType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ConsentTypeResponse.from(saved);
    }
}
