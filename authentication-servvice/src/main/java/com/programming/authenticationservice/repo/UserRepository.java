package com.programming.authenticationservice.repo;

import com.programming.authenticationservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT r.name " +
            "FROM authentication_service_user u " +
            "JOIN user_roles ur ON u.id = ur.user_id " +
            "JOIN authentication_service_role r ON ur.role_id = r.id " +
            "WHERE u.id = :id", nativeQuery = true)
    Optional<List<String>> findByIdWithRoles(@Param("id") Long id);
    User findByUsername(String username);
}
