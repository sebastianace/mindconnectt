package springboot.infrastructure.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.domain.role.port.repository.RoleRepository;
import springboot.domain.user.model.aggregate.User;
import springboot.domain.user.port.repository.UserRepository;

/**
 * Carga el usuario para Spring Security usando los PUERTOS del dominio (UserRepository y RoleRepository),
 * no el repositorio JPA directo como en el taller: así la seguridad sigue la arquitectura hexagonal.
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public JpaUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " no existe en el sistema!"));

        List<GrantedAuthority> authorities = roleRepository.findAllById(user.roleIds()).stream()
                .<GrantedAuthority>map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();

        return new org.springframework.security.core.userdetails.User(
                user.username(),
                user.passwordHash(),
                user.enabled(),
                true,   // la cuenta no ha expirado
                true,   // las credenciales no han expirado
                true,   // la cuenta no está bloqueada
                authorities);
    }
}
