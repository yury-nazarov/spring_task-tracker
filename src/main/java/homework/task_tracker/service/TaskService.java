package homework.task_tracker.service;

import homework.task_tracker.DTO.Task;
import homework.task_tracker.entity.TaskEntity;
import homework.task_tracker.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService (TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        // Получаем из БД данные в виде Entity объектов
        List<TaskEntity> items = repository.findAll();
        // Конвертируем в DTO
        List<Task> tasks = items.stream().map(item -> toDTO(item)).toList();
        return tasks;
    }

    public Task getTaskById(Long id) {
        TaskEntity task = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found task with id = " + id));
        return toDTO(task);
    }

    public Task createTask(Task task) {
        if (task.id() != null) {
            throw new IllegalArgumentException("Task cant contain id");
        }
        if (task.status() != null) {
            throw new IllegalArgumentException("Task cant contain status");
        }

        var newTask = new TaskEntity(
                null,
                task.creatorId(),
                task.assignedUserId(),
                TaskStatus.CREATED,
                task.createDateTime(),
                task.deadlineDate(),
                task.priority()
        );

        var taskEntity = repository.save(newTask);
        return toDTO(taskEntity);
    }

    public void updateTask(Long id, Task task) {
        TaskEntity taskEntity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task id not found" + id));

        // нельзя редактировать таски со статусом DONE
        if (taskEntity.getStatus() == TaskStatus.DONE
                && task.status() == TaskStatus.DONE) {
            throw new IllegalArgumentException("Task status cant be DONE");
        }
        var updatedTask = new TaskEntity(
                taskEntity.getId(), // Осталвяем оригинальный id
                // остальные поля подставляем из запроса
                task.creatorId(),
                task.assignedUserId(),
                task.status(),
                task.createDateTime(),
                task.deadlineDate(),
                task.priority()
        );
        // По id в Entity Hibernate самостоятельно определит запись, которую нужно обновить
        repository.save(updatedTask);
    }

    @Transactional // выполняет метод, как одну атомарную операцию в БД
    public void moveTaskToTrash(Long id) {
        TaskEntity taskEntity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task id not found" + id));
        taskEntity.setStatus(TaskStatus.TRASHED);
        // repository.save(taskEntity); // Благодоря Transactional save() не нужен
    }

    // TBD: Вероятно это в будущем уедет в маппер
    private Task toDTO(TaskEntity item) {
        return new Task(
                item.getId(),
                item.getCreatorId(),
                item.getAssignedUserId(),
                item.getStatus(),
                item.getCreateDateTime(),
                item.getDeadlineDate(),
                item.getPriority()
        );
    }

    @Transactional
    public void changeTaskStatus(Long id) {
        TaskEntity taskEntity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found. id=" + id));

        if (taskEntity.getAssignedUserId() == null) {
            throw new IllegalArgumentException("Field getAssignedUserId must be set");
        }

        Long tasksByAssignUserId = repository.countTasksByAssignedUserId(taskEntity.getAssignedUserId());
        if (tasksByAssignUserId >= 4) {
            throw new IllegalArgumentException("To many task count per user. Task count = " + tasksByAssignUserId);
        }
        taskEntity.setStatus(TaskStatus.IN_PROGRESS);
    }
}

