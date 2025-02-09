package java.taskmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.taskmanagementsystem.dto.user.UserResponse;
import java.taskmanagementsystem.model.user.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Param(value = "email") String email);

    @Query("SELECT new java.taskmanagementsystem.dto.user.UserResponse(u.username, u.email) FROM User u WHERE u.username = :username")
    Optional<UserResponse> findByUsername(@Param(value = "username") String username);

    @Query("SELECT new java.taskmanagementsystem.dto.user.UserResponse(u.username, u.email) FROM User u WHERE u.id = :id")
    Optional<UserResponse> findUserResponseById(@Param("id") Long id);


}
