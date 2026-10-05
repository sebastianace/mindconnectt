package springboot.application.consenttype.usecase;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.consenttype.command.RegisterConsentTypeCommand;
import springboot.application.consenttype.dto.ConsentTypeResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.consenttype.model.aggregate.ConsentType;
import springboot.domain.consenttype.port.repository.ConsentTypeRepository;

public class RegisterConsentTypeUseCase {
    private final ConsentTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterConsentTypeUseCase(
            ConsentTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ConsentTypeResponse execute(RegisterConsentTypeCommand command) {
        if (repository.existsByCode(command.code())) {
            throw new DuplicateResourceApplicationException("ConsentType", "code", command.code());
        }
        ConsentType aggregate = ConsentType.register(
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
