package homework.task_tracker.DTO;

import homework.task_tracker.service.TaskPriority;
import homework.task_tracker.service.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Task (
        Long id,
        Long creatorId,
        Long assignedUserId,
        TaskStatus status,
        LocalDateTime createDateTime, // ISO-8601
        LocalDate deadlineDate,
        TaskPriority priority
) {}
