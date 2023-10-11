package com.sii.conference.services;

import com.sii.conference.data.Lecture;
import com.sii.conference.data.Reservation;
import com.sii.conference.data.ThemedPath;
import com.sii.conference.data.User;
import com.sii.conference.data.repositories.LectureRepository;
import com.sii.conference.data.repositories.ReservationRepository;
import com.sii.conference.data.repositories.ThemedPathRepository;
import com.sii.conference.data.repositories.UserRepository;
import com.sii.conference.data.statistics.IdPercentStat;
import com.sii.conference.exceptions.lecture.LectureAlreadyFullException;
import com.sii.conference.exceptions.lecture.NoLectureWithThisIDException;
import com.sii.conference.exceptions.reservation.ReservationAlreadyExistsException;
import com.sii.conference.exceptions.reservation.ReservationCollidesWithAnotherException;
import com.sii.conference.exceptions.user.NoUserWithThisIDException;
import com.sii.conference.exceptions.user.NoUserWithThisLoginAndEmailExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final LectureRepository lectureRepository;

    private final UserRepository userRepository;

    private final ThemedPathRepository themedPathRepository;

    private final EmailService emailService;

    @Value("${lecture.max-reservations}")
    private Integer maxReservations;

    public Reservation createReservation(Reservation reservation, Integer userId, Integer lectureId) throws NoUserWithThisIDException, NoLectureWithThisIDException, LectureAlreadyFullException, ReservationAlreadyExistsException, ReservationCollidesWithAnotherException {
        validateIfReservationIsUnique(userId, lectureId);
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new NoUserWithThisIDException(String.format("User with id {%d} not found", userId)));
        Lecture lecture = lectureRepository.findLectureById(lectureId)
                .orElseThrow(() -> new NoLectureWithThisIDException(String.format("Lecture with id {%d} not found", lectureId)));

        reservation.setUser(user);
        reservation.setLecture(lecture);
        if (reservationRepository.countByLectureId(lectureId) < maxReservations) {
            List<Reservation> userReservations = reservationRepository.findAllByUserLoginOrderByIdAsc(user.getLogin());
            for (Reservation userReservation : userReservations) {
                Lecture resLecture = userReservation.getLecture();
                if (resLecture.getStartTime().equals(lecture.getStartTime()))
                    throw new ReservationCollidesWithAnotherException(String.format("Reservation collides with reservation with id {%d}", userReservation.getId()));
            }

            emailService.WriteToFile(user.getEmail(), "You reserved a spot in lecture: " + lecture.getTopic() + ".");
            return reservationRepository.save(reservation);
        } else {
            throw new LectureAlreadyFullException("Lecture already full!");
        }
    }

    public Reservation createReservation(Reservation reservation, String userLogin, String userEmail, Integer lectureId) throws NoUserWithThisIDException, NoLectureWithThisIDException, LectureAlreadyFullException, ReservationAlreadyExistsException, NoUserWithThisLoginAndEmailExistsException, ReservationCollidesWithAnotherException {
        Optional<User> userOptional = userRepository.findUserByLoginAndEmail(userLogin, userEmail);

        if (userOptional.isPresent()) {
            return createReservation(reservation, userOptional.get().getId(), lectureId);
        } else {
            throw new NoUserWithThisLoginAndEmailExistsException("User with given login and email does not exist!");
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

    public List<User> findAllUsersByLectureId(Integer lectureId)
    {
        List<Reservation> reservationsOfLecture = reservationRepository.findAllByLectureIdOrderByIdAsc(lectureId);
        List<User> usersOfLecture = new ArrayList<>();
        for (Reservation reservation : reservationsOfLecture) {
            usersOfLecture.add(reservation.getUser());
        }
        return usersOfLecture;
    }

    public List<IdPercentStat> getStatisticsAboutLectures()
    {
        List<Reservation> reservations = findAll();
        List<Lecture> lectures = lectureRepository.findAllByOrderByIdAsc();

        Map<Integer, Integer> reservationMap = new HashMap<>();
        for (Reservation reservation : reservations) {
            Integer lectureId = reservation.getLecture().getId();
            if (reservationMap.containsKey(lectureId)) {
                reservationMap.replace(lectureId, reservationMap.get(lectureId) + 1);
            } else {
                reservationMap.put(lectureId, 1);
            }
        }

        List<IdPercentStat> statistics = new ArrayList<>();

        for (Lecture lecture : lectures) {
            Integer lectureId = lecture.getId();
            if (reservationMap.containsKey(lectureId)) {
                statistics.add(new IdPercentStat(lectureId, (float)reservationMap.get(lectureId) / reservations.size()));
            } else {
                statistics.add(new IdPercentStat(lectureId, 0f));
            }
        }
        return statistics;
    }

    public List<IdPercentStat> getStatisticsAboutThemedPaths()
    {
        List<Reservation> reservations = findAll();
        List<ThemedPath> themedPaths = themedPathRepository.findAllByOrderByIdAsc();

        Map<Integer, Integer> reservationMap = new HashMap<>();
        for (Reservation reservation : reservations) {
            Integer themedPathId = reservation.getLecture().getThemedPath().getId();
            if (reservationMap.containsKey(themedPathId)) {
                reservationMap.replace(themedPathId, reservationMap.get(themedPathId) + 1);
            } else {
                reservationMap.put(themedPathId, 1);
            }
        }

        List<IdPercentStat> statistics = new ArrayList<>();

        for (ThemedPath themedPath : themedPaths) {
            Integer themedPathId = themedPath.getId();
            if (reservationMap.containsKey(themedPathId)) {
                statistics.add(new IdPercentStat(themedPathId, (float)reservationMap.get(themedPathId) / reservations.size()));
            } else {
                statistics.add(new IdPercentStat(themedPathId, 0f));
            }
        }
        return statistics;
    }

    private void validateIfReservationIsUnique(Integer userId, Integer lectureId) throws ReservationAlreadyExistsException {
        reservationRepository.findReservationByUserIdAndLectureId(userId, lectureId)
                .ifPresent(reservation -> {throw new ReservationAlreadyExistsException("Reservation already exists!");});
    }
}
