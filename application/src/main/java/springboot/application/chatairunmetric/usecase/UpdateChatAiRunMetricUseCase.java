package springboot.application.chatairunmetric.usecase;

import springboot.application.chatairunmetric.command.UpdateChatAiRunMetricCommand;
import springboot.application.chatairunmetric.dto.ChatAiRunMetricResponse;
import springboot.application.chatairunmetric.exception.ChatAiRunMetricNotFoundApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.chatairun.port.repository.ChatAiRunRepository;
import springboot.domain.chatairunmetric.model.aggregate.ChatAiRunMetric;
import springboot.domain.chatairunmetric.port.repository.ChatAiRunMetricRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class UpdateChatAiRunMetricUseCase {
    private final ChatAiRunMetricRepository repository;
    private final ChatAiRunRepository chatAiRunRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateChatAiRunMetricUseCase(
            ChatAiRunMetricRepository repository,
            ChatAiRunRepository chatAiRunRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.chatAiRunRepository = chatAiRunRepository;
        this.eventPublisher = eventPublisher;
    }

    public ChatAiRunMetricResponse execute(UpdateChatAiRunMetricCommand command) {
        ChatAiRunMetric aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ChatAiRunMetricNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.aiRunId(),
                command.promptTokens(),
                command.completionTokens(),
                command.totalTokens(),
                command.cost());
        ChatAiRunMetric saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ChatAiRunMetricResponse.from(saved);
    }

    private void validateReferences(UpdateChatAiRunMetricCommand command) {
        if (!chatAiRunRepository.existsById(command.aiRunId())) {
            throw new ReferenceNotFoundApplicationException("ChatAiRun", command.aiRunId().value());
        }
    }
}
