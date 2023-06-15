package com.sii.conference.data.repositories;

import com.sii.conference.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Integer id);

    Optional<User> findUserByLogin(String login);

    List<User> findAllByOrderByIdAsc();
}
