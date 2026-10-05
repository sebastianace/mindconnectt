package springboot.application.chatconversationaisetting.usecase;

import springboot.application.chatconversationaisetting.command.RegisterChatConversationAiSettingCommand;
import springboot.application.chatconversationaisetting.dto.ChatConversationAiSettingResponse;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.aimodel.port.repository.AiModelRepository;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;
import springboot.domain.chatconversationaisetting.model.aggregate.ChatConversationAiSetting;
import springboot.domain.chatconversationaisetting.port.repository.ChatConversationAiSettingRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class RegisterChatConversationAiSettingUseCase {
    private final ChatConversationAiSettingRepository repository;
    private final ChatConversationRepository chatConversationRepository;
    private final AiModelRepository aiModelRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterChatConversationAiSettingUseCase(
            ChatConversationAiSettingRepository repository,
            ChatConversationRepository chatConversationRepository,
            AiModelRepository aiModelRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.chatConversationRepository = chatConversationRepository;
        this.aiModelRepository = aiModelRepository;
        this.eventPublisher = eventPublisher;
    }

    public ChatConversationAiSettingResponse execute(RegisterChatConversationAiSettingCommand command) {
        validateReferences(command);
        ChatConversationAiSetting aggregate = ChatConversationAiSetting.register(
                command.conversationId(),
                command.aiEnabled(),
                command.defaultModelId());
        ChatConversationAiSetting saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ChatConversationAiSettingResponse.from(saved);
    }

    private void validateReferences(RegisterChatConversationAiSettingCommand command) {
        if (!chatConversationRepository.existsById(command.conversationId())) {
            throw new ReferenceNotFoundApplicationException("ChatConversation", command.conversationId().value());
        }
        if (!aiModelRepository.existsById(command.defaultModelId())) {
            throw new ReferenceNotFoundApplicationException("AiModel", command.defaultModelId().value());
        }
    }
}
