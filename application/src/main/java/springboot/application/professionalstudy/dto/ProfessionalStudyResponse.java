package springboot.application.professionalstudy.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.professionalstudy.model.aggregate.ProfessionalStudy;

public record ProfessionalStudyResponse(
        UUID id,
        UUID studyId,
        UUID professionalId,
        String title,
        String university,
        boolean valid,
        String resolutionNumber,
        UUID countryId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProfessionalStudyResponse from(ProfessionalStudy aggregate) {
        return new ProfessionalStudyResponse(
                aggregate.id().value(),
                aggregate.studyId().value(),
                aggregate.professionalId().value(),
                aggregate.title(),
                aggregate.university(),
                aggregate.valid(),
                aggregate.resolutionNumber(),
                aggregate.countryId().value(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}
