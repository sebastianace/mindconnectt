package springboot.application.chatparticipant.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import springboot.domain.chatparticipant.event.ChatParticipantDeletedEvent;
import springboot.domain.chatparticipant.model.aggregate.ChatParticipant;
import springboot.domain.chatparticipant.model.valueobject.ChatParticipantId;
import springboot.domain.chatparticipant.port.repository.ChatParticipantRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteChatParticipantUseCase {
    private final ChatParticipantRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteChatParticipantUseCase(ChatParticipantRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ChatParticipantDeletedEvent execute(ChatParticipantId id) {
        ChatParticipant aggregate = repository.findById(id)
                .orElseThrow(() -> new ChatParticipantNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ChatParticipantDeletedEvent event = new ChatParticipantDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
