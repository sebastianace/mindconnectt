package springboot.application.professionaltype.usecase;

import springboot.application.professionaltype.command.RegisterProfessionalTypeCommand;
import springboot.application.professionaltype.dto.ProfessionalTypeResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.professionaltype.model.aggregate.ProfessionalType;
import springboot.domain.professionaltype.port.repository.ProfessionalTypeRepository;

public class RegisterProfessionalTypeUseCase {
    private final ProfessionalTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterProfessionalTypeUseCase(
            ProfessionalTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ProfessionalTypeResponse execute(RegisterProfessionalTypeCommand command) {
        ProfessionalType aggregate = ProfessionalType.register(
                command.name());
        ProfessionalType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ProfessionalTypeResponse.from(saved);
    }
}
