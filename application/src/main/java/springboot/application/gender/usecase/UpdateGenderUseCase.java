package springboot.application.gender.usecase;

import springboot.application.gender.command.UpdateGenderCommand;
import springboot.application.gender.dto.GenderResponse;
import springboot.application.gender.exception.GenderNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.gender.model.aggregate.Gender;
import springboot.domain.gender.port.repository.GenderRepository;

public class UpdateGenderUseCase {
    private final GenderRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateGenderUseCase(
            GenderRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public GenderResponse execute(UpdateGenderCommand command) {
        Gender aggregate = repository.findById(command.id())
                .orElseThrow(() -> new GenderNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.description());
        Gender saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return GenderResponse.from(saved);
    }
}
