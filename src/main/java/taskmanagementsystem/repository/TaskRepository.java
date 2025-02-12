package taskmanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import taskmanagementsystem.model.task.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByAuthorUsername(String currentUser, Pageable pageable);
}