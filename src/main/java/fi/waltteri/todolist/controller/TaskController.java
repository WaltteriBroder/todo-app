package fi.waltteri.todolist.controller;

import fi.waltteri.todolist.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {

    private static final String REDIRECT_TO_MAIN_PAGE = "redirect:/";

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String main(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("tasks", taskService.getTasks());
        model.addAttribute("username", user.getUsername());
        return "index";
    }

    @PostMapping("/add")
    public String addTask(@RequestParam String title, @RequestParam String description) {
        taskService.saveTask(title, description);
        return REDIRECT_TO_MAIN_PAGE;
    }

    @PostMapping("/complete/{id}")
    public String markCompleted(@PathVariable Long id) {
        taskService.markAsCompleted(id);
        return REDIRECT_TO_MAIN_PAGE;
    }

    @PostMapping("/edit/title/{id}")
    public String editTaskTitle(@PathVariable Long id, @RequestParam String title) {
        taskService.editTaskTitle(id, title);
        return REDIRECT_TO_MAIN_PAGE;
    }

    @PostMapping("/edit/description/{id}")
    public String editTaskDescription(@PathVariable Long id, @RequestParam String description) {
        taskService.editTaskDescription(id, description);
        return REDIRECT_TO_MAIN_PAGE;
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return REDIRECT_TO_MAIN_PAGE;
    }
}
