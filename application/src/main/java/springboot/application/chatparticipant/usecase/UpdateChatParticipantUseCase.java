package springboot.application.chatparticipant.usecase;

import springboot.application.chatparticipant.command.UpdateChatParticipantCommand;
import springboot.application.chatparticipant.dto.ChatParticipantResponse;
import springboot.application.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;
import springboot.domain.chatparticipant.model.aggregate.ChatParticipant;
import springboot.domain.chatparticipant.port.repository.ChatParticipantRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.patient.port.repository.PatientRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.domain.sendertype.port.repository.SenderTypeRepository;

public class UpdateChatParticipantUseCase {
    private final ChatParticipantRepository repository;
    private final ChatConversationRepository chatConversationRepository;
    private final SenderTypeRepository senderTypeRepository;
    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateChatParticipantUseCase(
            ChatParticipantRepository repository,
            ChatConversationRepository chatConversationRepository,
            SenderTypeRepository senderTypeRepository,
            PatientRepository patientRepository,
            ProfessionalRepository professionalRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.chatConversationRepository = chatConversationRepository;
        this.senderTypeRepository = senderTypeRepository;
        this.patientRepository = patientRepository;
        this.professionalRepository = professionalRepository;
        this.eventPublisher = eventPublisher;
    }

    public ChatParticipantResponse execute(UpdateChatParticipantCommand command) {
        ChatParticipant aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ChatParticipantNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.conversationId(),
                command.participantTypeId(),
                command.patientId(),
                command.professionalId());
        ChatParticipant saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ChatParticipantResponse.from(saved);
    }

    private void validateReferences(UpdateChatParticipantCommand command) {
        if (!chatConversationRepository.existsById(command.conversationId())) {
            throw new ReferenceNotFoundApplicationException("ChatConversation", command.conversationId().value());
        }
        if (!senderTypeRepository.existsById(command.participantTypeId())) {
            throw new ReferenceNotFoundApplicationException("SenderType", command.participantTypeId().value());
        }
        if (command.patientId() != null && !patientRepository.existsById(command.patientId())) {
            throw new ReferenceNotFoundApplicationException("Patient", command.patientId().value());
        }
        if (command.professionalId() != null && !professionalRepository.existsById(command.professionalId())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.professionalId().value());
        }
    }
}
