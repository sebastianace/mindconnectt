package springboot.application.aimodel.usecase;

import java.util.List;

import springboot.application.aimodel.dto.AiModelResponse;
import springboot.domain.aimodel.port.repository.AiModelRepository;

public class ListAiModelUseCase {
    private final AiModelRepository repository;

    public ListAiModelUseCase(AiModelRepository repository) {
        this.repository = repository;
    }

    public List<AiModelResponse> execute() {
        return repository.findAll().stream()
                .map(AiModelResponse::from)
                .toList();
    }
}
