package springboot.application.sendertype.usecase;

import springboot.application.sendertype.command.UpdateSenderTypeCommand;
import springboot.application.sendertype.dto.SenderTypeResponse;
import springboot.application.sendertype.exception.SenderTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.sendertype.model.aggregate.SenderType;
import springboot.domain.sendertype.port.repository.SenderTypeRepository;

public class UpdateSenderTypeUseCase {
    private final SenderTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateSenderTypeUseCase(
            SenderTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public SenderTypeResponse execute(UpdateSenderTypeCommand command) {
        SenderType aggregate = repository.findById(command.id())
                .orElseThrow(() -> new SenderTypeNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.nameType());
        SenderType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return SenderTypeResponse.from(saved);
    }
}
