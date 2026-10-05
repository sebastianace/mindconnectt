package springboot.infrastructure.chatmessage.adapters.in.rest.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateChatMessageRequest(
        @NotNull(message = "conversationId is required")
        UUID conversationId,

        @NotNull(message = "messageTypeId is required")
        UUID messageTypeId,

        @NotNull(message = "participantId is required")
        UUID participantId,

        @NotBlank(message = "content is required")
        String content,

        @NotBlank(message = "metadata is required")
        String metadata
) {
}
