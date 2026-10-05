package springboot.application.chatconversationaisetting.usecase;

import springboot.application.chatconversationaisetting.dto.ChatConversationAiSettingResponse;
import springboot.application.chatconversationaisetting.exception.ChatConversationAiSettingNotFoundApplicationException;
import springboot.domain.chatconversationaisetting.model.valueobject.ChatConversationAiSettingId;
import springboot.domain.chatconversationaisetting.port.repository.ChatConversationAiSettingRepository;

public class GetChatConversationAiSettingByIdUseCase {
    private final ChatConversationAiSettingRepository repository;

    public GetChatConversationAiSettingByIdUseCase(ChatConversationAiSettingRepository repository) {
        this.repository = repository;
    }

    public ChatConversationAiSettingResponse execute(ChatConversationAiSettingId id) {
        return repository.findById(id)
                .map(ChatConversationAiSettingResponse::from)
                .orElseThrow(() -> new ChatConversationAiSettingNotFoundApplicationException(id.value().toString()));
    }
}
