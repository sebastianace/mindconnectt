package springboot.application.mentalstatusexam.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.mentalstatusexam.exception.MentalStatusExamNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.mentalstatusexam.event.MentalStatusExamDeletedEvent;
import springboot.domain.mentalstatusexam.model.aggregate.MentalStatusExam;
import springboot.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
import springboot.domain.mentalstatusexam.port.repository.MentalStatusExamRepository;

public class DeleteMentalStatusExamUseCase {
    private final MentalStatusExamRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteMentalStatusExamUseCase(MentalStatusExamRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public MentalStatusExamDeletedEvent execute(MentalStatusExamId id) {
        MentalStatusExam aggregate = repository.findById(id)
                .orElseThrow(() -> new MentalStatusExamNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        MentalStatusExamDeletedEvent event = new MentalStatusExamDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
