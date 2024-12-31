package fi.waltteri.todolist.service.implementation;

import fi.waltteri.todolist.dto.UserDto;
import fi.waltteri.todolist.exception.UserAlreadyExistAuthenticationException;
import fi.waltteri.todolist.model.User;
import fi.waltteri.todolist.repository.UserRepository;
import fi.waltteri.todolist.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username" + user.getUsername() + " not found."));
    }

    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistAuthenticationException {
        checkIfUsernameExists(userDto.getUsername());
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    private void checkIfUsernameExists(String username) {
        userRepository.findByUsername(username)
                .ifPresent(existingUser -> {
                    throw new UserAlreadyExistAuthenticationException(
                            "Username is already taken: " + username);
                });
    }
}
