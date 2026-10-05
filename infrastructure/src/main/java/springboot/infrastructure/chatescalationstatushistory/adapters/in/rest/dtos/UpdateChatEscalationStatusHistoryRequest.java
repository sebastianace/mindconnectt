package springboot.infrastructure.chatescalationstatushistory.adapters.in.rest.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record UpdateChatEscalationStatusHistoryRequest(
        @NotNull(message = "escalationId is required")
        UUID escalationId,

        @NotNull(message = "escalationStatusId is required")
        UUID escalationStatusId,

        @NotNull(message = "changedAt is required")
        LocalDateTime changedAt
) {
}
