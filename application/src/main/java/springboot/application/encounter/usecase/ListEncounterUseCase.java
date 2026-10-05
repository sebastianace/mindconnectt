package springboot.application.encounter.usecase;

import java.util.List;

import springboot.application.encounter.dto.EncounterResponse;
import springboot.domain.encounter.port.repository.EncounterRepository;

public class ListEncounterUseCase {
    private final EncounterRepository repository;

    public ListEncounterUseCase(EncounterRepository repository) {
        this.repository = repository;
    }

    public List<EncounterResponse> execute() {
        return repository.findAll().stream()
                .map(EncounterResponse::from)
                .toList();
    }
}
