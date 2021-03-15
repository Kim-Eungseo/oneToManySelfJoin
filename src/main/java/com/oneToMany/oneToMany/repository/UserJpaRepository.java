package com.oneToMany.oneToMany.repository;

import com.oneToMany.oneToMany.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
}
