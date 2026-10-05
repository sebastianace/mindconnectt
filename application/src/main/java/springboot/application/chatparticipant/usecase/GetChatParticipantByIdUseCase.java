package springboot.application.chatparticipant.usecase;

import springboot.application.chatparticipant.dto.ChatParticipantResponse;
import springboot.application.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import springboot.domain.chatparticipant.model.valueobject.ChatParticipantId;
import springboot.domain.chatparticipant.port.repository.ChatParticipantRepository;

public class GetChatParticipantByIdUseCase {
    private final ChatParticipantRepository repository;

    public GetChatParticipantByIdUseCase(ChatParticipantRepository repository) {
        this.repository = repository;
    }

    public ChatParticipantResponse execute(ChatParticipantId id) {
        return repository.findById(id)
                .map(ChatParticipantResponse::from)
                .orElseThrow(() -> new ChatParticipantNotFoundApplicationException(id.value().toString()));
    }
}
