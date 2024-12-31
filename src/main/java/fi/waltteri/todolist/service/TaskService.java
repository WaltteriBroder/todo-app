package fi.waltteri.todolist.service;

import fi.waltteri.todolist.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> getTasks();

    void saveTask(String title, String description);

    void deleteTask(Long id);

    void editTaskTitle(Long id, String title);

    void editTaskDescription(Long id, String description);

    void markAsCompleted(Long id);
}
