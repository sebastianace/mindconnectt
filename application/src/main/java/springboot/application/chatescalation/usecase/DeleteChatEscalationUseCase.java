package springboot.application.chatescalation.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import springboot.domain.chatescalation.event.ChatEscalationDeletedEvent;
import springboot.domain.chatescalation.model.aggregate.ChatEscalation;
import springboot.domain.chatescalation.model.valueobject.ChatEscalationId;
import springboot.domain.chatescalation.port.repository.ChatEscalationRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteChatEscalationUseCase {
    private final ChatEscalationRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteChatEscalationUseCase(ChatEscalationRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ChatEscalationDeletedEvent execute(ChatEscalationId id) {
        ChatEscalation aggregate = repository.findById(id)
                .orElseThrow(() -> new ChatEscalationNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ChatEscalationDeletedEvent event = new ChatEscalationDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
