package com.gabrielapredut.teachpoint.backend.service;

import com.gabrielapredut.teachpoint.backend.model.Instructor;
import com.gabrielapredut.teachpoint.backend.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    public List<Instructor> findAllInstructors() {
        return instructorRepository.findAll();
    }

    public Optional<Instructor> findInstructorById(Long id) {
        return instructorRepository.findById(id);
    }

    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public void deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
    }
}

