package fi.waltteri.todolist.controller;

import fi.waltteri.todolist.exception.UserAlreadyExistAuthenticationException;
import fi.waltteri.todolist.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException ex,  Model model) {
        model.addAttribute("error", ex.getMessage());
        return "index";
    }

    @ExceptionHandler(UserAlreadyExistAuthenticationException.class)
    public String handleUserAlreadyExistAuthenticationException(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("error", "Käyttäjänimi on jo käytössä.");
        return "register";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUsernameNotFoundException(Model model) {
        model.addAttribute("error", "Käyttäjänimeä ei löytynyt");
        return "login";
    }
}