package com.sii.conference.data.repositories;

import com.sii.conference.data.Lecture;
import com.sii.conference.data.Reservation;
import com.sii.conference.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findReservationById(Integer id);

    Optional<Reservation> findReservationByUserIdAndLectureId(Integer userid, Integer lectureId);

    List<Reservation> findAllByUserOrderByIdAsc(User user);

    List<Reservation> findAllByLectureOrderByIdAsc(Lecture lecture);

    List<Reservation> findAllByOrderByIdAsc();

    List<Reservation> findAllByUserLoginOrderByIdAsc(String login);

    List<Reservation> findAllByLectureIdOrderByIdAsc(Integer lectureId);

    Integer countByLectureId(Integer lectureId);
}
