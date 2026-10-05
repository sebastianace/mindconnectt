package springboot.application.messagetype.usecase;

import springboot.application.messagetype.command.RegisterMessageTypeCommand;
import springboot.application.messagetype.dto.MessageTypeResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.messagetype.model.aggregate.MessageType;
import springboot.domain.messagetype.port.repository.MessageTypeRepository;

public class RegisterMessageTypeUseCase {
    private final MessageTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterMessageTypeUseCase(
            MessageTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public MessageTypeResponse execute(RegisterMessageTypeCommand command) {
        MessageType aggregate = MessageType.register(
                command.nameType());
        MessageType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return MessageTypeResponse.from(saved);
    }
}
