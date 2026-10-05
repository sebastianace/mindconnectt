package springboot.application.chatairunmetric.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.chatairunmetric.exception.ChatAiRunMetricNotFoundApplicationException;
import springboot.domain.chatairunmetric.event.ChatAiRunMetricDeletedEvent;
import springboot.domain.chatairunmetric.model.aggregate.ChatAiRunMetric;
import springboot.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
import springboot.domain.chatairunmetric.port.repository.ChatAiRunMetricRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteChatAiRunMetricUseCase {
    private final ChatAiRunMetricRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteChatAiRunMetricUseCase(ChatAiRunMetricRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ChatAiRunMetricDeletedEvent execute(ChatAiRunMetricId id) {
        ChatAiRunMetric aggregate = repository.findById(id)
                .orElseThrow(() -> new ChatAiRunMetricNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ChatAiRunMetricDeletedEvent event = new ChatAiRunMetricDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
