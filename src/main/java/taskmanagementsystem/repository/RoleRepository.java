package taskmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taskmanagementsystem.model.user.Role;
import taskmanagementsystem.model.user.RoleEnum;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}