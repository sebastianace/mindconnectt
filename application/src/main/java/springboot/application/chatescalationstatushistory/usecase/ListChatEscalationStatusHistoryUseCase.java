package springboot.application.chatescalationstatushistory.usecase;

import java.util.List;

import springboot.application.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import springboot.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;

public class ListChatEscalationStatusHistoryUseCase {
    private final ChatEscalationStatusHistoryRepository repository;

    public ListChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository) {
        this.repository = repository;
    }

    public List<ChatEscalationStatusHistoryResponse> execute() {
        return repository.findAll().stream()
                .map(ChatEscalationStatusHistoryResponse::from)
                .toList();
    }
}
