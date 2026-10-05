package springboot.application.chatconversation.usecase;

import springboot.application.chatconversation.dto.ChatConversationResponse;
import springboot.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import springboot.domain.chatconversation.model.valueobject.ChatConversationId;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;

public class GetChatConversationByIdUseCase {
    private final ChatConversationRepository repository;

    public GetChatConversationByIdUseCase(ChatConversationRepository repository) {
        this.repository = repository;
    }

    public ChatConversationResponse execute(ChatConversationId id) {
        return repository.findById(id)
                .map(ChatConversationResponse::from)
                .orElseThrow(() -> new ChatConversationNotFoundApplicationException(id.value().toString()));
    }
}
