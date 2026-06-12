package homework.task_tracker.controller;

import homework.task_tracker.Task;
import homework.task_tracker.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    public static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable("id") Long id) {
        log.info("Called getTaskById: id = {}", id);
        return taskService.getTaskById(id);
    }

    @GetMapping("/")
    public List<Task> getAllTask() {
        log.info("Called getAllTask");
        return taskService.getAllTasks();
    }
}
