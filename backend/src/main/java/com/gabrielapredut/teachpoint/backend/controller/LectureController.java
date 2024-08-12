package com.gabrielapredut.teachpoint.backend.controller;

import com.gabrielapredut.teachpoint.backend.model.Course;
import com.gabrielapredut.teachpoint.backend.model.Lecture;
import com.gabrielapredut.teachpoint.backend.repository.CourseRepository;
import com.gabrielapredut.teachpoint.backend.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lectures")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private CourseRepository courseRepository; // Ensure you have this repository

    @GetMapping
    public ResponseEntity<List<Lecture>> getAllLectures() {
        return ResponseEntity.ok(lectureService.findAllLectures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lecture> getLectureById(@PathVariable Long id) {
        Optional<Lecture> lecture = lectureService.findLectureById(id);
        return lecture.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createLecture(@Valid @RequestBody Lecture lecture, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // Validate and set course
        if (lecture.getCourse() == null || lecture.getCourse().getId() <= 0) {
            return ResponseEntity.badRequest().body("Course ID must be provided.");
        }

        Course course = courseRepository.findById(lecture.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course with ID " + lecture.getCourse().getId() + " not found"));

        lecture.setCourse(course);

        Lecture savedLecture = lectureService.saveLecture(lecture);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLecture);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLecture(@PathVariable Long id, @Valid @RequestBody Lecture lecture, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        if (!lectureService.findLectureById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Validate and set course
        if (lecture.getCourse() == null || lecture.getCourse().getId() <= 0) {
            return ResponseEntity.badRequest().body("Course ID must be provided.");
        }

        Course course = courseRepository.findById(lecture.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course with ID " + lecture.getCourse().getId() + " not found"));

        lecture.setCourse(course);
        lecture.setId(id);

        Lecture updatedLecture = lectureService.saveLecture(lecture);
        return ResponseEntity.ok(updatedLecture);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable Long id) {
        try {
            lectureService.deleteLecture(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
