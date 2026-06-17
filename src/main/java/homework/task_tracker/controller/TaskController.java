package homework.task_tracker.controller;

import homework.task_tracker.Task;
import homework.task_tracker.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/task")
public class TaskController {

    public static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Task>> getAllTask() {
        log.info("Called getAllTask");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id) {
        log.info("Called getTaskById: id = {}", id);
        try {
            return ResponseEntity.ok(taskService.getTaskById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Task> createTask(
            @RequestBody Task task
    ){
        log.info("Called createTask");
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(task));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask (
            @PathVariable Long id,
            @RequestBody Task task
    ) {
        log.info("Called updateTask: id = {}", id);
        try {
            taskService.updateTask(id, task);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PutMapping("/{id}/trash")
    public ResponseEntity<Void> trashTask (
            @PathVariable Long id
    ) {
        log.info("Called trashTask: id = {}", id);
        try {
            taskService.moveTaskToTrash(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Void> changeTaskStatus(
            @PathVariable Long id
    ){
        log.info("Called changeTaskStatus with id = {}", id);
        try {
            taskService.changeTaskStatus(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


}
