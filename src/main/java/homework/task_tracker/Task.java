package homework.task_tracker;

import java.time.LocalDate;

public record Task (
        Long id,
        Long creatorId,
        Long assignedUserId,
        TaskStatus status,
        LocalDate createDateTime,
        LocalDate deadlineDate,
        TaskPriority priority
) {}
