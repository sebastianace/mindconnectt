package springboot.infrastructure.documenttype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.documenttype.usecase.DeleteDocumentTypeUseCase;
import springboot.application.documenttype.usecase.GetDocumentTypeByIdUseCase;
import springboot.application.documenttype.usecase.ListDocumentTypeUseCase;
import springboot.application.documenttype.usecase.RegisterDocumentTypeUseCase;
import springboot.application.documenttype.usecase.UpdateDocumentTypeUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.documenttype.port.repository.DocumentTypeRepository;
import springboot.infrastructure.documenttype.adapters.out.persistence.mappers.DocumentTypePersistenceMapper;
import springboot.infrastructure.documenttype.adapters.out.persistence.repositories.DocumentTypeJpaRepository;
import springboot.infrastructure.documenttype.adapters.out.persistence.repositories.DocumentTypeRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context DocumentType: adaptador de persistencia y casos de uso.
 */
@Configuration
public class DocumentTypeBeansConfig {

    @Bean
    public DocumentTypePersistenceMapper documentTypePersistenceMapper() {
        return new DocumentTypePersistenceMapper();
    }

    @Bean
    public DocumentTypeRepository documentTypeRepository(DocumentTypeJpaRepository jpaRepository, DocumentTypePersistenceMapper mapper) {
        return new DocumentTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterDocumentTypeUseCase registerDocumentTypeUseCase(
            DocumentTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterDocumentTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public GetDocumentTypeByIdUseCase getDocumentTypeByIdUseCase(DocumentTypeRepository repository) {
        return new GetDocumentTypeByIdUseCase(repository);
    }

    @Bean
    public ListDocumentTypeUseCase listDocumentTypeUseCase(DocumentTypeRepository repository) {
        return new ListDocumentTypeUseCase(repository);
    }

    @Bean
    public UpdateDocumentTypeUseCase updateDocumentTypeUseCase(
            DocumentTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateDocumentTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteDocumentTypeUseCase deleteDocumentTypeUseCase(DocumentTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteDocumentTypeUseCase(repository, eventPublisher);
    }
}
