package springboot.application.chatairunerror.usecase;

import springboot.application.chatairunerror.command.UpdateChatAiRunErrorCommand;
import springboot.application.chatairunerror.dto.ChatAiRunErrorResponse;
import springboot.application.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.chatairun.port.repository.ChatAiRunRepository;
import springboot.domain.chatairunerror.model.aggregate.ChatAiRunError;
import springboot.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class UpdateChatAiRunErrorUseCase {
    private final ChatAiRunErrorRepository repository;
    private final ChatAiRunRepository chatAiRunRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateChatAiRunErrorUseCase(
            ChatAiRunErrorRepository repository,
            ChatAiRunRepository chatAiRunRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.chatAiRunRepository = chatAiRunRepository;
        this.eventPublisher = eventPublisher;
    }

    public ChatAiRunErrorResponse execute(UpdateChatAiRunErrorCommand command) {
        ChatAiRunError aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ChatAiRunErrorNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.aiRunId(),
                command.errorMessage(),
                command.errorCode(),
                command.providerErrorId());
        ChatAiRunError saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ChatAiRunErrorResponse.from(saved);
    }

    private void validateReferences(UpdateChatAiRunErrorCommand command) {
        if (!chatAiRunRepository.existsById(command.aiRunId())) {
            throw new ReferenceNotFoundApplicationException("ChatAiRun", command.aiRunId().value());
        }
    }
}
