package springboot.application.chatescalationstatushistory.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.chatescalationstatushistory.exception.ChatEscalationStatusHistoryNotFoundApplicationException;
import springboot.domain.chatescalationstatushistory.event.ChatEscalationStatusHistoryDeletedEvent;
import springboot.domain.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
import springboot.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import springboot.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteChatEscalationStatusHistoryUseCase {
    private final ChatEscalationStatusHistoryRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ChatEscalationStatusHistoryDeletedEvent execute(ChatEscalationStatusHistoryId id) {
        ChatEscalationStatusHistory aggregate = repository.findById(id)
                .orElseThrow(() -> new ChatEscalationStatusHistoryNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ChatEscalationStatusHistoryDeletedEvent event = new ChatEscalationStatusHistoryDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
