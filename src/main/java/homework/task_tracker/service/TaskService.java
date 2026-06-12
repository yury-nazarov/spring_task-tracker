package homework.task_tracker.service;

import homework.task_tracker.Task;
import homework.task_tracker.TaskPriority;
import homework.task_tracker.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class TaskService {
    public static final HashMap<Long, Task> taskStorage = new HashMap<>();

    static {
        taskStorage.put(1L, new Task(
                1L,
                100L,
                1000L,
                TaskStatus.CREATED,
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                TaskPriority.Low
        ));
        taskStorage.put(2L, new Task(
                2L,
                100L,
                1001L,
                TaskStatus.IN_PROGRESS,
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                TaskPriority.Medium
        ));
        taskStorage.put(3L, new Task(
                3L,
                1001L,
                1000L,
                TaskStatus.DONE,
                LocalDate.now().minusDays(20),
                LocalDate.now(),
                TaskPriority.High
        ));
    }

    public Task getTaskById(Long id) {
        if (!taskStorage.containsKey(id)) {
            throw new NoSuchElementException("Not found task with id = " + id);
        }
        return taskStorage.get(id);
    }

    public List<Task> getAllTasks() {
        return taskStorage.values().stream().toList();
    }
}
