package springboot.application.stateregion.command;

import java.util.Objects;

import springboot.domain.country.model.valueobject.CountryId;
import springboot.domain.stateregion.model.valueobject.StateRegionId;

public record UpdateStateRegionCommand(
        StateRegionId id,
        String nameRegion,
        String codeRegion,
        String description,
        boolean active,
        CountryId countryId
) {
    public UpdateStateRegionCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(nameRegion, "nameRegion must not be null");
        Objects.requireNonNull(codeRegion, "codeRegion must not be null");
        Objects.requireNonNull(description, "description must not be null");
        Objects.requireNonNull(countryId, "countryId must not be null");
    }
}
