package springboot.application.chatescalationstatushistory.usecase;

import springboot.application.chatescalationstatushistory.command.UpdateChatEscalationStatusHistoryCommand;
import springboot.application.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import springboot.application.chatescalationstatushistory.exception.ChatEscalationStatusHistoryNotFoundApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.chatescalation.port.repository.ChatEscalationRepository;
import springboot.domain.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
import springboot.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.escalationstatus.port.repository.EscalationStatusRepository;

public class UpdateChatEscalationStatusHistoryUseCase {
    private final ChatEscalationStatusHistoryRepository repository;
    private final ChatEscalationRepository chatEscalationRepository;
    private final EscalationStatusRepository escalationStatusRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateChatEscalationStatusHistoryUseCase(
            ChatEscalationStatusHistoryRepository repository,
            ChatEscalationRepository chatEscalationRepository,
            EscalationStatusRepository escalationStatusRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.chatEscalationRepository = chatEscalationRepository;
        this.escalationStatusRepository = escalationStatusRepository;
        this.eventPublisher = eventPublisher;
    }

    public ChatEscalationStatusHistoryResponse execute(UpdateChatEscalationStatusHistoryCommand command) {
        ChatEscalationStatusHistory aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ChatEscalationStatusHistoryNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.escalationId(),
                command.escalationStatusId(),
                command.changedAt());
        ChatEscalationStatusHistory saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ChatEscalationStatusHistoryResponse.from(saved);
    }

    private void validateReferences(UpdateChatEscalationStatusHistoryCommand command) {
        if (!chatEscalationRepository.existsById(command.escalationId())) {
            throw new ReferenceNotFoundApplicationException("ChatEscalation", command.escalationId().value());
        }
        if (!escalationStatusRepository.existsById(command.escalationStatusId())) {
            throw new ReferenceNotFoundApplicationException("EscalationStatus", command.escalationStatusId().value());
        }
    }
}
