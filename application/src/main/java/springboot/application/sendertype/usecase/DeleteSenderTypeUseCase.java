package springboot.application.sendertype.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.sendertype.exception.SenderTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.sendertype.event.SenderTypeDeletedEvent;
import springboot.domain.sendertype.model.aggregate.SenderType;
import springboot.domain.sendertype.model.valueobject.SenderTypeId;
import springboot.domain.sendertype.port.repository.SenderTypeRepository;

public class DeleteSenderTypeUseCase {
    private final SenderTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteSenderTypeUseCase(SenderTypeRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public SenderTypeDeletedEvent execute(SenderTypeId id) {
        SenderType aggregate = repository.findById(id)
                .orElseThrow(() -> new SenderTypeNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        SenderTypeDeletedEvent event = new SenderTypeDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
