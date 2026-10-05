package springboot.infrastructure.common.config;

import java.lang.reflect.Method;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * Hace transaccional el método execute(...) de todos los casos de uso SIN poner
 * anotaciones de Spring en la capa application (que sigue siendo Java puro).
 *
 * - Register/Update/Delete: transacción de escritura; rollback ante cualquier RuntimeException.
 * - Get/List: transacción de solo lectura.
 *
 * Así, por ejemplo, en Update el findById y el save ocurren en la misma transacción.
 */
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class UseCaseTransactionConfig {
    private static final String APPLICATION_PACKAGE = "springboot.application";

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TransactionInterceptor useCaseTransactionInterceptor() {
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionAttributeSource(new UseCaseTransactionAttributeSource());
        return interceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor useCaseTransactionAdvisor(TransactionInterceptor useCaseTransactionInterceptor) {
        return new DefaultPointcutAdvisor(new UseCaseExecutePointcut(), useCaseTransactionInterceptor);
    }

    private static boolean isUseCase(Class<?> type) {
        return type != null
                && type.getPackageName().startsWith(APPLICATION_PACKAGE)
                && type.getSimpleName().endsWith("UseCase");
    }

    private static final class UseCaseExecutePointcut extends StaticMethodMatcherPointcut {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return "execute".equals(method.getName()) && isUseCase(targetClass);
        }
    }

    private static final class UseCaseTransactionAttributeSource implements TransactionAttributeSource {
        @Override
        public TransactionAttribute getTransactionAttribute(Method method, Class<?> targetClass) {
            if (!"execute".equals(method.getName()) || !isUseCase(targetClass)) {
                return null;
            }
            DefaultTransactionAttribute attribute = new DefaultTransactionAttribute();
            String name = targetClass.getSimpleName();
            attribute.setReadOnly(name.startsWith("Get") || name.startsWith("List"));
            return attribute;
        }
    }
}
