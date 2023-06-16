package com.sii.conference.services;

import com.sii.conference.data.Lecture;
import com.sii.conference.data.repositories.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    public void updateOrCreateLecture(Lecture lecture) {
        lectureRepository.save(lecture);
    }

    public Optional<Lecture> findLectureById(Integer id) {
        return lectureRepository.findLectureById(id);
    }

    public List<Lecture> findAll() {
        return lectureRepository.findAllByOrderByIdAsc();
    }

    public void deleteLecture(Lecture lecture) {
        lectureRepository.delete(lecture);
    }
}
