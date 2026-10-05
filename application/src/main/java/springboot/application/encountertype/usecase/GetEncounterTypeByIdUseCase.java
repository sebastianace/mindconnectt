package springboot.application.encountertype.usecase;

import springboot.application.encountertype.dto.EncounterTypeResponse;
import springboot.application.encountertype.exception.EncounterTypeNotFoundApplicationException;
import springboot.domain.encountertype.model.valueobject.EncounterTypeId;
import springboot.domain.encountertype.port.repository.EncounterTypeRepository;

public class GetEncounterTypeByIdUseCase {
    private final EncounterTypeRepository repository;

    public GetEncounterTypeByIdUseCase(EncounterTypeRepository repository) {
        this.repository = repository;
    }

    public EncounterTypeResponse execute(EncounterTypeId id) {
        return repository.findById(id)
                .map(EncounterTypeResponse::from)
                .orElseThrow(() -> new EncounterTypeNotFoundApplicationException(id.value().toString()));
    }
}
