package springboot.application.stateregion.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.stateregion.command.RegisterStateRegionCommand;
import springboot.application.stateregion.dto.StateRegionResponse;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.country.model.aggregate.Country;
import springboot.domain.country.model.valueobject.CountryId;
import springboot.domain.country.port.repository.CountryRepository;
import springboot.domain.stateregion.event.StateRegionRegisteredEvent;
import springboot.domain.stateregion.model.aggregate.StateRegion;
import springboot.domain.stateregion.model.valueobject.StateRegionId;
import springboot.domain.stateregion.port.repository.StateRegionRepository;

class RegisterStateRegionUseCaseTest {
    private final InMemoryStateRegions regions = new InMemoryStateRegions();
    private final InMemoryCountries countries = new InMemoryCountries();
    private final List<DomainEvent> published = new ArrayList<>();
    private final RegisterStateRegionUseCase useCase =
            new RegisterStateRegionUseCase(regions, countries, published::addAll);

    @Test void shouldRegisterRegionAndPublishEvent() {
        Country colombia = countries.save(Country.register("Colombia", "CO", "Colombia", true, "+57"));

        StateRegionResponse response = useCase.execute(command("SAN", colombia.id()));

        assertEquals("Santander", response.nameRegion());
        assertEquals(colombia.id().value(), response.countryId());
        assertEquals(1, regions.data.size());
        assertEquals(1, published.size());
        assertInstanceOf(StateRegionRegisteredEvent.class, published.getFirst());
    }

    @Test void shouldRejectUnknownCountry() {
        CountryId unknown = CountryId.generate();

        ReferenceNotFoundApplicationException ex = assertThrows(ReferenceNotFoundApplicationException.class,
                () -> useCase.execute(command("SAN", unknown)));

        assertTrue(ex.getMessage().contains(unknown.value().toString()));
        assertTrue(regions.data.isEmpty());
        assertTrue(published.isEmpty());
    }

    @Test void shouldRejectDuplicatedCodeInsideTheSameCountry() {
        Country colombia = countries.save(Country.register("Colombia", "CO", "Colombia", true, "+57"));
        useCase.execute(command("SAN", colombia.id()));

        assertThrows(DuplicateResourceApplicationException.class, () -> useCase.execute(command("SAN", colombia.id())));
        assertEquals(1, regions.data.size());
    }

    private static RegisterStateRegionCommand command(String code, CountryId countryId) {
        return new RegisterStateRegionCommand("Santander", code, "Departamento de Santander", true, countryId);
    }

    private static final class InMemoryStateRegions implements StateRegionRepository {
        private final Map<StateRegionId, StateRegion> data = new LinkedHashMap<>();
        @Override public StateRegion save(StateRegion value) { data.put(value.id(), value); return value; }
        @Override public Optional<StateRegion> findById(StateRegionId id) { return Optional.ofNullable(data.get(id)); }
        @Override public List<StateRegion> findAll() { return List.copyOf(data.values()); }
        @Override public boolean existsById(StateRegionId id) { return data.containsKey(id); }
        @Override public boolean existsByCountryIdAndCode(CountryId countryId, String code) {
            return data.values().stream().anyMatch(r -> r.countryId().equals(countryId) && r.codeRegion().equals(code));
        }
        @Override public void delete(StateRegion value) { data.remove(value.id()); }
    }

    private static final class InMemoryCountries implements CountryRepository {
        private final Map<CountryId, Country> data = new LinkedHashMap<>();
        @Override public Country save(Country value) { data.put(value.id(), value); return value; }
        @Override public Optional<Country> findById(CountryId id) { return Optional.ofNullable(data.get(id)); }
        @Override public List<Country> findAll() { return List.copyOf(data.values()); }
        @Override public boolean existsById(CountryId id) { return data.containsKey(id); }
        @Override public boolean existsByCode(String code) {
            return data.values().stream().anyMatch(c -> c.codeCountry().equals(code));
        }
        @Override public void delete(Country value) { data.remove(value.id()); }
    }
}
