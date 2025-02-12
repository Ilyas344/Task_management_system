package taskmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import taskmanagementsystem.dto.user.UserResponse;
import taskmanagementsystem.model.user.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Param(value = "email") String email);

    @Query("SELECT new taskmanagementsystem.dto.user.UserResponse(u.username, u.email) FROM User u WHERE u.username = :username")
    Optional<UserResponse> findByUser(@Param(value = "username") String username);

    @Query("SELECT new taskmanagementsystem.dto.user.UserResponse(u.username, u.email) FROM User u WHERE u.id = :id")
    Optional<UserResponse> findUserResponseById(@Param("id") Long id);

    Optional<User> findByUsername(String username);



}
