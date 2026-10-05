package springboot.application.airunstatus.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;
import springboot.domain.airunstatus.event.AiRunStatusDeletedEvent;
import springboot.domain.airunstatus.model.aggregate.AiRunStatus;
import springboot.domain.airunstatus.model.valueobject.AiRunStatusId;
import springboot.domain.airunstatus.port.repository.AiRunStatusRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteAiRunStatusUseCase {
    private final AiRunStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteAiRunStatusUseCase(AiRunStatusRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public AiRunStatusDeletedEvent execute(AiRunStatusId id) {
        AiRunStatus aggregate = repository.findById(id)
                .orElseThrow(() -> new AiRunStatusNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        AiRunStatusDeletedEvent event = new AiRunStatusDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
