package homework.task_tracker.DTO;

import java.time.LocalDateTime;

public record ErrorResponseDTO (
    String message,
    String detailMessage,
    LocalDateTime errorTime
){}
