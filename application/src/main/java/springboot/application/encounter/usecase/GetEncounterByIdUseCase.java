package springboot.application.encounter.usecase;

import springboot.application.encounter.dto.EncounterResponse;
import springboot.application.encounter.exception.EncounterNotFoundApplicationException;
import springboot.domain.encounter.model.valueobject.EncounterId;
import springboot.domain.encounter.port.repository.EncounterRepository;

public class GetEncounterByIdUseCase {
    private final EncounterRepository repository;

    public GetEncounterByIdUseCase(EncounterRepository repository) {
        this.repository = repository;
    }

    public EncounterResponse execute(EncounterId id) {
        return repository.findById(id)
                .map(EncounterResponse::from)
                .orElseThrow(() -> new EncounterNotFoundApplicationException(id.value().toString()));
    }
}
