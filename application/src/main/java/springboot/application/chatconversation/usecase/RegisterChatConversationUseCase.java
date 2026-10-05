package springboot.application.chatconversation.usecase;

import springboot.application.chatconversation.command.RegisterChatConversationCommand;
import springboot.application.chatconversation.dto.ChatConversationResponse;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.chatconversation.model.aggregate.ChatConversation;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.conversationstatus.port.repository.ConversationStatusRepository;
import springboot.domain.priority.port.repository.PriorityRepository;

public class RegisterChatConversationUseCase {
    private final ChatConversationRepository repository;
    private final ConversationStatusRepository conversationStatusRepository;
    private final PriorityRepository priorityRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterChatConversationUseCase(
            ChatConversationRepository repository,
            ConversationStatusRepository conversationStatusRepository,
            PriorityRepository priorityRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.conversationStatusRepository = conversationStatusRepository;
        this.priorityRepository = priorityRepository;
        this.eventPublisher = eventPublisher;
    }

    public ChatConversationResponse execute(RegisterChatConversationCommand command) {
        validateReferences(command);
        ChatConversation aggregate = ChatConversation.register(
                command.conversationStatusId(),
                command.priorityId(),
                command.lastMessageAt(),
                command.closed(),
                command.closedAt(),
                command.closedBy());
        ChatConversation saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ChatConversationResponse.from(saved);
    }

    private void validateReferences(RegisterChatConversationCommand command) {
        if (!conversationStatusRepository.existsById(command.conversationStatusId())) {
            throw new ReferenceNotFoundApplicationException("ConversationStatus", command.conversationStatusId().value());
        }
        if (!priorityRepository.existsById(command.priorityId())) {
            throw new ReferenceNotFoundApplicationException("Priority", command.priorityId().value());
        }
    }
}
