package homework.task_tracker.repository;

import homework.task_tracker.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    // derived query method (метод, который Spring Data построит по имени).
    //List<TaskEntity> findAllByAssignedUserId(Long assignedUserId);

    @Query("""
    SELECT COUNT(t)
    FROM TaskEntity t
    WHERE t.assignedUserId = :userId
    """)
    long countTasksByAssignedUserId(@Param("userId") Long userId);
}
