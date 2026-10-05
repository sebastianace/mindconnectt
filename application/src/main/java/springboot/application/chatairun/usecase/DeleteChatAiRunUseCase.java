package springboot.application.chatairun.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
import springboot.domain.chatairun.event.ChatAiRunDeletedEvent;
import springboot.domain.chatairun.model.aggregate.ChatAiRun;
import springboot.domain.chatairun.model.valueobject.ChatAiRunId;
import springboot.domain.chatairun.port.repository.ChatAiRunRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteChatAiRunUseCase {
    private final ChatAiRunRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteChatAiRunUseCase(ChatAiRunRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ChatAiRunDeletedEvent execute(ChatAiRunId id) {
        ChatAiRun aggregate = repository.findById(id)
                .orElseThrow(() -> new ChatAiRunNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ChatAiRunDeletedEvent event = new ChatAiRunDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
