package springboot.application.chatescalationstatushistory.usecase;

import springboot.application.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import springboot.application.chatescalationstatushistory.exception.ChatEscalationStatusHistoryNotFoundApplicationException;
import springboot.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import springboot.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;

public class GetChatEscalationStatusHistoryByIdUseCase {
    private final ChatEscalationStatusHistoryRepository repository;

    public GetChatEscalationStatusHistoryByIdUseCase(ChatEscalationStatusHistoryRepository repository) {
        this.repository = repository;
    }

    public ChatEscalationStatusHistoryResponse execute(ChatEscalationStatusHistoryId id) {
        return repository.findById(id)
                .map(ChatEscalationStatusHistoryResponse::from)
                .orElseThrow(() -> new ChatEscalationStatusHistoryNotFoundApplicationException(id.value().toString()));
    }
}
