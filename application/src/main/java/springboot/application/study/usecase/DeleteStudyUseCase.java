package springboot.application.study.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.study.exception.StudyNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.study.event.StudyDeletedEvent;
import springboot.domain.study.model.aggregate.Study;
import springboot.domain.study.model.valueobject.StudyId;
import springboot.domain.study.port.repository.StudyRepository;

public class DeleteStudyUseCase {
    private final StudyRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteStudyUseCase(StudyRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public StudyDeletedEvent execute(StudyId id) {
        Study aggregate = repository.findById(id)
                .orElseThrow(() -> new StudyNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        StudyDeletedEvent event = new StudyDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
