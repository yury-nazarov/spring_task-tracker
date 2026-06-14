package homework.task_tracker.service;

import homework.task_tracker.Task;
import homework.task_tracker.TaskPriority;
import homework.task_tracker.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final HashMap<Long, Task> taskStorage;
    private final AtomicLong idCounter;

    public TaskService () {
        this.taskStorage = new HashMap<>();
        this.idCounter = new AtomicLong(); // потокобезопасный сторадж для счетчика
    }

    public List<Task> getAllTasks() {
        return taskStorage.values().stream().toList();
    }

    public Task getTaskById(Long id) {
        if (!taskStorage.containsKey(id)) {
            throw new NoSuchElementException("Not found task with id = " + id);
        }
        return taskStorage.get(id);
    }

    public Task createTask(Task task) {
        if (task.id() != null) {
            throw new IllegalArgumentException("Task cant contain id");
        }
        if (task.status() != null) {
            throw new IllegalArgumentException("Task cant contain status");
        }
        var newTask = new Task(
                idCounter.incrementAndGet(),
                task.creatorId(),
                task.assignedUserId(),
                TaskStatus.CREATED,
                task.createDateTime(),
                task.deadlineDate(),
                task.priority()
        );
        taskStorage.put(newTask.id(), newTask);
        return newTask;
    }

    public void updateTask(Long id, Task task) {
        // нельзя редактировать таски со статусом DONE
        // TODO: NullPointerException - если id не будет в БД
        if (!taskStorage.containsKey(id)) {
            throw new NoSuchElementException("Task id not found");
        }

        if (taskStorage.get(id).status() == TaskStatus.DONE
                && task.status() == TaskStatus.DONE) {
            throw new IllegalArgumentException("Task status cant be DONE");
        }

        var updatedTask = new Task(
                id,
                task.creatorId(),
                task.assignedUserId(),
                task.status(),
                task.createDateTime(),
                task.deadlineDate(),
                task.priority()
        );
        taskStorage.put(id, updatedTask);
        System.out.println(updatedTask);
        System.out.println(taskStorage);
        return;
    }

    public void deleteTask(Long id) {
        taskStorage.remove(id);
        return;
    }
}
