package com.sii.conference.services;

import com.sii.conference.data.Lecture;
import com.sii.conference.data.Reservation;
import com.sii.conference.data.Themed_Path;
import com.sii.conference.data.User;
import com.sii.conference.data.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public void updateOrCreateReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public Optional<Reservation> findReservationById(Integer id) {
        return reservationRepository.findReservationById(id);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAllByOrderByIdAsc();
    }

    public List<Reservation> findAllByUser(User user) {
        return reservationRepository.findAllByUserOrderByIdAsc(user);
    }

    public List<Reservation> findAllByLecture(Lecture lecture) {
        return reservationRepository.findAllByLectureOrderByIdAsc(lecture);
    }

    public void deleteReservation(Reservation reservation) {
        reservationRepository.delete(reservation);
    }
}
