package springboot.domain.user.port.security;

/**
 * Puerto de salida: convierte una contraseña en texto plano en un hash seguro.
 * El dominio y la aplicación solo conocen esta interfaz; el algoritmo (BCrypt)
 * vive en la infraestructura.
 */
@FunctionalInterface
public interface PasswordHasher {
    String hash(String rawPassword);
}
