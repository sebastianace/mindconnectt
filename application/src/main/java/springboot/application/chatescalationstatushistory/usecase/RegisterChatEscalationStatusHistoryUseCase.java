package springboot.application.chatescalationstatushistory.usecase;

import springboot.application.chatescalationstatushistory.command.RegisterChatEscalationStatusHistoryCommand;
import springboot.application.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.chatescalation.port.repository.ChatEscalationRepository;
import springboot.domain.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
import springboot.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.escalationstatus.port.repository.EscalationStatusRepository;

public class RegisterChatEscalationStatusHistoryUseCase {
    private final ChatEscalationStatusHistoryRepository repository;
    private final ChatEscalationRepository chatEscalationRepository;
    private final EscalationStatusRepository escalationStatusRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterChatEscalationStatusHistoryUseCase(
            ChatEscalationStatusHistoryRepository repository,
            ChatEscalationRepository chatEscalationRepository,
            EscalationStatusRepository escalationStatusRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.chatEscalationRepository = chatEscalationRepository;
        this.escalationStatusRepository = escalationStatusRepository;
        this.eventPublisher = eventPublisher;
    }

    public ChatEscalationStatusHistoryResponse execute(RegisterChatEscalationStatusHistoryCommand command) {
        validateReferences(command);
        ChatEscalationStatusHistory aggregate = ChatEscalationStatusHistory.register(
                command.escalationId(),
                command.escalationStatusId(),
                command.changedAt());
        ChatEscalationStatusHistory saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ChatEscalationStatusHistoryResponse.from(saved);
    }

    private void validateReferences(RegisterChatEscalationStatusHistoryCommand command) {
        if (!chatEscalationRepository.existsById(command.escalationId())) {
            throw new ReferenceNotFoundApplicationException("ChatEscalation", command.escalationId().value());
        }
        if (!escalationStatusRepository.existsById(command.escalationStatusId())) {
            throw new ReferenceNotFoundApplicationException("EscalationStatus", command.escalationStatusId().value());
        }
    }
}
