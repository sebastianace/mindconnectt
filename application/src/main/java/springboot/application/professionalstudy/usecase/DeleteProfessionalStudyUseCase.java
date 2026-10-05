package springboot.application.professionalstudy.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.professionalstudy.event.ProfessionalStudyDeletedEvent;
import springboot.domain.professionalstudy.model.aggregate.ProfessionalStudy;
import springboot.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
import springboot.domain.professionalstudy.port.repository.ProfessionalStudyRepository;

public class DeleteProfessionalStudyUseCase {
    private final ProfessionalStudyRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteProfessionalStudyUseCase(ProfessionalStudyRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ProfessionalStudyDeletedEvent execute(ProfessionalStudyId id) {
        ProfessionalStudy aggregate = repository.findById(id)
                .orElseThrow(() -> new ProfessionalStudyNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ProfessionalStudyDeletedEvent event = new ProfessionalStudyDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
