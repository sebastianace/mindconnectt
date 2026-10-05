package springboot.application.priority.usecase;

import springboot.application.priority.dto.PriorityResponse;
import springboot.application.priority.exception.PriorityNotFoundApplicationException;
import springboot.domain.priority.model.valueobject.PriorityId;
import springboot.domain.priority.port.repository.PriorityRepository;

public class GetPriorityByIdUseCase {
    private final PriorityRepository repository;

    public GetPriorityByIdUseCase(PriorityRepository repository) {
        this.repository = repository;
    }

    public PriorityResponse execute(PriorityId id) {
        return repository.findById(id)
                .map(PriorityResponse::from)
                .orElseThrow(() -> new PriorityNotFoundApplicationException(id.value().toString()));
    }
}
