package ru.vladp.manager.frontend.security.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vladp.manager.frontend.security.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "tasks")
    Optional<User> findByEmail(String email);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "tasks")
    Optional<User> findById(int userId);
}
