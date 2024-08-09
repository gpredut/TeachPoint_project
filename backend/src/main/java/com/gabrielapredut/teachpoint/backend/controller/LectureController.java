package com.gabrielapredut.teachpoint.backend.controller;

import com.gabrielapredut.teachpoint.backend.model.Lecture;
import com.gabrielapredut.teachpoint.backend.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lectures")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @GetMapping
    public List<Lecture> getAllLectures() {
        return lectureService.findAllLectures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lecture> getLectureById(@PathVariable Long id) {
        Optional<Lecture> lecture = lectureService.findLectureById(id);
        return lecture.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Lecture> createLecture(@RequestBody Lecture lecture) {
        Lecture savedLecture = lectureService.saveLecture(lecture);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLecture);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lecture> updateLecture(@PathVariable Long id, @RequestBody Lecture lecture) {
        if (!lectureService.findLectureById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        lecture.setId(id);
        Lecture updatedLecture = lectureService.saveLecture(lecture);
        return ResponseEntity.ok(updatedLecture);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable Long id) {
        if (!lectureService.findLectureById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        lectureService.deleteLecture(id);
        return ResponseEntity.noContent().build();
    }
}

