package springboot.application.professionaltype.usecase;

import springboot.application.professionaltype.command.UpdateProfessionalTypeCommand;
import springboot.application.professionaltype.dto.ProfessionalTypeResponse;
import springboot.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.professionaltype.model.aggregate.ProfessionalType;
import springboot.domain.professionaltype.port.repository.ProfessionalTypeRepository;

public class UpdateProfessionalTypeUseCase {
    private final ProfessionalTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateProfessionalTypeUseCase(
            ProfessionalTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ProfessionalTypeResponse execute(UpdateProfessionalTypeCommand command) {
        ProfessionalType aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ProfessionalTypeNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.name());
        ProfessionalType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ProfessionalTypeResponse.from(saved);
    }
}
