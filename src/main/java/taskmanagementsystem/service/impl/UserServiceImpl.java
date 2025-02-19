package taskmanagementsystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taskmanagementsystem.dto.user.RoleDto;
import taskmanagementsystem.dto.user.UserRequest;
import taskmanagementsystem.dto.user.UserResponse;
import taskmanagementsystem.mappers.RoleMapper;
import taskmanagementsystem.mappers.UserMapper;
import taskmanagementsystem.model.exception.ResourceNotFoundException;
import taskmanagementsystem.model.user.Role;
import taskmanagementsystem.model.user.RoleEnum;
import taskmanagementsystem.model.user.User;
import taskmanagementsystem.repository.RoleRepository;
import taskmanagementsystem.repository.UserRepository;
import taskmanagementsystem.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMap;
    private final RoleMapper roleMap;
    private final RoleRepository roleRepository;


    @Override
    public UserResponse getById(final Long id) {

        return userRepository.findUserResponseById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    public User getUserById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    public UserResponse getByUsername(final String username) {

        return userRepository.findByUser(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    public User getByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    public UserResponse update(final UserRequest newUser) {
        User oldUser = userRepository.findById(newUser.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
        Role role = roleMap.toEntity(newUser.getRole().iterator().next());
        role.setName(RoleEnum.ROLE_USER);
        oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        oldUser.setEmail(newUser.getEmail());
        oldUser.setUsername(newUser.getUsername());
        oldUser.setRoles(Set.of(role));
        userRepository.save(oldUser);
        return userMap.userMapper(oldUser);

    }


//    @Override
//    public UserResponse create(final UserRequest userDto) {
//            log.info("Creating new user with username: {} and email: {}", userDto.getUsername(), userDto.getEmail());
//        User user = userMap.userDtoMapper(userDto);
//        Role role = roleMap.toEntity(userDto.getRole().iterator().next());
//
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        user.setRoles(Set.of(role));
//        return userMap.userMapper(userRepository.save(user));
//    }

    @Override
    public UserResponse create(final UserRequest userDto) {
        log.info("Creating new user with username: {} and email: {}", userDto.getUsername(), userDto.getEmail());

        User user = userMap.userDtoMapper(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        for (RoleDto roleDto : userDto.getRole()) {
            Role role = roleRepository.findByName(roleDto.name())
                    .orElseThrow(() -> new EntityNotFoundException("Role " + roleDto.name() + " not found"));
            roles.add(role);
        }

        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        log.info("User created successfully with id: {}", savedUser.getId());

        return userMap.userMapper(savedUser);
    }



    @Override
    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

}
