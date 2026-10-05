package springboot.application.chatconversationaisetting.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.chatconversationaisetting.model.aggregate.ChatConversationAiSetting;

public record ChatConversationAiSettingResponse(
        UUID id,
        UUID conversationId,
        boolean aiEnabled,
        UUID defaultModelId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ChatConversationAiSettingResponse from(ChatConversationAiSetting aggregate) {
        return new ChatConversationAiSettingResponse(
                aggregate.id().value(),
                aggregate.conversationId().value(),
                aggregate.aiEnabled(),
                aggregate.defaultModelId().value(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}
