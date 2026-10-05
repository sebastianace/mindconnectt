package springboot.application.chatparticipant.usecase;

import java.util.List;

import springboot.application.chatparticipant.dto.ChatParticipantResponse;
import springboot.domain.chatparticipant.port.repository.ChatParticipantRepository;

public class ListChatParticipantUseCase {
    private final ChatParticipantRepository repository;

    public ListChatParticipantUseCase(ChatParticipantRepository repository) {
        this.repository = repository;
    }

    public List<ChatParticipantResponse> execute() {
        return repository.findAll().stream()
                .map(ChatParticipantResponse::from)
                .toList();
    }
}
