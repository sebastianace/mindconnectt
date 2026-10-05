package springboot.application.chatparticipant.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.chatparticipant.model.aggregate.ChatParticipant;

public record ChatParticipantResponse(
        UUID id,
        UUID conversationId,
        UUID participantTypeId,
        UUID patientId,
        UUID professionalId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ChatParticipantResponse from(ChatParticipant aggregate) {
        return new ChatParticipantResponse(
                aggregate.id().value(),
                aggregate.conversationId().value(),
                aggregate.participantTypeId().value(),
                aggregate.patientId() == null ? null : aggregate.patientId().value(),
                aggregate.professionalId() == null ? null : aggregate.professionalId().value(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}
