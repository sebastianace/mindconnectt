package springboot.application.study.usecase;

import springboot.application.study.command.UpdateStudyCommand;
import springboot.application.study.dto.StudyResponse;
import springboot.application.study.exception.StudyNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.study.model.aggregate.Study;
import springboot.domain.study.port.repository.StudyRepository;

public class UpdateStudyUseCase {
    private final StudyRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateStudyUseCase(
            StudyRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public StudyResponse execute(UpdateStudyCommand command) {
        Study aggregate = repository.findById(command.id())
                .orElseThrow(() -> new StudyNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.name());
        Study saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return StudyResponse.from(saved);
    }
}
