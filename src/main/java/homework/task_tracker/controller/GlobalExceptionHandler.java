package homework.task_tracker.controller;

import homework.task_tracker.DTO.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice // Централизованный перехватчик исключений в контроллерах
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handlerGenericException(
        Exception e
    ) {

        log.error("Handle: Exception: {}", e.getMessage());

        var responseDTO = new ErrorResponseDTO(
                "Internal Server Error",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlerEntityNotFound(
            EntityNotFoundException e
    ){
        log.error("Handle: EntityNotFound: {}", e.getMessage());

        var responseDTO = new ErrorResponseDTO(
                "Entity Not Found",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handlerIllegalArgument(
            IllegalArgumentException e
    ) {
        log.error("Handler: IllegalArgument: {}", e.getMessage());

        var responseDTO = new ErrorResponseDTO(
                "Illegal Argument",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handlerMethodArgumentNotValid(
            MethodArgumentNotValidException e
    ) {
        log.error("Handler: MethodArgumentNotValid: {}", e.getMessage());

        var responseDTO = new ErrorResponseDTO(
                "Method Argument Not Valid",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }
}
