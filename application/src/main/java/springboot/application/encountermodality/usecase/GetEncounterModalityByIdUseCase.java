package springboot.application.encountermodality.usecase;

import springboot.application.encountermodality.dto.EncounterModalityResponse;
import springboot.application.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import springboot.domain.encountermodality.model.valueobject.EncounterModalityId;
import springboot.domain.encountermodality.port.repository.EncounterModalityRepository;

public class GetEncounterModalityByIdUseCase {
    private final EncounterModalityRepository repository;

    public GetEncounterModalityByIdUseCase(EncounterModalityRepository repository) {
        this.repository = repository;
    }

    public EncounterModalityResponse execute(EncounterModalityId id) {
        return repository.findById(id)
                .map(EncounterModalityResponse::from)
                .orElseThrow(() -> new EncounterModalityNotFoundApplicationException(id.value().toString()));
    }
}
