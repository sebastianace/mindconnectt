package springboot.application.chatairunmetric.usecase;

import springboot.application.chatairunmetric.dto.ChatAiRunMetricResponse;
import springboot.application.chatairunmetric.exception.ChatAiRunMetricNotFoundApplicationException;
import springboot.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
import springboot.domain.chatairunmetric.port.repository.ChatAiRunMetricRepository;

public class GetChatAiRunMetricByIdUseCase {
    private final ChatAiRunMetricRepository repository;

    public GetChatAiRunMetricByIdUseCase(ChatAiRunMetricRepository repository) {
        this.repository = repository;
    }

    public ChatAiRunMetricResponse execute(ChatAiRunMetricId id) {
        return repository.findById(id)
                .map(ChatAiRunMetricResponse::from)
                .orElseThrow(() -> new ChatAiRunMetricNotFoundApplicationException(id.value().toString()));
    }
}
