package springboot.application.airunstatus.usecase;

import springboot.application.airunstatus.dto.AiRunStatusResponse;
import springboot.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;
import springboot.domain.airunstatus.model.valueobject.AiRunStatusId;
import springboot.domain.airunstatus.port.repository.AiRunStatusRepository;

public class GetAiRunStatusByIdUseCase {
    private final AiRunStatusRepository repository;

    public GetAiRunStatusByIdUseCase(AiRunStatusRepository repository) {
        this.repository = repository;
    }

    public AiRunStatusResponse execute(AiRunStatusId id) {
        return repository.findById(id)
                .map(AiRunStatusResponse::from)
                .orElseThrow(() -> new AiRunStatusNotFoundApplicationException(id.value().toString()));
    }
}
