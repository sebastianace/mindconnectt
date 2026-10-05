package springboot.application.gender.usecase;

import springboot.application.gender.command.RegisterGenderCommand;
import springboot.application.gender.dto.GenderResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.gender.model.aggregate.Gender;
import springboot.domain.gender.port.repository.GenderRepository;

public class RegisterGenderUseCase {
    private final GenderRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterGenderUseCase(
            GenderRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public GenderResponse execute(RegisterGenderCommand command) {
        Gender aggregate = Gender.register(
                command.description());
        Gender saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return GenderResponse.from(saved);
    }
}
