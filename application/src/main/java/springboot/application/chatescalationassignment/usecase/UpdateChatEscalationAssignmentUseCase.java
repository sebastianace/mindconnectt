package springboot.application.chatescalationassignment.usecase;

import springboot.application.chatescalationassignment.command.UpdateChatEscalationAssignmentCommand;
import springboot.application.chatescalationassignment.dto.ChatEscalationAssignmentResponse;
import springboot.application.chatescalationassignment.exception.ChatEscalationAssignmentNotFoundApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.chatescalation.port.repository.ChatEscalationRepository;
import springboot.domain.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
import springboot.domain.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.professional.port.repository.ProfessionalRepository;

public class UpdateChatEscalationAssignmentUseCase {
    private final ChatEscalationAssignmentRepository repository;
    private final ChatEscalationRepository chatEscalationRepository;
    private final ProfessionalRepository professionalRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateChatEscalationAssignmentUseCase(
            ChatEscalationAssignmentRepository repository,
            ChatEscalationRepository chatEscalationRepository,
            ProfessionalRepository professionalRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.chatEscalationRepository = chatEscalationRepository;
        this.professionalRepository = professionalRepository;
        this.eventPublisher = eventPublisher;
    }

    public ChatEscalationAssignmentResponse execute(UpdateChatEscalationAssignmentCommand command) {
        ChatEscalationAssignment aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ChatEscalationAssignmentNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.escalationId(),
                command.professionalId(),
                command.assignedAt());
        ChatEscalationAssignment saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ChatEscalationAssignmentResponse.from(saved);
    }

    private void validateReferences(UpdateChatEscalationAssignmentCommand command) {
        if (!chatEscalationRepository.existsById(command.escalationId())) {
            throw new ReferenceNotFoundApplicationException("ChatEscalation", command.escalationId().value());
        }
        if (!professionalRepository.existsById(command.professionalId())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.professionalId().value());
        }
    }
}
