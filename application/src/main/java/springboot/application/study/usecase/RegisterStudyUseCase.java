package springboot.application.study.usecase;

import springboot.application.study.command.RegisterStudyCommand;
import springboot.application.study.dto.StudyResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.study.model.aggregate.Study;
import springboot.domain.study.port.repository.StudyRepository;

public class RegisterStudyUseCase {
    private final StudyRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterStudyUseCase(
            StudyRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public StudyResponse execute(RegisterStudyCommand command) {
        Study aggregate = Study.register(
                command.name());
        Study saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return StudyResponse.from(saved);
    }
}
