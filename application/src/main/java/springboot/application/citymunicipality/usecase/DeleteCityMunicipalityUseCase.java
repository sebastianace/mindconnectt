package springboot.application.citymunicipality.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import springboot.domain.citymunicipality.event.CityMunicipalityDeletedEvent;
import springboot.domain.citymunicipality.model.aggregate.CityMunicipality;
import springboot.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class DeleteCityMunicipalityUseCase {
    private final CityMunicipalityRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteCityMunicipalityUseCase(CityMunicipalityRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public CityMunicipalityDeletedEvent execute(CityMunicipalityId id) {
        CityMunicipality aggregate = repository.findById(id)
                .orElseThrow(() -> new CityMunicipalityNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        CityMunicipalityDeletedEvent event = new CityMunicipalityDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
