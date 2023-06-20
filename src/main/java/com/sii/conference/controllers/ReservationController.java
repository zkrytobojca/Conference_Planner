package com.sii.conference.controllers;

import com.sii.conference.data.Lecture;
import com.sii.conference.data.Reservation;
import com.sii.conference.data.User;
import com.sii.conference.data.elements.ReservationCreationElement;
import com.sii.conference.data.elements.ReservationCreationLoginEmailElement;
import com.sii.conference.data.statistics.IdPercentStat;
import com.sii.conference.exceptions.lecture.LectureAlreadyFullException;
import com.sii.conference.exceptions.lecture.NoLectureWithThisIDException;
import com.sii.conference.exceptions.reservation.ReservationAlreadyExistsException;
import com.sii.conference.exceptions.reservation.ReservationCollidesWithAnotherException;
import com.sii.conference.exceptions.user.NoUserWithThisIDException;
import com.sii.conference.exceptions.user.NoUserWithThisLoginAndEmailExistsException;
import com.sii.conference.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<String> addNewReservation(@RequestBody ReservationCreationElement reservationCreationElement) {
        try{
            Reservation createdReservation = reservationService.createReservation(new Reservation(), reservationCreationElement.getUserid(), reservationCreationElement.getLectureId());

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdReservation.getId())
                    .toUri();

            return ResponseEntity.created(location).body("Reservation created successfully.");

        } catch (NoUserWithThisIDException | NoLectureWithThisIDException | LectureAlreadyFullException |
                 ReservationAlreadyExistsException | ReservationCollidesWithAnotherException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createUsingLoginAndEmail")
    public ResponseEntity<String> addNewReservationUsingLoginAndEmail(@RequestBody ReservationCreationLoginEmailElement reservationCreationElement) {
        try{
            Reservation createdReservation = reservationService.createReservation(new Reservation(), reservationCreationElement.getUserLogin(), reservationCreationElement.getUserEmail(), reservationCreationElement.getLectureId());

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdReservation.getId())
                    .toUri();

            return ResponseEntity.created(location).body("Reservation created successfully.");

        } catch (NoUserWithThisIDException | NoLectureWithThisIDException | LectureAlreadyFullException |
                 ReservationAlreadyExistsException | NoUserWithThisLoginAndEmailExistsException | ReservationCollidesWithAnotherException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Integer id) {
        final Optional<Reservation> foundReservation = reservationService.findReservationById(id);
        return ResponseEntity.of(foundReservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservationList = reservationService.findAll();
        return ResponseEntity.ok().body(reservationList);
    }

    @GetMapping("/listLecturesOfUser")
    public ResponseEntity<List<Lecture>> getAllLecturesOfUser(@RequestParam String userLogin) {
        List<Lecture> lecturesOfUser = reservationService.findAllLecturesByUserLogin(userLogin);
        return ResponseEntity.ok().body(lecturesOfUser);
    }

    @GetMapping("/listUsersOfLecture")
    public ResponseEntity<List<User>> getAllLecturesOfUser(@RequestParam Integer lectureId) {
        List<User> usersOfLecture = reservationService.findAllUsersByLectureId(lectureId);
        return ResponseEntity.ok().body(usersOfLecture);
    }

    @GetMapping("/statistics/lectures")
    public ResponseEntity<List<IdPercentStat>> getStatsLectures() {
        List<IdPercentStat> statistics = reservationService.getStatisticsAboutLectures();
        return ResponseEntity.ok().body(statistics);
    }

    @GetMapping("/statistics/themedPaths")
    public ResponseEntity<List<IdPercentStat>> getStatsThemedPaths() {
        List<IdPercentStat> statistics = reservationService.getStatisticsAboutThemedPaths();
        return ResponseEntity.ok().body(statistics);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable("id") Integer id, @RequestBody Reservation newReservation) {
        try {
            Optional<Reservation> updatedReservation =  reservationService.updateReservation(id, newReservation);
            return ResponseEntity.of(updatedReservation);
        } catch (NoUserWithThisIDException | NoLectureWithThisIDException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable("id") Integer id) {
        final Optional<Reservation> reservationToDelete = reservationService.findReservationById(id);

        if (reservationToDelete.isPresent()) {
            reservationService.deleteReservation(reservationToDelete.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
