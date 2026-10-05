package springboot.application.chatairunmetric.usecase;

import springboot.application.chatairunmetric.command.RegisterChatAiRunMetricCommand;
import springboot.application.chatairunmetric.dto.ChatAiRunMetricResponse;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.chatairun.port.repository.ChatAiRunRepository;
import springboot.domain.chatairunmetric.model.aggregate.ChatAiRunMetric;
import springboot.domain.chatairunmetric.port.repository.ChatAiRunMetricRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class RegisterChatAiRunMetricUseCase {
    private final ChatAiRunMetricRepository repository;
    private final ChatAiRunRepository chatAiRunRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterChatAiRunMetricUseCase(
            ChatAiRunMetricRepository repository,
            ChatAiRunRepository chatAiRunRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.chatAiRunRepository = chatAiRunRepository;
        this.eventPublisher = eventPublisher;
    }

    public ChatAiRunMetricResponse execute(RegisterChatAiRunMetricCommand command) {
        validateReferences(command);
        ChatAiRunMetric aggregate = ChatAiRunMetric.register(
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

    private void validateReferences(RegisterChatAiRunMetricCommand command) {
        if (!chatAiRunRepository.existsById(command.aiRunId())) {
            throw new ReferenceNotFoundApplicationException("ChatAiRun", command.aiRunId().value());
        }
    }
}
