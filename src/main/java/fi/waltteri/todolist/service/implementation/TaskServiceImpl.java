package fi.waltteri.todolist.service.implementation;


import fi.waltteri.todolist.model.Task;
import fi.waltteri.todolist.model.User;
import fi.waltteri.todolist.repository.TaskRepository;
import fi.waltteri.todolist.service.TaskService;
import fi.waltteri.todolist.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private static final String TASK_NOT_FOUND = "Task not found";

    private final UserService userService;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(UserService userService, TaskRepository taskRepository) {
        this.userService = userService;
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        User currentUser = userService.getCurrentUser();
        return taskRepository.findByUser(currentUser);
    }

    public void saveTask(String title, String description) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setCompleted(false);
        task.setUser(userService.getCurrentUser());
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void editTaskTitle(Long id, String title) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND));
        task.setTitle(title);
        taskRepository.save(task);
    }

    public void editTaskDescription(Long id, String description) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND));
        task.setDescription(description);
        taskRepository.save(task);
    }

    public void markAsCompleted(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND));
        task.setCompleted(true);
        taskRepository.save(task);
    }
}