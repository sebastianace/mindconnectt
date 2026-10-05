package springboot.application.chatairunerror.usecase;

import springboot.application.chatairunerror.dto.ChatAiRunErrorResponse;
import springboot.application.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
import springboot.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import springboot.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;

public class GetChatAiRunErrorByIdUseCase {
    private final ChatAiRunErrorRepository repository;

    public GetChatAiRunErrorByIdUseCase(ChatAiRunErrorRepository repository) {
        this.repository = repository;
    }

    public ChatAiRunErrorResponse execute(ChatAiRunErrorId id) {
        return repository.findById(id)
                .map(ChatAiRunErrorResponse::from)
                .orElseThrow(() -> new ChatAiRunErrorNotFoundApplicationException(id.value().toString()));
    }
}
