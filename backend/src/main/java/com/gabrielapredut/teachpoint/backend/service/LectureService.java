package com.gabrielapredut.teachpoint.backend.service;

import com.gabrielapredut.teachpoint.backend.model.Lecture;
import com.gabrielapredut.teachpoint.backend.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    public List<Lecture> findAllLectures() {
        return lectureRepository.findAll();
    }

    public Optional<Lecture> findLectureById(Long id) {
        return lectureRepository.findById(id);
    }

    public Lecture saveLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    public void deleteLecture(Long id) {
        lectureRepository.deleteById(id);
    }
}
