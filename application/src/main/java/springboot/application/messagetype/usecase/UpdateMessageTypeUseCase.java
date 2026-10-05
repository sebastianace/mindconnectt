package springboot.application.messagetype.usecase;

import springboot.application.messagetype.command.UpdateMessageTypeCommand;
import springboot.application.messagetype.dto.MessageTypeResponse;
import springboot.application.messagetype.exception.MessageTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.messagetype.model.aggregate.MessageType;
import springboot.domain.messagetype.port.repository.MessageTypeRepository;

public class UpdateMessageTypeUseCase {
    private final MessageTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateMessageTypeUseCase(
            MessageTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public MessageTypeResponse execute(UpdateMessageTypeCommand command) {
        MessageType aggregate = repository.findById(command.id())
                .orElseThrow(() -> new MessageTypeNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.nameType());
        MessageType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return MessageTypeResponse.from(saved);
    }
}
