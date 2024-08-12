package com.gabrielapredut.teachpoint.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gabrielapredut.teachpoint.backend.model.Instructor;
import com.gabrielapredut.teachpoint.backend.model.User;
import com.gabrielapredut.teachpoint.backend.repository.InstructorRepository;
import com.gabrielapredut.teachpoint.backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Instructor> findAllInstructors() {
        return instructorRepository.findAllWithCourses();
    }

    @Transactional(readOnly = true)
    public Optional<Instructor> findInstructorById(Long id) {
        return instructorRepository.findByIdWithCourses(id);
    }

    @Transactional
    public Instructor saveInstructor(Instructor instructor) {
        // Ensure that the associated user is saved
        User user = instructor.getUser();
        if (user.getId() == null) {
            user = userRepository.save(user);
            instructor.setUser(user);
        }
        return instructorRepository.save(instructor);
    }

    @Transactional
    public void deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
    }
}

