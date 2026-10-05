package springboot.application.messagetype.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.messagetype.exception.MessageTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.messagetype.event.MessageTypeDeletedEvent;
import springboot.domain.messagetype.model.aggregate.MessageType;
import springboot.domain.messagetype.model.valueobject.MessageTypeId;
import springboot.domain.messagetype.port.repository.MessageTypeRepository;

public class DeleteMessageTypeUseCase {
    private final MessageTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteMessageTypeUseCase(MessageTypeRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public MessageTypeDeletedEvent execute(MessageTypeId id) {
        MessageType aggregate = repository.findById(id)
                .orElseThrow(() -> new MessageTypeNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        MessageTypeDeletedEvent event = new MessageTypeDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
