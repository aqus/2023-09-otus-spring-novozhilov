package ru.otus.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.catalog.models.UserInfo;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUsername(String username);
}
