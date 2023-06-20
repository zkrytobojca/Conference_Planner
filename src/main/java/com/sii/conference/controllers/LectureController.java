package com.sii.conference.controllers;

import com.sii.conference.data.Lecture;
import com.sii.conference.data.elements.LectureCreationElement;
import com.sii.conference.exceptions.themedpath.NoThemedPathWithThisIDException;
import com.sii.conference.services.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/lecture")
public class LectureController {

    private final LectureService lectureService;

    @PostMapping
    public ResponseEntity<Lecture> addNewLecture(@RequestBody LectureCreationElement lectureCreationElement) {
        try{
            Lecture createdLecture = lectureService.createLecture(lectureCreationElement.getLecture(), lectureCreationElement.getThemedPathId());

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdLecture.getId())
                    .toUri();

            return ResponseEntity.created(location).body(createdLecture);

        } catch (NoThemedPathWithThisIDException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lecture> getLectureById(@PathVariable("id") Integer id) {
        final Optional<Lecture> foundLecture = lectureService.findLectureById(id);
        return ResponseEntity.of(foundLecture);
    }

    @GetMapping
    public ResponseEntity<List<Lecture>> getAllLectures() {
        List<Lecture> lectureList = lectureService.findAll();
        return ResponseEntity.ok().body(lectureList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lecture> updateLecture(@PathVariable("id") Integer id, @RequestBody Lecture newLecture) {
        try {
            Optional<Lecture> updatedLecture = lectureService.updateLecture(id, newLecture);
            return ResponseEntity.of(updatedLecture);

        } catch (NoThemedPathWithThisIDException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Lecture> deleteLecture(@PathVariable("id") Integer id) {
        final Optional<Lecture> lectureToDelete = lectureService.findLectureById(id);

        if (lectureToDelete.isPresent()) {
            lectureService.deleteLecture(lectureToDelete.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
