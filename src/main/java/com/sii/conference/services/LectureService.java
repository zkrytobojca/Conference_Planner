package com.sii.conference.services;

import com.sii.conference.data.Lecture;
import com.sii.conference.data.ThemedPath;
import com.sii.conference.data.User;
import com.sii.conference.data.repositories.LectureRepository;
import com.sii.conference.data.repositories.ThemedPathRepository;
import com.sii.conference.exceptions.themedpath.NoThemedPathWithThisIDException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    private final ThemedPathRepository themedPathRepository;

    public Lecture createLecture(Lecture lecture, Integer themedPathId) throws NoThemedPathWithThisIDException {
        Optional<ThemedPath> themedPathOptional = themedPathRepository.findThemedPathById(themedPathId);
        if (themedPathOptional.isPresent()) {
            ThemedPath themedPath = themedPathOptional.get();
            lecture.setThemedPath(themedPath);
            return lectureRepository.save(lecture);
        } else {
            throw new NoThemedPathWithThisIDException(String.format("Themed Path with id {%d} not found", themedPathId));
        }
    }

    public Optional<Lecture> updateLecture(Integer id, Lecture lecture) throws NoThemedPathWithThisIDException {
        final Optional<Lecture> lectureOptional = findLectureById(id);

        Lecture oldLecture = null;
        if (lectureOptional.isPresent()) {
            oldLecture = lectureOptional.get();
            if (lecture.getTopic() != null) oldLecture.setTopic(lecture.getTopic());
            if (lecture.getStartTime() != null) oldLecture.setStartTime(lecture.getStartTime());
            if (lecture.getEndTime() != null) oldLecture.setEndTime(lecture.getEndTime());
            if (lecture.getThemedPath() != null) {
                Integer themedPathId = lecture.getThemedPath().getId();
                Optional<ThemedPath> themedPathOptional = themedPathRepository.findThemedPathById(themedPathId);
                if (themedPathOptional.isPresent()) {
                    oldLecture.setThemedPath(themedPathOptional.get());
                } else {
                    throw new NoThemedPathWithThisIDException(String.format("Themed Path with id {%d} not found", themedPathId));
                }
            }
            return Optional.of(lectureRepository.save(oldLecture));
        }
        else return Optional.empty();
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
