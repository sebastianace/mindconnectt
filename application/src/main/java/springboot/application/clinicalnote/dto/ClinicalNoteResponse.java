package springboot.application.clinicalnote.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.clinicalnote.model.aggregate.ClinicalNote;

public record ClinicalNoteResponse(
        UUID id,
        UUID encounterId,
        UUID professionalId,
        String subjective,
        String objective,
        String assessment,
        String plan,
        String additionalNotes,
        LocalDateTime signedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ClinicalNoteResponse from(ClinicalNote aggregate) {
        return new ClinicalNoteResponse(
                aggregate.id().value(),
                aggregate.encounterId().value(),
                aggregate.professionalId().value(),
                aggregate.subjective(),
                aggregate.objective(),
                aggregate.assessment(),
                aggregate.plan(),
                aggregate.additionalNotes(),
                aggregate.signedAt(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}
