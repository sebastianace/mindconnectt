package springboot.application.chatairunerror.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
import springboot.domain.chatairunerror.event.ChatAiRunErrorDeletedEvent;
import springboot.domain.chatairunerror.model.aggregate.ChatAiRunError;
import springboot.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import springboot.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteChatAiRunErrorUseCase {
    private final ChatAiRunErrorRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteChatAiRunErrorUseCase(ChatAiRunErrorRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ChatAiRunErrorDeletedEvent execute(ChatAiRunErrorId id) {
        ChatAiRunError aggregate = repository.findById(id)
                .orElseThrow(() -> new ChatAiRunErrorNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ChatAiRunErrorDeletedEvent event = new ChatAiRunErrorDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
