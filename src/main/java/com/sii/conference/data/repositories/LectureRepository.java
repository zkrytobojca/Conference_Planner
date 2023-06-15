package com.sii.conference.data.repositories;

import com.sii.conference.data.Lecture;
import com.sii.conference.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    Optional<Lecture> findLectureById(Integer id);

    List<Lecture> findAllByOrderByIdAsc();
}
