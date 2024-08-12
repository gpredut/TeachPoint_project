package com.gabrielapredut.teachpoint.backend.service;

import com.gabrielapredut.teachpoint.backend.model.Course;
import com.gabrielapredut.teachpoint.backend.model.Lecture;
import com.gabrielapredut.teachpoint.backend.repository.CourseRepository;
import com.gabrielapredut.teachpoint.backend.repository.LectureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LectureService {

    private static final Logger logger = LoggerFactory.getLogger(LectureService.class);

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private CourseRepository courseRepository; // Ensure you have this repository

    @Transactional(readOnly = true)
    public List<Lecture> findAllLectures() {
        return lectureRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Lecture> findLectureById(Long id) {
        return lectureRepository.findById(id);
    }

    @Transactional
    public Lecture saveLecture(Lecture lecture) {
        logger.info("Saving lecture: {}", lecture.getTitle());

        // Validate course
        if (lecture.getCourse() == null || lecture.getCourse().getId() <= 0) {
            throw new IllegalArgumentException("Course ID must be provided.");
        }

        // Fetch and set course
        Course course = courseRepository.findById(lecture.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course with ID " + lecture.getCourse().getId() + " not found"));

        lecture.setCourse(course);

        return lectureRepository.save(lecture);
    }

    @Transactional
    public void deleteLecture(Long id) {
        if (lectureRepository.existsById(id)) {
            lectureRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Lecture with ID " + id + " not found");
        }
    }
}
