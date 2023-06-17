package com.sii.conference.data.repositories;

import com.sii.conference.data.ThemedPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThemedPathRepository extends JpaRepository<ThemedPath, Long> {

    Optional<ThemedPath> findThemedPathById(Integer id);

    List<ThemedPath> findAllByOrderByIdAsc();
}
