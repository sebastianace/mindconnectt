package springboot.infrastructure.relationshiptype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.application.relationshiptype.usecase.DeleteRelationshipTypeUseCase;
import springboot.application.relationshiptype.usecase.GetRelationshipTypeByIdUseCase;
import springboot.application.relationshiptype.usecase.ListRelationshipTypeUseCase;
import springboot.application.relationshiptype.usecase.RegisterRelationshipTypeUseCase;
import springboot.application.relationshiptype.usecase.UpdateRelationshipTypeUseCase;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.relationshiptype.port.repository.RelationshipTypeRepository;
import springboot.infrastructure.relationshiptype.adapters.out.persistence.mappers.RelationshipTypePersistenceMapper;
import springboot.infrastructure.relationshiptype.adapters.out.persistence.repositories.RelationshipTypeJpaRepository;
import springboot.infrastructure.relationshiptype.adapters.out.persistence.repositories.RelationshipTypeRepositoryAdapter;

/**
 * Ensambla explícitamente el bounded context RelationshipType: adaptador de persistencia y casos de uso.
 */
@Configuration
public class RelationshipTypeBeansConfig {

    @Bean
    public RelationshipTypePersistenceMapper relationshipTypePersistenceMapper() {
        return new RelationshipTypePersistenceMapper();
    }

    @Bean
    public RelationshipTypeRepository relationshipTypeRepository(RelationshipTypeJpaRepository jpaRepository, RelationshipTypePersistenceMapper mapper) {
        return new RelationshipTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterRelationshipTypeUseCase registerRelationshipTypeUseCase(
            RelationshipTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new RegisterRelationshipTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public GetRelationshipTypeByIdUseCase getRelationshipTypeByIdUseCase(RelationshipTypeRepository repository) {
        return new GetRelationshipTypeByIdUseCase(repository);
    }

    @Bean
    public ListRelationshipTypeUseCase listRelationshipTypeUseCase(RelationshipTypeRepository repository) {
        return new ListRelationshipTypeUseCase(repository);
    }

    @Bean
    public UpdateRelationshipTypeUseCase updateRelationshipTypeUseCase(
            RelationshipTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new UpdateRelationshipTypeUseCase(repository, eventPublisher);
    }

    @Bean
    public DeleteRelationshipTypeUseCase deleteRelationshipTypeUseCase(RelationshipTypeRepository repository, DomainEventPublisher eventPublisher) {
        return new DeleteRelationshipTypeUseCase(repository, eventPublisher);
    }
}
