package springboot.application.chatmessage.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.chatmessage.exception.ChatMessageNotFoundApplicationException;
import springboot.domain.chatmessage.event.ChatMessageDeletedEvent;
import springboot.domain.chatmessage.model.aggregate.ChatMessage;
import springboot.domain.chatmessage.model.valueobject.ChatMessageId;
import springboot.domain.chatmessage.port.repository.ChatMessageRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteChatMessageUseCase {
    private final ChatMessageRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteChatMessageUseCase(ChatMessageRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ChatMessageDeletedEvent execute(ChatMessageId id) {
        ChatMessage aggregate = repository.findById(id)
                .orElseThrow(() -> new ChatMessageNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ChatMessageDeletedEvent event = new ChatMessageDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
