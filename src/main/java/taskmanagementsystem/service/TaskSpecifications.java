package taskmanagementsystem.service;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import taskmanagementsystem.model.task.Task;
import taskmanagementsystem.model.user.RoleEnum;
import taskmanagementsystem.model.user.User;

public final class TaskSpecifications {


    private TaskSpecifications() {
    }

    public static Specification<Task> getTasksForUser(Authentication authentication) {
        return (root, query, criteriaBuilder) -> {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals(RoleEnum.ROLE_ADMIN.name()));

            if (isAdmin) {
                return criteriaBuilder.conjunction();
            } else {
                Join<Task, User> authorJoin = root.join("author", JoinType.INNER);
                return criteriaBuilder.equal(authorJoin.get("email"), authentication.getName());
            }
        };
    }
}
