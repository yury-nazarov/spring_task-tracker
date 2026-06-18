package homework.task_tracker.DTO;

import homework.task_tracker.service.TaskPriority;
import homework.task_tracker.service.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Task (
        @Null
        Long id,
        @NotNull
        Long creatorId,
        Long assignedUserId,
        @NotNull
        TaskStatus status,
        @FutureOrPresent
        LocalDateTime createDateTime, // ISO-8601
        @FutureOrPresent
        LocalDate deadlineDate,
        @NotNull
        TaskPriority priority
) {}
