package java.taskmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.taskmanagementsystem.dto.user.UserRequest;
import java.taskmanagementsystem.dto.user.UserResponse;
import java.taskmanagementsystem.mappers.UserMapper;
import java.taskmanagementsystem.model.exception.ResourceNotFoundException;
import java.taskmanagementsystem.model.user.Role;
import java.taskmanagementsystem.model.user.RoleEnum;
import java.taskmanagementsystem.model.user.User;
import java.taskmanagementsystem.repository.UserRepository;
import java.taskmanagementsystem.service.UserService;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMap;


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

        return userRepository.findByUsername(username)
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
        oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        oldUser.setEmail(newUser.getEmail());
        oldUser.setUsername(newUser.getUsername());
        oldUser.setRoles(Set.of(newUser.getRole().iterator().next()));
        userRepository.save(oldUser);
        return userMap.userMapper(oldUser);

    }


    @Override
    public UserResponse create(final UserRequest userDto) {
        User user = userMap.userDtoMapper(userDto);


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = new Role();
        role.setName(RoleEnum.ROLE_USER);
        user.setRoles(Set.of(role));
        return userMap.userMapper(userRepository.save(user));
    }


    @Override
    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

}
