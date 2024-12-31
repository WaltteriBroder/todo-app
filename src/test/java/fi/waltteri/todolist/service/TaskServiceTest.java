package fi.waltteri.todolist.service;

import fi.waltteri.todolist.model.Task;
import fi.waltteri.todolist.model.User;
import fi.waltteri.todolist.repository.TaskRepository;
import fi.waltteri.todolist.service.implementation.TaskServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private User user;
    private Task task;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setCompleted(false);
        task.setUser(user);
    }

    @Test
    void testGetTasks() {
        when(taskRepository.findByUser(user)).thenReturn(List.of(task));
        when(userService.getCurrentUser()).thenReturn(user);

        List<Task> tasks = taskService.getTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
    }

    @Test
    void testSaveTask() {
        String title = "New Task";
        String description = "New Task Description";
        when(userService.getCurrentUser()).thenReturn(user);

        taskService.saveTask(title, description);

        verify(taskRepository, times(1)).save(Mockito.any(Task.class));
    }

    @Test
    void testDeleteTask() {
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEditTaskTitle() {
        String newTitle = "Updated Task Title";
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.editTaskTitle(1L, newTitle);

        assertEquals(newTitle, task.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testEditTaskTitleThrowsEntityNotFoundException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> taskService.editTaskTitle(1L, "New Title"));
    }

    @Test
    void testEditTaskDescription() {
        String newDescription = "Updated Task Description";
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.editTaskDescription(1L, newDescription);

        assertEquals("Updated Task Description", task.getDescription());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testEditTaskDescriptionThrowsEntityNotFoundException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> taskService.editTaskDescription(1L, "New Description"));
    }

    @Test
    void testMarkAsCompleted() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.markAsCompleted(1L);

        assertTrue(task.isCompleted());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testMarkAsCompletedThrowsEntityNotFoundException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> taskService.markAsCompleted(1L));
    }
}