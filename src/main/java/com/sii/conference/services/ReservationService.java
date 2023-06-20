package com.sii.conference.services;

import com.sii.conference.data.Lecture;
import com.sii.conference.data.Reservation;
import com.sii.conference.data.ThemedPath;
import com.sii.conference.data.User;
import com.sii.conference.data.repositories.LectureRepository;
import com.sii.conference.data.repositories.ReservationRepository;
import com.sii.conference.data.repositories.UserRepository;
import com.sii.conference.exceptions.lecture.NoLectureWithThisIDException;
import com.sii.conference.exceptions.themedpath.NoThemedPathWithThisIDException;
import com.sii.conference.exceptions.user.NoUserWithThisIDException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final LectureRepository lectureRepository;

    private final UserRepository userRepository;

    public Reservation createReservation(Reservation reservation, Integer userId, Integer lectureId) throws NoUserWithThisIDException, NoLectureWithThisIDException {
        Optional<User> userOptional = userRepository.findUserById(userId);
        Optional<Lecture> lectureOptional = lectureRepository.findLectureById(lectureId);

        if (userOptional.isPresent() && lectureOptional.isPresent()) {
            User user = userOptional.get();
            reservation.setUser(user);
            Lecture lecture = lectureOptional.get();
            reservation.setLecture(lecture);
            return reservationRepository.save(reservation);
        } else {
            if (userOptional.isEmpty()) throw new NoUserWithThisIDException(String.format("User with id {%d} not found", userId));
            else throw new NoLectureWithThisIDException(String.format("Lecture with id {%d} not found", lectureId));
        }
    }

    public Optional<Reservation> updateReservation(Integer id, Reservation reservation) throws NoUserWithThisIDException, NoLectureWithThisIDException {
        final Optional<Reservation> reservationOptional = findReservationById(id);

        Reservation oldReservation = null;
        if (reservationOptional.isPresent()) {
            oldReservation = reservationOptional.get();
            if (reservation.getUser() != null) {
                Integer userId = reservation.getUser().getId();
                Optional<User> userOptional = userRepository.findUserById(userId);
                if (userOptional.isPresent()) {
                    oldReservation.setUser(userOptional.get());
                } else {
                    throw new NoUserWithThisIDException(String.format("User with id {%d} not found", userId));
                }
            }
            if (reservation.getLecture() != null) {
                Integer lectureId = reservation.getLecture().getId();
                Optional<Lecture> lectureOptional = lectureRepository.findLectureById(lectureId);
                if (lectureOptional.isPresent()) {
                    oldReservation.setLecture(lectureOptional.get());
                } else {
                    throw new NoLectureWithThisIDException(String.format("Lecture with id {%d} not found", lectureId));
                }
            }
            return Optional.of(reservationRepository.save(oldReservation));
        }
        else return Optional.empty();
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

    public List<Lecture> findAllLecturesByUserLogin(String login)
    {
        List<Reservation> reservationsOfUser = reservationRepository.findAllByUserLoginOrderByIdAsc(login);
        List<Lecture> lecturesOfUser = new ArrayList<>();
        for (Reservation reservation : reservationsOfUser) {
            lecturesOfUser.add(reservation.getLecture());
        }
        return lecturesOfUser;
    }
}
