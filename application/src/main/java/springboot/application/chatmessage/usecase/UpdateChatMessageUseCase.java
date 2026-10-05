package springboot.application.chatmessage.usecase;

import springboot.application.chatmessage.command.UpdateChatMessageCommand;
import springboot.application.chatmessage.dto.ChatMessageResponse;
import springboot.application.chatmessage.exception.ChatMessageNotFoundApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;
import springboot.domain.chatmessage.model.aggregate.ChatMessage;
import springboot.domain.chatmessage.port.repository.ChatMessageRepository;
import springboot.domain.chatparticipant.port.repository.ChatParticipantRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.messagetype.port.repository.MessageTypeRepository;

public class UpdateChatMessageUseCase {
    private final ChatMessageRepository repository;
    private final ChatConversationRepository chatConversationRepository;
    private final MessageTypeRepository messageTypeRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateChatMessageUseCase(
            ChatMessageRepository repository,
            ChatConversationRepository chatConversationRepository,
            MessageTypeRepository messageTypeRepository,
            ChatParticipantRepository chatParticipantRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.chatConversationRepository = chatConversationRepository;
        this.messageTypeRepository = messageTypeRepository;
        this.chatParticipantRepository = chatParticipantRepository;
        this.eventPublisher = eventPublisher;
    }

    public ChatMessageResponse execute(UpdateChatMessageCommand command) {
        ChatMessage aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ChatMessageNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.conversationId(),
                command.messageTypeId(),
                command.participantId(),
                command.content(),
                command.metadata());
        ChatMessage saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ChatMessageResponse.from(saved);
    }

    private void validateReferences(UpdateChatMessageCommand command) {
        if (!chatConversationRepository.existsById(command.conversationId())) {
            throw new ReferenceNotFoundApplicationException("ChatConversation", command.conversationId().value());
        }
        if (!messageTypeRepository.existsById(command.messageTypeId())) {
            throw new ReferenceNotFoundApplicationException("MessageType", command.messageTypeId().value());
        }
        if (!chatParticipantRepository.existsById(command.participantId())) {
            throw new ReferenceNotFoundApplicationException("ChatParticipant", command.participantId().value());
        }
    }
}
