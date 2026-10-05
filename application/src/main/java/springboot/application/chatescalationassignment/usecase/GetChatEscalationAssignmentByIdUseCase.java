package springboot.application.chatescalationassignment.usecase;

import springboot.application.chatescalationassignment.dto.ChatEscalationAssignmentResponse;
import springboot.application.chatescalationassignment.exception.ChatEscalationAssignmentNotFoundApplicationException;
import springboot.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import springboot.domain.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;

public class GetChatEscalationAssignmentByIdUseCase {
    private final ChatEscalationAssignmentRepository repository;

    public GetChatEscalationAssignmentByIdUseCase(ChatEscalationAssignmentRepository repository) {
        this.repository = repository;
    }

    public ChatEscalationAssignmentResponse execute(ChatEscalationAssignmentId id) {
        return repository.findById(id)
                .map(ChatEscalationAssignmentResponse::from)
                .orElseThrow(() -> new ChatEscalationAssignmentNotFoundApplicationException(id.value().toString()));
    }
}
