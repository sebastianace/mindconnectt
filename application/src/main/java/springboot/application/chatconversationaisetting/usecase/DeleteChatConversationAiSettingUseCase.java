package springboot.application.chatconversationaisetting.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.chatconversationaisetting.exception.ChatConversationAiSettingNotFoundApplicationException;
import springboot.domain.chatconversationaisetting.event.ChatConversationAiSettingDeletedEvent;
import springboot.domain.chatconversationaisetting.model.aggregate.ChatConversationAiSetting;
import springboot.domain.chatconversationaisetting.model.valueobject.ChatConversationAiSettingId;
import springboot.domain.chatconversationaisetting.port.repository.ChatConversationAiSettingRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteChatConversationAiSettingUseCase {
    private final ChatConversationAiSettingRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteChatConversationAiSettingUseCase(ChatConversationAiSettingRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ChatConversationAiSettingDeletedEvent execute(ChatConversationAiSettingId id) {
        ChatConversationAiSetting aggregate = repository.findById(id)
                .orElseThrow(() -> new ChatConversationAiSettingNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ChatConversationAiSettingDeletedEvent event = new ChatConversationAiSettingDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
