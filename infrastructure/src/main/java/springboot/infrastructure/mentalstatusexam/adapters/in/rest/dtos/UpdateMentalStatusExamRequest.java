package springboot.infrastructure.mentalstatusexam.adapters.in.rest.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateMentalStatusExamRequest(
        @NotNull(message = "encounterId is required")
        UUID encounterId,

        @NotBlank(message = "appearance is required")
        String appearance,

        @NotBlank(message = "behavior is required")
        String behavior,

        @NotBlank(message = "attitude is required")
        String attitude,

        @NotBlank(message = "consciousness is required")
        String consciousness,

        @NotBlank(message = "orientation is required")
        String orientation,

        @NotBlank(message = "attention is required")
        String attention,

        @NotBlank(message = "memory is required")
        String memory,

        @NotBlank(message = "speech is required")
        String speech,

        @NotBlank(message = "mood is required")
        String mood,

        @NotBlank(message = "affect is required")
        String affect,

        @NotBlank(message = "thoughtProcess is required")
        String thoughtProcess,

        @NotBlank(message = "thoughtContent is required")
        String thoughtContent,

        @NotBlank(message = "perception is required")
        String perception,

        @NotBlank(message = "judgment is required")
        String judgment,

        @NotBlank(message = "insight is required")
        String insight,

        @NotBlank(message = "psychomotorActivity is required")
        String psychomotorActivity,

        @NotBlank(message = "observations is required")
        String observations,

        @NotNull(message = "createdBy is required")
        UUID createdBy
) {
}
