package fi.waltteri.todolist.service;
import fi.waltteri.todolist.dto.UserDto;
import fi.waltteri.todolist.exception.UserAlreadyExistAuthenticationException;
import fi.waltteri.todolist.model.User;
import fi.waltteri.todolist.repository.UserRepository;
import fi.waltteri.todolist.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final String TEST_USER = "testuser";
    private final String PASSWORD = "password";
    private final String ENCODED_PASSWORD = "encodedpassword";

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setUsername(TEST_USER);
        userDto.setPassword(PASSWORD);

        user = new User();
        user.setUsername(TEST_USER);
        user.setPassword(ENCODED_PASSWORD);
    }

    @Test
    void testRegisterNewUserAccount_Success() {
        when(userRepository.findByUsername(TEST_USER)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerNewUserAccount(userDto);

        assertNotNull(registeredUser);
        assertEquals(TEST_USER, registeredUser.getUsername());
        assertEquals(ENCODED_PASSWORD, registeredUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterNewUserAccount_UserAlreadyExists() {
        when(userRepository.findByUsername(TEST_USER)).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistAuthenticationException.class, () -> userService.registerNewUserAccount(userDto));
    }
}