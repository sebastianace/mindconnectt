package springboot.infrastructure.common.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springboot.domain.common.port.DomainEventPublisher;
import springboot.infrastructure.common.adapters.out.event.DomainEventLogListener;
import springboot.infrastructure.common.adapters.out.event.SpringDomainEventPublisher;

/**
 * Beans compartidos por todos los bounded contexts.
 */
@Configuration
public class CommonBeansConfig {

    @Bean
    public DomainEventPublisher domainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new SpringDomainEventPublisher(applicationEventPublisher);
    }

    @Bean
    public DomainEventLogListener domainEventLogListener() {
        return new DomainEventLogListener();
    }
}
