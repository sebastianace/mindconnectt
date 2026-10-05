package springboot.application.aimodel.usecase;

import springboot.application.aimodel.dto.AiModelResponse;
import springboot.application.aimodel.exception.AiModelNotFoundApplicationException;
import springboot.domain.aimodel.model.valueobject.AiModelId;
import springboot.domain.aimodel.port.repository.AiModelRepository;

public class GetAiModelByIdUseCase {
    private final AiModelRepository repository;

    public GetAiModelByIdUseCase(AiModelRepository repository) {
        this.repository = repository;
    }

    public AiModelResponse execute(AiModelId id) {
        return repository.findById(id)
                .map(AiModelResponse::from)
                .orElseThrow(() -> new AiModelNotFoundApplicationException(id.value().toString()));
    }
}
