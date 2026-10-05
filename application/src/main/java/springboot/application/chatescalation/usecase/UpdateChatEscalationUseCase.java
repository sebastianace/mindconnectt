package springboot.application.chatescalation.usecase;

import springboot.application.chatescalation.command.UpdateChatEscalationCommand;
import springboot.application.chatescalation.dto.ChatEscalationResponse;
import springboot.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;
import springboot.domain.chatescalation.model.aggregate.ChatEscalation;
import springboot.domain.chatescalation.port.repository.ChatEscalationRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.escalationstatus.port.repository.EscalationStatusRepository;

public class UpdateChatEscalationUseCase {
    private final ChatEscalationRepository repository;
    private final ChatConversationRepository chatConversationRepository;
    private final EscalationStatusRepository escalationStatusRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateChatEscalationUseCase(
            ChatEscalationRepository repository,
            ChatConversationRepository chatConversationRepository,
            EscalationStatusRepository escalationStatusRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.chatConversationRepository = chatConversationRepository;
        this.escalationStatusRepository = escalationStatusRepository;
        this.eventPublisher = eventPublisher;
    }

    public ChatEscalationResponse execute(UpdateChatEscalationCommand command) {
        ChatEscalation aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ChatEscalationNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.conversationId(),
                command.statusId(),
                command.fromAi(),
                command.reason());
        ChatEscalation saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ChatEscalationResponse.from(saved);
    }

    private void validateReferences(UpdateChatEscalationCommand command) {
        if (!chatConversationRepository.existsById(command.conversationId())) {
            throw new ReferenceNotFoundApplicationException("ChatConversation", command.conversationId().value());
        }
        if (!escalationStatusRepository.existsById(command.statusId())) {
            throw new ReferenceNotFoundApplicationException("EscalationStatus", command.statusId().value());
        }
    }
}
