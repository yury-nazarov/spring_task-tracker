package homework.task_tracker.controller;

import homework.task_tracker.DTO.Task;
import homework.task_tracker.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    public static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    @Autowired
    public TaskController(
            TaskService taskService
    ) {
        this.taskService = taskService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Task>> getAllTask() {
        log.info("Called getAllTask");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(
            @PathVariable("id") Long id
    ) {
        log.info("Called getTaskById: id = {}", id);
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(
            @RequestBody @Valid Task task
    ){
        log.info("Called createTask");
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask (
            @PathVariable Long id,
            @RequestBody @Valid Task task
    ) {
        log.info("Called updateTask: id = {}", id);
        taskService.updateTask(id, task);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/{id}/trash")
    public ResponseEntity<Void> trashTask (
            @PathVariable Long id
    ) {
        log.info("Called trashTask: id = {}", id);
        taskService.moveTaskToTrash(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Void> changeTaskStatus(
            @PathVariable Long id
    ){
        log.info("Called changeTaskStatus with id = {}", id);
        taskService.changeTaskStatus(id);
        return ResponseEntity.ok().build();
    }
}
