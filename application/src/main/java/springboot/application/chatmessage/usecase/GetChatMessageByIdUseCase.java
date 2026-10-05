package springboot.application.chatmessage.usecase;

import springboot.application.chatmessage.dto.ChatMessageResponse;
import springboot.application.chatmessage.exception.ChatMessageNotFoundApplicationException;
import springboot.domain.chatmessage.model.valueobject.ChatMessageId;
import springboot.domain.chatmessage.port.repository.ChatMessageRepository;

public class GetChatMessageByIdUseCase {
    private final ChatMessageRepository repository;

    public GetChatMessageByIdUseCase(ChatMessageRepository repository) {
        this.repository = repository;
    }

    public ChatMessageResponse execute(ChatMessageId id) {
        return repository.findById(id)
                .map(ChatMessageResponse::from)
                .orElseThrow(() -> new ChatMessageNotFoundApplicationException(id.value().toString()));
    }
}
