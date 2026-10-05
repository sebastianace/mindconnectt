package springboot.application.chatescalationassignment.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.chatescalationassignment.exception.ChatEscalationAssignmentNotFoundApplicationException;
import springboot.domain.chatescalationassignment.event.ChatEscalationAssignmentDeletedEvent;
import springboot.domain.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
import springboot.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import springboot.domain.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteChatEscalationAssignmentUseCase {
    private final ChatEscalationAssignmentRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ChatEscalationAssignmentDeletedEvent execute(ChatEscalationAssignmentId id) {
        ChatEscalationAssignment aggregate = repository.findById(id)
                .orElseThrow(() -> new ChatEscalationAssignmentNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ChatEscalationAssignmentDeletedEvent event = new ChatEscalationAssignmentDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
