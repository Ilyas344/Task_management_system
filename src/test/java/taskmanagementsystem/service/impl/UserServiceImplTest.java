package taskmanagementsystem.service.impl;



import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import taskmanagementsystem.config.TestConfig;
import taskmanagementsystem.dto.user.UserRequest;
import taskmanagementsystem.dto.user.UserResponse;
import taskmanagementsystem.mappers.UserMapper;
import taskmanagementsystem.model.user.Role;
import taskmanagementsystem.model.exception.ResourceNotFoundException;
import taskmanagementsystem.model.user.RoleEnum;
import taskmanagementsystem.model.user.User;
import taskmanagementsystem.repository.UserRepository;
import taskmanagementsystem.service.UserService;


import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserService userService;


    @Test
    public void testGetById_Success() throws Exception {
        var id = 1L;
        var username = "user";
        var email = "user@example.com";
        var user = new User(username, email, "password", Set.of(new Role(RoleEnum.ROLE_USER)));
        UserResponse expectedUserDto = new UserResponse( username, email);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.userMapper(user)).thenReturn(expectedUserDto);

        var actualUserDto = userService.getById(id);

        assertNotNull(actualUserDto);
        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;
        when(userRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> userService.getById(id));
        verify(userRepository).findById(id);
    }

    @Test
    public void testGetUserById_Success() throws Exception {
        Long id = 1L;
        String username = "user";
        String email = "user@example.com";
        User user = new User(username, email, "password", Set.of(new Role(RoleEnum.ROLE_USER)));

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User actualUser = userService.getUserById(id);

        assertNotNull(actualUser);
        assertEquals(user, actualUser);
    }

    @Test
    void getByUsername() {
        String username = "username@gmail.com";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        var testUser = userService.getByUsername(username);
        verify(userRepository).findByUsername(username);
        assertEquals(testUser.getUsername(), user.getUsername());

    }

    @Test
    void getByNotExistingUsername() {
        String username = "username@gmail.com";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> userService.getByUsername(username));
        verify(userRepository).findByUsername(username);
    }


    @Test
    public void testUpdate() throws Exception {
        Long id = 1L;
        String username = "user";
        String email = "user@example.com";
        String newPassword = "newPassword";
        String encodedPassword = "encodedPassword";

        var userResponse = new UserResponse(username, email);
        User existingUser = new User(username, email, "oldPassword", Set.of(new Role(RoleEnum.ROLE_USER)));
        var userRequest = new UserRequest(username, email,"newPassword",null);

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);
        when(userMapper.updateUserFromDto(userRequest, existingUser)).thenReturn(existingUser);
        when(userMapper.userMapper(existingUser)).thenReturn(userResponse);

        var actualUserDto = userService.update(1L,userRequest);

        assertNotNull(actualUserDto);
        assertEquals(userResponse, actualUserDto);
        verify(userRepository).save(existingUser);
    }

    @Test

    public void testUpdate_NotFound() throws Exception {
        Long id = 1L;
        String username = "user";
        String email = "user@example.com";
        String newPassword = "newPassword";

        var currentUserRequest = new UserRequest(username, email, newPassword, null);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.update(id, currentUserRequest));
    }


    @Test
    public void testCreate_Success() throws Exception {
        String username = "user";
        String email = "user@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        var userRequest = new UserRequest(username, email, password, null);
        User expectedUser = new User(username, email, encodedPassword, Set.of(new Role(RoleEnum.ROLE_USER)));
        var userResponse = new UserResponse(username, email);
        when(userMapper.userDtoMapper(userRequest)).thenReturn(expectedUser);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        when(userMapper.userMapper(expectedUser)).thenReturn(userResponse);

        var actualUserDto = userService.create(userRequest);

        assertNotNull(actualUserDto);
        assertEquals(userRequest.getUsername(), actualUserDto.getUsername());
        assertEquals(userRequest.getEmail(), actualUserDto.getEmail());
        verify(userRepository).save(expectedUser);
    }


    @Test
    void createWithExistingUsername() {
        String username = "username@gmail.com";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        var userDto = UserRequest.builder()
                .username("username")
                .password("password")
                .build();
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(new User()));
        when(passwordEncoder.encode(password))
                .thenReturn("encodedPassword");
        assertThrows(NullPointerException.class,
                () -> userService.create(userDto));
        verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    void createWithDifferentPasswords() {
        var userDto = UserRequest.builder()
                .username("username")
                .password("password")
                .build();
        String username = "username@gmail.com";
        String password = "password1";
        String passwordConfirmation = "password2";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        assertThrows(NullPointerException.class,
                () -> userService.create(userDto));
        verify(userRepository, Mockito.never()).save(user);
    }


    @Test
    void delete() {
        Long id = 1L;
        userService.delete(id);
        verify(userRepository).deleteById(id);
    }

}
