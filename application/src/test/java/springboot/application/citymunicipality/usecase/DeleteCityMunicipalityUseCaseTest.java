package springboot.application.citymunicipality.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import springboot.domain.citymunicipality.event.CityMunicipalityDeletedEvent;
import springboot.domain.citymunicipality.model.aggregate.CityMunicipality;
import springboot.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.stateregion.model.valueobject.StateRegionId;

class DeleteCityMunicipalityUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldDeleteExistingAggregate() {
        CityMunicipality aggregate = CityMunicipality.register(
                "Bogota",
                "BOG",
                "Capital district",
                true,
                StateRegionId.generate());
        FakeRepository repository = new FakeRepository(aggregate);
        CityMunicipalityDeletedEvent event = new DeleteCityMunicipalityUseCase(repository, published::addAll).execute(aggregate.id());
        assertSame(aggregate, repository.deletedAggregate()); assertEquals(aggregate.id(), event.id()); assertNotNull(event.occurredOn());
        assertEquals(List.of(event), published);
    }
    @Test void shouldRejectDeletionWhenAggregateDoesNotExist() {
        FakeRepository repository = new FakeRepository(null);
        assertThrows(CityMunicipalityNotFoundApplicationException.class,
                () -> new DeleteCityMunicipalityUseCase(repository, published::addAll).execute(CityMunicipalityId.generate()));
        assertNull(repository.deletedAggregate());
        assertTrue(published.isEmpty());
    }
    private static final class FakeRepository implements CityMunicipalityRepository {
        private final CityMunicipality aggregate; private CityMunicipality deletedAggregate;
        private FakeRepository(CityMunicipality aggregate) { this.aggregate = aggregate; }
        @Override public CityMunicipality save(CityMunicipality value) { return value; }
        @Override public Optional<CityMunicipality> findById(CityMunicipalityId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<CityMunicipality> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }
        @Override public boolean existsByRegionIdAndCode(StateRegionId regionId, String code) { return false; }
        @Override public boolean existsById(CityMunicipalityId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public void delete(CityMunicipality value) { deletedAggregate = value; }
        private CityMunicipality deletedAggregate() { return deletedAggregate; }
    }
}
