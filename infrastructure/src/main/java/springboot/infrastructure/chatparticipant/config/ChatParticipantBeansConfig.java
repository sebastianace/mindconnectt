package springboot.infrastructure.chatparticipant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.chatparticipant.usecase.DeleteChatParticipantUseCase;
import springboot.application.chatparticipant.usecase.GetChatParticipantByIdUseCase;
import springboot.application.chatparticipant.usecase.ListChatParticipantUseCase;
import springboot.application.chatparticipant.usecase.RegisterChatParticipantUseCase;
import springboot.application.chatparticipant.usecase.UpdateChatParticipantUseCase;
import springboot.domain.chatconversation.port.repository.ChatConversationRepository;
import springboot.domain.chatparticipant.port.repository.ChatParticipantRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.patient.port.repository.PatientRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.domain.sendertype.port.repository.SenderTypeRepository;
import springboot.infrastructure.chatparticipant.adapters.out.persistence.mappers.ChatParticipantPersistenceMapper;
import springboot.infrastructure.chatparticipant.adapters.out.persistence.repositories.ChatParticipantJpaRepository;
import springboot.infrastructure.chatparticipant.adapters.out.persistence.repositories.ChatParticipantRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ChatParticipant: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ChatParticipantBeansConfig {

    @Bean
    public ChatParticipantPersistenceMapper chatParticipantPersistenceMapper() {
        return new ChatParticipantPersistenceMapper();
    }

    @Bean
    public ChatParticipantRepository chatParticipantRepository(ChatParticipantJpaRepository jpaRepository, ChatParticipantPersistenceMapper mapper) {
        return new ChatParticipantRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatParticipantUseCase registerChatParticipantUseCase(
            ChatParticipantRepository repository, ChatConversationRepository chatConversationRepository, SenderTypeRepository senderTypeRepository, PatientRepository patientRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new RegisterChatParticipantUseCase(repository, chatConversationRepository, senderTypeRepository, patientRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public GetChatParticipantByIdUseCase getChatParticipantByIdUseCase(ChatParticipantRepository repository) {
        return new GetChatParticipantByIdUseCase(repository);
    }

    @Bean
    public ListChatParticipantUseCase listChatParticipantUseCase(ChatParticipantRepository repository) {
        return new ListChatParticipantUseCase(repository);
    }

    @Bean
    public UpdateChatParticipantUseCase updateChatParticipantUseCase(
            ChatParticipantRepository repository, ChatConversationRepository chatConversationRepository, SenderTypeRepository senderTypeRepository, PatientRepository patientRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new UpdateChatParticipantUseCase(repository, chatConversationRepository, senderTypeRepository, patientRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public DeleteChatParticipantUseCase deleteChatParticipantUseCase(ChatParticipantRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteChatParticipantUseCase(repository, eventPublisher);
    }
}
