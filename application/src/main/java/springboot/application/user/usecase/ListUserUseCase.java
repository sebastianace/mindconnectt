package springboot.application.user.usecase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import springboot.application.user.dto.UserResponse;
import springboot.domain.role.model.aggregate.Role;
import springboot.domain.role.model.valueobject.RoleId;
import springboot.domain.role.port.repository.RoleRepository;
import springboot.domain.user.model.aggregate.User;
import springboot.domain.user.port.repository.UserRepository;

public class ListUserUseCase {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ListUserUseCase(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserResponse> execute() {
        List<User> users = userRepository.findAll();
        Set<RoleId> allRoleIds = new HashSet<>();
        users.forEach(user -> allRoleIds.addAll(user.roleIds()));
        Map<RoleId, String> namesById = roleRepository.findAllById(allRoleIds).stream()
                .collect(Collectors.toMap(Role::id, Role::name, (a, b) -> a, HashMap::new));

        return users.stream()
                .map(user -> UserResponse.from(user, user.roleIds().stream()
                        .map(namesById::get)
                        .filter(Objects::nonNull)
                        .sorted()
                        .toList()))
                .toList();
    }
}
