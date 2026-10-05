package springboot.application.conversationstatus.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.conversationstatus.exception.ConversationStatusNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.conversationstatus.event.ConversationStatusDeletedEvent;
import springboot.domain.conversationstatus.model.aggregate.ConversationStatus;
import springboot.domain.conversationstatus.model.valueobject.ConversationStatusId;
import springboot.domain.conversationstatus.port.repository.ConversationStatusRepository;

public class DeleteConversationStatusUseCase {
    private final ConversationStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteConversationStatusUseCase(ConversationStatusRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ConversationStatusDeletedEvent execute(ConversationStatusId id) {
        ConversationStatus aggregate = repository.findById(id)
                .orElseThrow(() -> new ConversationStatusNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ConversationStatusDeletedEvent event = new ConversationStatusDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
