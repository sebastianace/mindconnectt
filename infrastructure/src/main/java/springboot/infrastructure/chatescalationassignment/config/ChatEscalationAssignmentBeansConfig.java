package springboot.infrastructure.chatescalationassignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.chatescalationassignment.usecase.DeleteChatEscalationAssignmentUseCase;
import springboot.application.chatescalationassignment.usecase.GetChatEscalationAssignmentByIdUseCase;
import springboot.application.chatescalationassignment.usecase.ListChatEscalationAssignmentUseCase;
import springboot.application.chatescalationassignment.usecase.RegisterChatEscalationAssignmentUseCase;
import springboot.application.chatescalationassignment.usecase.UpdateChatEscalationAssignmentUseCase;
import springboot.domain.chatescalation.port.repository.ChatEscalationRepository;
import springboot.domain.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.professional.port.repository.ProfessionalRepository;
import springboot.infrastructure.chatescalationassignment.adapters.out.persistence.mappers.ChatEscalationAssignmentPersistenceMapper;
import springboot.infrastructure.chatescalationassignment.adapters.out.persistence.repositories.ChatEscalationAssignmentJpaRepository;
import springboot.infrastructure.chatescalationassignment.adapters.out.persistence.repositories.ChatEscalationAssignmentRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context ChatEscalationAssignment: adaptador de persistencia y casos de uso.
 */
@Configuration
public class ChatEscalationAssignmentBeansConfig {

    @Bean
    public ChatEscalationAssignmentPersistenceMapper chatEscalationAssignmentPersistenceMapper() {
        return new ChatEscalationAssignmentPersistenceMapper();
    }

    @Bean
    public ChatEscalationAssignmentRepository chatEscalationAssignmentRepository(ChatEscalationAssignmentJpaRepository jpaRepository, ChatEscalationAssignmentPersistenceMapper mapper) {
        return new ChatEscalationAssignmentRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatEscalationAssignmentUseCase registerChatEscalationAssignmentUseCase(
            ChatEscalationAssignmentRepository repository, ChatEscalationRepository chatEscalationRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new RegisterChatEscalationAssignmentUseCase(repository, chatEscalationRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public GetChatEscalationAssignmentByIdUseCase getChatEscalationAssignmentByIdUseCase(ChatEscalationAssignmentRepository repository) {
        return new GetChatEscalationAssignmentByIdUseCase(repository);
    }

    @Bean
    public ListChatEscalationAssignmentUseCase listChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository) {
        return new ListChatEscalationAssignmentUseCase(repository);
    }

    @Bean
    public UpdateChatEscalationAssignmentUseCase updateChatEscalationAssignmentUseCase(
            ChatEscalationAssignmentRepository repository, ChatEscalationRepository chatEscalationRepository, ProfessionalRepository professionalRepository, DomainEventPublisher eventPublisher) {
        return new UpdateChatEscalationAssignmentUseCase(repository, chatEscalationRepository, professionalRepository, eventPublisher);
    }

    @Bean
    public DeleteChatEscalationAssignmentUseCase deleteChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteChatEscalationAssignmentUseCase(repository, eventPublisher);
    }
}
