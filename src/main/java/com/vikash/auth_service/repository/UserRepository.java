package com.vikash.auth_service.repository;

import com.vikash.auth_service.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("""
//            SELECT DISTINCT u
//            FROM User u
//            LEFT JOIN FETCH u.roles
//            WHERE u.email = :email
//            """)
    @EntityGraph(attributePaths = "roles")
    Optional<User> findUserByEmail(@Param("email") String email);

     boolean existsByEmail(String email);


}




