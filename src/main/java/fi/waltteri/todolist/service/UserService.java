package fi.waltteri.todolist.service;

import fi.waltteri.todolist.dto.UserDto;
import fi.waltteri.todolist.exception.UserAlreadyExistAuthenticationException;
import fi.waltteri.todolist.model.User;

public interface UserService {

    User getCurrentUser();

    User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistAuthenticationException;
}
