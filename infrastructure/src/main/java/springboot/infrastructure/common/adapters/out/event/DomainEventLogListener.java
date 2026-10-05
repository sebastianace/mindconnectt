package springboot.infrastructure.common.adapters.out.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.event.TransactionalEventListener;

import springboot.domain.common.event.DomainEvent;

/**
 * Escucha todos los eventos de dominio y los registra en el log una vez la transacción
 * se confirma (si hubo rollback, el evento no se registra porque nunca ocurrió).
 */
public class DomainEventLogListener {
    private static final Logger log = LoggerFactory.getLogger(DomainEventLogListener.class);

    @TransactionalEventListener(fallbackExecution = true)
    public void on(DomainEvent event) {
        log.info("Domain event: {}", event);
    }
}
