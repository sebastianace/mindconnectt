package springboot.application.chatairun.usecase;

import springboot.application.chatairun.command.RegisterChatAiRunCommand;
import springboot.application.chatairun.dto.ChatAiRunResponse;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.aimodel.port.repository.AiModelRepository;
import springboot.domain.airunstatus.port.repository.AiRunStatusRepository;
import springboot.domain.chatairun.model.aggregate.ChatAiRun;
import springboot.domain.chatairun.port.repository.ChatAiRunRepository;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;
import springboot.domain.chatmessage.port.repository.ChatMessageRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class RegisterChatAiRunUseCase {
    private final ChatAiRunRepository repository;
    private final ChatConversationRepository chatConversationRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final AiModelRepository aiModelRepository;
    private final AiRunStatusRepository aiRunStatusRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterChatAiRunUseCase(
            ChatAiRunRepository repository,
            ChatConversationRepository chatConversationRepository,
            ChatMessageRepository chatMessageRepository,
            AiModelRepository aiModelRepository,
            AiRunStatusRepository aiRunStatusRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.chatConversationRepository = chatConversationRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.aiModelRepository = aiModelRepository;
        this.aiRunStatusRepository = aiRunStatusRepository;
        this.eventPublisher = eventPublisher;
    }

    public ChatAiRunResponse execute(RegisterChatAiRunCommand command) {
        validateReferences(command);
        ChatAiRun aggregate = ChatAiRun.register(
                command.conversationId(),
                command.messageId(),
                command.modelId(),
                command.aiRunStatusId());
        ChatAiRun saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ChatAiRunResponse.from(saved);
    }

    private void validateReferences(RegisterChatAiRunCommand command) {
        if (!chatConversationRepository.existsById(command.conversationId())) {
            throw new ReferenceNotFoundApplicationException("ChatConversation", command.conversationId().value());
        }
        if (!chatMessageRepository.existsById(command.messageId())) {
            throw new ReferenceNotFoundApplicationException("ChatMessage", command.messageId().value());
        }
        if (!aiModelRepository.existsById(command.modelId())) {
            throw new ReferenceNotFoundApplicationException("AiModel", command.modelId().value());
        }
        if (!aiRunStatusRepository.existsById(command.aiRunStatusId())) {
            throw new ReferenceNotFoundApplicationException("AiRunStatus", command.aiRunStatusId().value());
        }
    }
}
