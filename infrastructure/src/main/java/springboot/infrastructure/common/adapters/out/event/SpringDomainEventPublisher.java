package springboot.infrastructure.common.adapters.out.event;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import springboot.domain.common.event.DomainEvent;
import springboot.domain.common.port.DomainEventPublisher;

/**
 * Adaptador de salida: implementa el puerto DomainEventPublisher usando el bus de eventos de Spring.
 */
public class SpringDomainEventPublisher implements DomainEventPublisher {
    private final ApplicationEventPublisher publisher;

    public SpringDomainEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(List<DomainEvent> events) {
        events.forEach(publisher::publishEvent);
    }
}
