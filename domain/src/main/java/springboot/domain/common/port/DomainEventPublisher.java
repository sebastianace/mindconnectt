package springboot.domain.common.port;

import java.util.List;

import springboot.domain.common.event.DomainEvent;

/**
 * Puerto de salida para publicar los eventos de dominio.
 * El dominio y la aplicación solo conocen esta interfaz; la implementación
 * concreta (Spring) vive en la capa de infraestructura.
 */
@FunctionalInterface
public interface DomainEventPublisher {
    void publish(List<DomainEvent> events);
}
