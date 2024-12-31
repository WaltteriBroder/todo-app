package fi.waltteri.todolist.repository;

import fi.waltteri.todolist.model.Task;
import fi.waltteri.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);
}