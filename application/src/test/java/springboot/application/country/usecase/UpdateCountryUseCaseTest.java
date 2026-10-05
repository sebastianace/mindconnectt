package springboot.application.country.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import springboot.application.country.command.UpdateCountryCommand;
import springboot.application.country.dto.CountryResponse;
import springboot.application.country.exception.CountryNotFoundApplicationException;
import springboot.domain.common.event.DomainEvent;
import springboot.domain.common.exception.DomainValidationException;
import springboot.domain.country.event.CountryUpdatedEvent;
import springboot.domain.country.model.aggregate.Country;
import springboot.domain.country.model.valueobject.CountryId;
import springboot.domain.country.port.repository.CountryRepository;

class UpdateCountryUseCaseTest {
    private final List<DomainEvent> published = new ArrayList<>();

    @Test void shouldUpdateAndPublishUpdatedEvent() {
        Country country = Country.register("Colombia", "CO", "Colombia", true, "+57");
        country.clearDomainEvents();
        SingleCountryRepository repository = new SingleCountryRepository(country);

        CountryResponse response = new UpdateCountryUseCase(repository, published::addAll)
                .execute(new UpdateCountryCommand(country.id(), "Colombia", "COL", "República de Colombia", true, "+57"));

        assertEquals("COL", response.codeCountry());
        assertEquals("República de Colombia", response.description());
        assertEquals(1, published.size());
        assertInstanceOf(CountryUpdatedEvent.class, published.getFirst());
        assertTrue(country.domainEvents().isEmpty(), "events must be cleared after publishing");
    }

    @Test void shouldReturnNotFoundWithClearMessage() {
        CountryId id = CountryId.generate();

        CountryNotFoundApplicationException ex = assertThrows(CountryNotFoundApplicationException.class,
                () -> new UpdateCountryUseCase(new SingleCountryRepository(null), published::addAll)
                        .execute(new UpdateCountryCommand(id, "Colombia", "CO", "Colombia", true, "+57")));

        assertEquals("Country not found with id: " + id.value(), ex.getMessage());
        assertTrue(published.isEmpty());
    }

    @Test void shouldRejectBlankNameFromTheDomain() {
        Country country = Country.register("Colombia", "CO", "Colombia", true, "+57");

        assertThrows(DomainValidationException.class,
                () -> new UpdateCountryUseCase(new SingleCountryRepository(country), published::addAll)
                        .execute(new UpdateCountryCommand(country.id(), "  ", "CO", "Colombia", true, "+57")));
        assertTrue(published.isEmpty());
    }

    private static final class SingleCountryRepository implements CountryRepository {
        private final Country aggregate;
        private SingleCountryRepository(Country aggregate) { this.aggregate = aggregate; }
        @Override public Country save(Country value) { return value; }
        @Override public Optional<Country> findById(CountryId id) { return Optional.ofNullable(aggregate).filter(v -> v.id().equals(id)); }
        @Override public List<Country> findAll() { return aggregate == null ? List.of() : List.of(aggregate); }
        @Override public boolean existsById(CountryId id) { return aggregate != null && aggregate.id().equals(id); }
        @Override public boolean existsByCode(String code) { return false; }
        @Override public void delete(Country value) { }
    }
}
