package springboot.application.chatairunerror.usecase;

import springboot.application.chatairunerror.command.RegisterChatAiRunErrorCommand;
import springboot.application.chatairunerror.dto.ChatAiRunErrorResponse;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.chatairun.port.repository.ChatAiRunRepository;
import springboot.domain.chatairunerror.model.aggregate.ChatAiRunError;
import springboot.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class RegisterChatAiRunErrorUseCase {
    private final ChatAiRunErrorRepository repository;
    private final ChatAiRunRepository chatAiRunRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterChatAiRunErrorUseCase(
            ChatAiRunErrorRepository repository,
            ChatAiRunRepository chatAiRunRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.chatAiRunRepository = chatAiRunRepository;
        this.eventPublisher = eventPublisher;
    }

    public ChatAiRunErrorResponse execute(RegisterChatAiRunErrorCommand command) {
        validateReferences(command);
        ChatAiRunError aggregate = ChatAiRunError.register(
                command.aiRunId(),
                command.errorMessage(),
                command.errorCode(),
                command.providerErrorId());
        ChatAiRunError saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ChatAiRunErrorResponse.from(saved);
    }

    private void validateReferences(RegisterChatAiRunErrorCommand command) {
        if (!chatAiRunRepository.existsById(command.aiRunId())) {
            throw new ReferenceNotFoundApplicationException("ChatAiRun", command.aiRunId().value());
        }
    }
}
