package springboot.application.chatescalationassignment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.chatescalationassignment.model.aggregate.ChatEscalationAssignment;

public record ChatEscalationAssignmentResponse(
        UUID id,
        UUID escalationId,
        UUID professionalId,
        LocalDateTime assignedAt
) {
    public static ChatEscalationAssignmentResponse from(ChatEscalationAssignment aggregate) {
        return new ChatEscalationAssignmentResponse(
                aggregate.id().value(),
                aggregate.escalationId().value(),
                aggregate.professionalId().value(),
                aggregate.assignedAt());
    }
}
