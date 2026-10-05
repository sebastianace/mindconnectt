package springboot.application.aimodel.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.aimodel.exception.AiModelNotFoundApplicationException;
import springboot.domain.aimodel.event.AiModelDeletedEvent;
import springboot.domain.aimodel.model.aggregate.AiModel;
import springboot.domain.aimodel.model.valueobject.AiModelId;
import springboot.domain.aimodel.port.repository.AiModelRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteAiModelUseCase {
    private final AiModelRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteAiModelUseCase(AiModelRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public AiModelDeletedEvent execute(AiModelId id) {
        AiModel aggregate = repository.findById(id)
                .orElseThrow(() -> new AiModelNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        AiModelDeletedEvent event = new AiModelDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
