package com.sii.conference.data.repositories;

import com.sii.conference.data.Lecture;
import com.sii.conference.data.Reservation;
import com.sii.conference.data.Themed_Path;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThemedPathRepository extends JpaRepository<Themed_Path, Long> {

    Optional<Themed_Path> findThemed_PathById(Integer id);

    List<Themed_Path> findAllByOrderByIdAsc();
}
