package springboot.infrastructure.chatescalation.adapters.in.rest.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateChatEscalationRequest(
        @NotNull(message = "conversationId is required")
        UUID conversationId,

        @NotNull(message = "statusId is required")
        UUID statusId,

        @NotNull(message = "fromAi is required")
        Boolean fromAi,

        @NotBlank(message = "reason is required")
        String reason
) {
}
