package springboot.application.stateregion.usecase;

import java.util.List;

import springboot.application.stateregion.dto.StateRegionResponse;
import springboot.domain.stateregion.port.repository.StateRegionRepository;

public class ListStateRegionUseCase {
    private final StateRegionRepository repository;

    public ListStateRegionUseCase(StateRegionRepository repository) {
        this.repository = repository;
    }

    public List<StateRegionResponse> execute() {
        return repository.findAll().stream()
                .map(StateRegionResponse::from)
                .toList();
    }
}
