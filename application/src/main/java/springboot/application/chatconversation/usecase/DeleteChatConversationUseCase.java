package springboot.application.chatconversation.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import springboot.domain.chatconversation.event.ChatConversationDeletedEvent;
import springboot.domain.chatconversation.model.aggregate.ChatConversation;
import springboot.domain.chatconversation.model.valueobject.ChatConversationId;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteChatConversationUseCase {
    private final ChatConversationRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteChatConversationUseCase(ChatConversationRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ChatConversationDeletedEvent execute(ChatConversationId id) {
        ChatConversation aggregate = repository.findById(id)
                .orElseThrow(() -> new ChatConversationNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ChatConversationDeletedEvent event = new ChatConversationDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
